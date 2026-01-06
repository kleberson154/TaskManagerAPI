package com.kleberson.taskmanagerapi.controller;

import com.kleberson.taskmanagerapi.dto.task.TaskRequest;
import com.kleberson.taskmanagerapi.dto.task.TaskResponse;
import com.kleberson.taskmanagerapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse create(@RequestBody TaskRequest request) {
        return taskService.create(request);
    }

    @GetMapping
    public Page<TaskResponse> findAll(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable) {
        return taskService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public TaskResponse update(
            @PathVariable Long id,
            @RequestBody TaskRequest request
    ) {
        return taskService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

