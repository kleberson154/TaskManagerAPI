package com.kleberson.taskmanagerapi.service;

import com.kleberson.taskmanagerapi.dto.task.TaskRequest;
import com.kleberson.taskmanagerapi.dto.task.TaskResponse;
import com.kleberson.taskmanagerapi.exception.AccessDeniedException;
import com.kleberson.taskmanagerapi.exception.ResourceNotFoundException;
import com.kleberson.taskmanagerapi.model.Task;
import com.kleberson.taskmanagerapi.model.TaskStatus;
import com.kleberson.taskmanagerapi.model.User;
import com.kleberson.taskmanagerapi.repository.TaskRepository;
import com.kleberson.taskmanagerapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Page<TaskResponse> findAll(Pageable pageable){
        User user = getAuthenticatedUser();

        return taskRepository.findByUser(user, pageable).map(task -> new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getCreatedAt()));
    }

    public TaskResponse create(TaskRequest request) {
        User user = getAuthenticatedUser();

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .status(TaskStatus.PENDENTE)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);
    }

    public TaskResponse update(Long id, TaskRequest request) {
        User user = getAuthenticatedUser();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        task.setTitle(request.title());
        task.setDescription(request.description());

        taskRepository.save(task);
        return mapToResponse(task);
    }

    public void delete(Long id) {
        User user = getAuthenticatedUser();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        taskRepository.delete(task);
    }

    private TaskResponse mapToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getCreatedAt()
        );
    }
}

