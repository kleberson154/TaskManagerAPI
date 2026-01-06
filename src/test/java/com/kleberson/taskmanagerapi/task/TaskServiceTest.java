package com.kleberson.taskmanagerapi.task;

import com.kleberson.taskmanagerapi.dto.task.TaskRequest;
import com.kleberson.taskmanagerapi.model.Task;
import com.kleberson.taskmanagerapi.model.TaskStatus;
import com.kleberson.taskmanagerapi.model.User;
import com.kleberson.taskmanagerapi.repository.TaskRepository;
import com.kleberson.taskmanagerapi.repository.UserRepository;
import com.kleberson.taskmanagerapi.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("teste@email.com");

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication())
                .thenReturn(new UsernamePasswordAuthenticationToken(user.getEmail(), null));
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createTask_shouldReturnSavedTask() {
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Task savedTask = Task.builder()
                .id(1L)
                .title("Teste Task")
                .description("Descrição")
                .status(TaskStatus.PENDENTE)
                .user(user)
                .build();
        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        var request = new TaskRequest(
                "Teste Task",
                "Descrição"
        );

        var response = taskService.create(request);

        assertEquals(savedTask.getId(), response.id());
        assertEquals("Teste Task", response.title());
    }

    @Test
    void getAllTasks_shouldReturnTaskList() {
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Task task1 = Task.builder()
                .id(1L)
                .title("Task 1")
                .description("Descrição 1")
                .status(TaskStatus.PENDENTE)
                .user(user)
                .build();

        Task task2 = Task.builder()
                .id(2L)
                .title("Task 2")
                .description("Descrição 2")
                .status(TaskStatus.PENDENTE)
                .user(user)
                .build();

        List<Task> tasks = List.of(task1, task2);
        Page<Task> taskPage = new PageImpl<>(tasks);
        Mockito.when(taskRepository.findByUser(Mockito.eq(user), any(Pageable.class))).thenReturn(taskPage);

        var responsePage = taskService.findAll(Pageable.unpaged());

        Assertions.assertEquals(2, responsePage.getContent().size());
        Assertions.assertEquals("Task 1", responsePage.getContent().get(0).title());
        Assertions.assertEquals("Task 2", responsePage.getContent().get(1).title());
    }
}

