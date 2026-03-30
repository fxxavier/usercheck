package com.github.felipesx.usercheck.controller;

import com.github.felipesx.usercheck.dto.VoteStatusResponse;
import com.github.felipesx.usercheck.exception.InvalidCpfException;
import com.github.felipesx.usercheck.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private static final String VALID_CPF = "52998224725";

    @Test
    void shouldReturn200WithAbleToVote() throws Exception {
        when(userService.checkVoteStatus(VALID_CPF)).thenReturn(VoteStatusResponse.ableToVote());

        mockMvc.perform(get("/users/{cpf}", VALID_CPF))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ABLE_TO_VOTE"));
    }

    @Test
    void shouldReturn200WithUnableToVote() throws Exception {
        when(userService.checkVoteStatus(VALID_CPF)).thenReturn(VoteStatusResponse.unableToVote());

        mockMvc.perform(get("/users/{cpf}", VALID_CPF))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UNABLE_TO_VOTE"));
    }

    @Test
    void shouldReturn404ForInvalidCpf() throws Exception {
        String invalidCpf = "000.000.000-00";
        when(userService.checkVoteStatus(invalidCpf)).thenThrow(new InvalidCpfException(invalidCpf));

        mockMvc.perform(get("/users/{cpf}", invalidCpf))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }
}
