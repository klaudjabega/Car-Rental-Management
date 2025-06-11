package com.example.drivenimbus.controller;

import com.example.drivenimbus.model.Users;
import com.example.drivenimbus.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerMockTest {
    @MockitoBean
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private List<Users> users;

    @BeforeEach
    void setUp() {
        Users user = new Users();
        user.setFullName("Chris");
        user.setUserId(1L);

        Users user2 = new Users();
        user2.setFullName("John");
        user2.setUserId(2L);

        users = List.of(user,user2);
    }

    @Test
    public void shouldReturnListOfUsers() throws Exception {
        Mockito.when(userService.fetchAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].fullName").value("Chris"))
                .andExpect(jsonPath("$[1].fullName").value("John"));

        Mockito.verify(userService, Mockito.times(1)).fetchAllUsers();
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        Long userId = 1L;
        Mockito.when(userService.fetchUserById(userId)).thenReturn(users.get(0));
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Chris"));

        Mockito.verify(userService, Mockito.times(1)).fetchUserById(userId);
    }

    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        Long userId = 100L;
        Mockito.when(userService.fetchUserById(userId)).thenReturn(null);
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isNotFound());
        Mockito.verify(userService, Mockito.times(1)).fetchUserById(userId);
    }

    @Test
    void shouldDeleteUser() throws Exception {
        Long userId = 1L;
        Mockito.when(userService.deleteUserById(userId)).thenReturn(true);
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk());
        Mockito.verify(userService, Mockito.times(1)).deleteUserById(userId);
    }

    @Test
    void shouldReturn404WhenUserNotFoundWhenDeleting() throws Exception {
        Long userId = 100L;
        Mockito.when(userService.deleteUserById(userId)).thenReturn(false);

        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isNotFound());

        Mockito.verify(userService, Mockito.times(1)).deleteUserById(userId);
    }
}
