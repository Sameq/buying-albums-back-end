package br.com.sysmap.bootcamp.controller;

import br.com.sysmap.bootcamp.domain.User;
import br.com.sysmap.bootcamp.domain.Wallet;
import br.com.sysmap.bootcamp.service.UserService;
import br.com.sysmap.bootcamp.service.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ControllerWalletTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;
    @Mock
    private UserService userService;


    @Test
    @DisplayName("Adds credit to the user's wallet")
    void creditReturnsOk() throws Exception{
        BigDecimal value = new BigDecimal("100.00");
        String username = "testuser";
        User user = User.builder().build();
        when(userService.findByName(username)).thenReturn(user);
        ResultActions resultActions = mockMvc.perform(post("/credit/{value}", value)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
        verify(walletService).addFromWallet(user, value);
    }

    @Test
    @DisplayName("Search for the user's wallet")
    void getWalletReturnsOk() throws Exception{
        String username = "testuser";
        User user = User.builder().build();
        when(userService.findByName(username)).thenReturn(user);

        Wallet wallet =  Wallet.builder().build();
        when(walletService.getWallet(user)).thenReturn(wallet);

        ResultActions resultActions = mockMvc.perform(get("/wallet")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }
}