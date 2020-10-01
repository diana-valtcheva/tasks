package com.example.sumup.service;

import com.example.sumup.model.Task;
import com.example.sumup.model.TasksSequence;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    static private String HEADER = "#!/usr/bin/env bash\n\n";

    public String generateScript(TasksSequence tasks) throws IncorrectDataException {
        return HEADER + order(tasks).stream().map(Task::getCommand).reduce((c1, c2) -> c1 + "\n" + c2).orElse("");
    }

    /**
     * Order tasks so that they can be executed
     * (1) First select all tasks ready for execution, i.e. without required tasks
     * (2) then remove from required tasks list already selected tasks, because they are executed not already required
     * (3) remove the selected tasks from tasks list too
     * repeat (1), (2) and (3) till possible
     * if there isn't ready for execution tasks and task list is empty -> the job is done!
     * if tasks list is not empty, that means that some error exist: cycle in task dependencies or dependency of non existing task
     */
    public List<Task> order(TasksSequence tasks) throws IncorrectDataException {
        ArrayList<OperativeTask> working = new ArrayList<>();
        ArrayList<Task> result = new ArrayList<>();
        tasks.getTasks().forEach(t -> working.add(new OperativeTask(t)));

        List<Task> roots;

        do {
            roots = getAndRemoveRoots(working);
            result.addAll(roots);
        } while (!roots.isEmpty());

        if (!working.isEmpty()) throw new IncorrectDataException();

        return result;
    }

    protected List<Task> getAndRemoveRoots(ArrayList<OperativeTask> working) {
        List<OperativeTask> roots = working.stream().filter(t -> t.hasNoRequired()).collect(Collectors.toList());

        working.removeAll(roots);
        working.forEach(t -> roots.forEach(tootTask -> t.removeRequired(tootTask.getName())));

        return roots.stream().map(OperativeTask::getTask).collect(Collectors.toList());
    }

    private class OperativeTask {
        private Task task;
        private HashSet<String> requiredTasks;

        public OperativeTask(Task task) {
            this.task = task;
            this.requiredTasks = new HashSet<String>();
            this.requiredTasks.addAll(task.getRequiredTask());
        }

        boolean hasNoRequired() {
            return requiredTasks.isEmpty();
        }

        void removeRequired(String taskName) {
            requiredTasks.remove(taskName);
        }

        public Task getTask() {
            return task;
        }

        public String getName() {
            return task.getName();
        }
    }
}
