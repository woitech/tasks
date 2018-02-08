package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Log
@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        log.info("sending tasks");
        TaskDto task1 = new TaskDto(1L, "Task 1", "Task 1 description");
        TaskDto task2 = new TaskDto(2L, "Task 2", "Task 2 description");
        TaskDto task3 = new TaskDto(3L, "Task 3", "Task 3 description");
        return new ArrayList<>(Arrays.asList(task1, task2, task3));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask")
    public TaskDto getTask(Long taskId) {
        log.info("sending task with id: " + taskId);
        return new TaskDto(taskId, "Task title", "Task description");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
    public @ResponseBody void deleteTask(Long taskId) {
        log.info("deleting task with id: " + taskId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateTask")
    public TaskDto updateTask(@RequestBody TaskDto task) {
        log.info("updating " + task);
        return task;
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTask")
    public void createTask(@RequestBody TaskDto task) {
        log.info("creating " + task);
    }
}
