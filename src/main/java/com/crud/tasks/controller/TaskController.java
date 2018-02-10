package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


// demo version:
// assuming that there are only 3 tasks
// validation is applied to TaskDao not Task
// exceptions don't inform about some details
@Log
@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    @RequestMapping(method = RequestMethod.GET)
    public List<TaskDto> getTasks() {
        log.info("sending task");
        TaskDto task1 = new TaskDto(1L, "Task 1", "Task 1 description");
        TaskDto task2 = new TaskDto(2L, "Task 2", "Task 2 description");
        TaskDto task3 = new TaskDto(3L, "Task 3", "Task 3 description");
        return new ArrayList<>(Arrays.asList(task1, task2, task3));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("taskId") Long taskId) {
        validate(taskId);
        log.info("sending task with id: " + taskId);
        return new ResponseEntity<>(new TaskDto(taskId, "Task title", "Task description"), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{taskId}")
    public ResponseEntity deleteTask(@PathVariable("taskId") Long taskId) {
        validate(taskId);
        log.info("deleting task with id: " + taskId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto task) {
        validate(task);
        log.info("updating " + task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto task) {
        validateNew(task);
        log.info("creating " + task);
        return new ResponseEntity<>(new TaskDto(4L, task.getTitle(),task.getContent()),HttpStatus.CREATED);
    }

    private void validate(Long taskId) {
        if(taskId == null || taskId <= 0) {
            throw new BadTaskIdException();
        }
        if(taskId > 3) {
            throw new NoSuchTaskException();
        }
    }

    private void validate(TaskDto task) {
        Long id = task.getId();
        String title = task.getTitle();
        if(id == null || id <= 0 || id > 3 || title == null || title.isEmpty()) {
            throw new InvalidTaskException();
        }
    }

    private void validateNew(TaskDto task) {
        Long id = task.getId();
        String title = task.getTitle();
        if(id != null || title == null || title.isEmpty()) {
            throw new InvalidTaskException();
        }
    }
}
