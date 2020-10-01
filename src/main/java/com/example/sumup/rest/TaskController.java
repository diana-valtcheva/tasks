package com.example.sumup.rest;


import com.example.sumup.model.Task;
import com.example.sumup.model.TasksSequence;
import com.example.sumup.service.IncorrectDataException;
import com.example.sumup.service.LoadTasksService;
import com.example.sumup.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    LoadTasksService loadService;

    @PostMapping(value = "/tasks/order")
    public List<Task> orderTasks(HttpServletRequest request) throws BadDataException, BadDataFormatException {
        return executeTaskService(request, (tasksSequence -> taskService.order(tasksSequence)));
    }

    @PostMapping("/tasks/script")
    public String generateScript(HttpServletRequest request) throws BadDataException, BadDataFormatException {
        return executeTaskService(request, (tasksSequence -> taskService.generateScript(tasksSequence)));
    }

    protected <R> R executeTaskService(HttpServletRequest request, CheckedFunction<TasksSequence, R> service) throws BadDataException, BadDataFormatException {
        try {
            return service.apply(loadService.load(request.getReader()));
        } catch (IncorrectDataException e) {
            throw new BadDataException();
        } catch (IOException e) {
            throw new BadDataFormatException();
        }
    }

    @FunctionalInterface
    public interface CheckedFunction<T, R> {
        R apply(T t) throws IncorrectDataException;
    }
}
