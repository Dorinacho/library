package com.library.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.controllers.UserController;
import com.library.dto.UserDTO;
import com.library.models.ERole;
import com.library.models.Role;
import com.library.models.User;
import com.library.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@WithMockUser(roles = {"ADMIN"})
class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldGetUsers() throws Exception {
        List<UserDTO> users = new ArrayList<>(
                Arrays.asList(new UserDTO(1L, "Marin Marcel", "marin.marcel", "marin@yahoo.com"),
                        new UserDTO(2L, "Teofil Leontin", "teofil.leontin", "teofil.leontin@yahoo.com"),
                        new UserDTO(3L, "Pompiliu Mangalie", "pompiliu", "pompiliu@gmail.com")));

        when(userService.getUsers()).thenReturn(users);
        mockMvc.perform(get("/library/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()))
                .andDo(print());
    }

    @Test
    void shouldGetUsernames() throws Exception {
        List<String> usernames = new ArrayList<>(
                Arrays.asList("marin.marcel", "teofil.leontin", "pompiliu"));

        when(userService.getUsernames()).thenReturn(usernames);
        mockMvc.perform(get("/library/users/usernames"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(usernames.size()))
                .andDo(print());
    }

    @Test
    void shouldGetUserById() throws Exception {
        long id = 1L;
        User user = new User("Marin Marcel", "marin@yahoo.com", "marin.marcel");

        when(userService.getUserByID(anyLong())).thenReturn(Optional.of(user));

        mockMvc.perform(get("/library/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andDo(print());
    }

    @Test
    void shouldNotGetUserById() throws Exception {
        long id = 1L;
        User user = new User("Marin Marcel", "marin@yahoo.com", "marin.marcel");

        when(userService.getUserByID(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/library/users/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

//    @Test
//    void shouldCreateUser() throws Exception {
//        UserCreationDTO user = new UserCreationDTO("Marin Marcel", "marin@yahoo.com", "marin.marcel", "marinmarcel");
//
//        mockMvc.perform(post("/library/users").contentType(MediaType.APPLICATION_JSON).with(csrf())
//                        .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isCreated())
//                .andDo(print());
//    }

    @Test
    void shouldUpdateUser() throws Exception {
        long id = 1L;
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_USER));
        User user = new User("Marin Marcel", "marin.marcel", "marin@yahoo.com", roles);
        User updatedUser = new User("Paul Alexe", "paul.marcel", "alexe@yahoo.com", roles);

        when(userService.getUserByID(anyLong())).thenReturn(Optional.of(user));
        when(userService.updateUser(any(User.class), anyLong())).thenReturn(updatedUser);

        mockMvc.perform(put("/library/users/{id}", id).contentType(MediaType.APPLICATION_JSON).with(csrf())
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedUser.getName()))
                .andExpect(jsonPath("$.email").value(updatedUser.getEmail()))
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
                .andDo(print());
    }

    @Test
    void couldNotUpdateUser() throws Exception {
        long id = 1L;
        User updatedUser = new User("Paul Alexe", "alexe@yahoo.com", "paul.marcel");

        when(userService.getUserByID(anyLong())).thenReturn(Optional.empty());
        when(userService.updateUser(any(User.class), anyLong())).thenReturn(updatedUser);

        mockMvc.perform(get("/library/users/{id}", id).contentType(MediaType.APPLICATION_JSON).with(csrf())
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        long id = 1L;

        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/library/users/{id}", id).with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

}
