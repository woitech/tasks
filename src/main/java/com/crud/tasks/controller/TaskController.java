package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import static com.crud.tasks.controller.InvalidTaskException.InvalidTaskReason.*;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    @Autowired
    private DBService service;

    @Autowired
    private TaskMapper taskMapper;

    // alias '/all' is consistent with convention applied to deleteAllTasks()
    @RequestMapping(method = RequestMethod.GET, value = {"", "/all"})
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("taskId") Long taskId) {
        validateLegalId(taskId);
        return new ResponseEntity<>(
            taskMapper.mapToTaskDto(service.getTask(taskId).orElseThrow(NoSuchTaskException::new)),
            HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{taskId}")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable("taskId") Long taskId) {
        validateExistingId(taskId);
        service.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // supplementary '/all' is for security reason
    @RequestMapping(method = RequestMethod.DELETE, value = "/all")
    public ResponseEntity<TaskDto> deleteAllTasks() {
        service.deleteAllTasks();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // todo: avoid task creation after deletion in the case of concurrent access
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto task) {
        validateExisting(task);
        return new ResponseEntity<>(
                taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(task))),
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto task) {
        validateNew(task);
        return new ResponseEntity<>(
            taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(task))),
            HttpStatus.CREATED);
    }

    // todo: constraints (string length fo example) should be in common config file and set in @Column annotation

    private void validateLegalId(Long taskId) {
        if(taskId == null || taskId <= 0) {
            throw new IllegalTaskIdException();
        }
    }

    private void validateExistingId(Long taskId) {
        validateLegalId(taskId);
        if(!service.taskExists(taskId)) {
            throw new NoSuchTaskException();
        }
    }

    private void validateNew(TaskDto task) {
        if(task.getId() != null) {
            throw new InvalidTaskException(FORCED_ID);
        }
        String title = task.getTitle();
        if(title == null || title.isEmpty()|| title.length() > 20) {
            throw new InvalidTaskException(ILLEGAL_TITLE);
        }
    }

    private void validateExisting(TaskDto task) {
        Long id = task.getId();
        if(id == null || id <= 0) {
            throw new InvalidTaskException(ILLEGAL_ID);
        }
        if(!service.taskExists(id)) {
            throw new InvalidTaskException(NOT_EXIST);
        }
        String title = task.getTitle();
        if(title == null || title.isEmpty() || title.length() > 20) {
            throw new InvalidTaskException(ILLEGAL_TITLE);
        }
    }
}
