package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DBService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Log
@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    @Autowired
    private DBService service;

    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("taskId") Long taskId) {
        validateId(taskId);
        return new ResponseEntity<>(
            taskMapper.mapToTaskDto(service.getTask(taskId).orElseThrow(NoSuchTaskException::new)),
            HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{taskId}")
    public ResponseEntity deleteTask(@PathVariable("taskId") Long taskId) {
        throw new UnsupportedOperationException();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto task) {
        throw new UnsupportedOperationException();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto task) {
        throw new UnsupportedOperationException();
    }

    private void validateId(Long taskId) {
        if(taskId == null || taskId <= 0) {
            throw new BadTaskIdException();
        }
    }
}
