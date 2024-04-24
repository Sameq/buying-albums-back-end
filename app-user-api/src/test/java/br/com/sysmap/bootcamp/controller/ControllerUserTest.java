package br.com.sysmap.bootcamp.controller;

import br.com.sysmap.bootcamp.domain.User;
import br.com.sysmap.bootcamp.dto.AuthDto;
import br.com.sysmap.bootcamp.dto.UserDTO;
import br.com.sysmap.bootcamp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ControllerUserTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should Return User When Valid User Is Saved")
    public void shouldReturnUserWhenValidUserIsSaved() throws Exception {
        User user = User.builder().id(1L).name("teste").email("test").password("teste").build();

        Mockito.when(userService.saveUser(user)).thenReturn(user);

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }


    @Test
    @DisplayName("Authenticate User Token")
    void authReturnsOk() throws Exception  {

        AuthDto authDto = new AuthDto();
        authDto.setToken("11111");
        authDto.setPassword("teste123");
        authDto.setEmail("teste@hotmail");

        AuthDto authenticatedDto = new AuthDto();

        when(userService.auth(any(AuthDto.class))).thenReturn(authenticatedDto);

        ResultActions resultActions = mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authDto)));

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Search for user by id")
    void getUserIdReturnsOk() throws Exception{
        Long userId = 1L;
//        when(userService.getUserId(userId)).thenReturn(Optional.of(new User.builder());
        ResultActions resultActions = mockMvc.perform(get("/" + userId)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Update user credentials")
    void updateUserReturnsOk() throws Exception{
        UserDTO userDTO = new UserDTO("teste", "teste@example.com", "password");
        ResultActions resultActions = mockMvc.perform(put("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John\",\"email\":\"john@example.com\",\"password\":\"password\"}"));

        resultActions.andExpect(status().isOk());
//        verify(userService).updateUser(userDTO);
    }
}