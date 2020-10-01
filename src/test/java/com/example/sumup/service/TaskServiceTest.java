package com.example.sumup.service;

import com.example.sumup.model.Task;
import com.example.sumup.model.TasksSequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class TaskServiceTest {
    @Autowired
    private TaskService service;

    private Task task1, task2, task3, task4, task5, task6, task7;

    @BeforeAll
    void setUp() {
        task1 = new Task();
        task1.setName("task-1");
        task1.setCommand("task-1 command");

        task2 = new Task();
        task2.setName("task-2");
        task2.setCommand("task-2 command");
        task2.setRequiredTask(List.of("task-3"));


        task3 = new Task();
        task3.setName("task-3");
        task3.setCommand("task-3 command");
        task3.setRequiredTask(List.of("task-1"));


        task4 = new Task();
        task4.setName("task-4");
        task4.setCommand("task-4 command");
        task4.setRequiredTask(List.of("task-2", "task-3"));


        task5 = new Task();
        task5.setName("task-5");
        task5.setCommand("task-5 command");
        task5.setRequiredTask(List.of("task-6"));

        task6 = new Task();
        task6.setName("task-6");
        task6.setCommand("task-6 command");
        task6.setRequiredTask(List.of("task-7"));


        task7 = new Task();
        task7.setName("task-7");
        task7.setCommand("task-7 command");
        task7.setRequiredTask(List.of("task-5"));


    }

    @Test
    void testOrder() throws IncorrectDataException {
        TasksSequence sequence = new TasksSequence();
        sequence.setTasks(List.of(task1, task2, task3, task4));

        Assertions.assertIterableEquals(List.of(task1, task3, task2, task4), service.order(sequence));
    }

    @Test
    void testScript() throws IncorrectDataException {
        TasksSequence sequence = new TasksSequence();
        sequence.setTasks(List.of(task1, task2, task3, task4));

        Assertions.assertEquals("#!/usr/bin/env bash\n\ntask-1 command\ntask-3 command\ntask-2 command\ntask-4 command", service.generateScript(sequence));
    }

    @Test
    void testCycle() {
        TasksSequence sequence = new TasksSequence();
        sequence.setTasks(List.of(task1, task2, task3, task4, task5, task6, task7));

        Assertions.assertThrows(IncorrectDataException.class, () -> service.order(sequence));
    }

    @Test
    void testInconsistentData() {
        TasksSequence sequence = new TasksSequence();
        sequence.setTasks(List.of(task1, task2, task3, task4, task5, task6));

        Assertions.assertThrows(IncorrectDataException.class, () -> service.order(sequence));
    }

    @Test
    void testEmpty() throws IncorrectDataException {
        TasksSequence sequence = new TasksSequence();
        Assertions.assertTrue(service.order(sequence).isEmpty());
    }
}