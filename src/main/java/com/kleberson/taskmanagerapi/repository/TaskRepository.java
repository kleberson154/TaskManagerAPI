package com.kleberson.taskmanagerapi.repository;

import com.kleberson.taskmanagerapi.model.Task;
import com.kleberson.taskmanagerapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByUser(User user, Pageable pageable);
}

