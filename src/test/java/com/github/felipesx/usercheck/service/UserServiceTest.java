package com.github.felipesx.usercheck.service;

import com.github.felipesx.usercheck.dto.VoteStatusResponse;
import com.github.felipesx.usercheck.exception.InvalidCpfException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    private final UserService userService = new UserService();

    // CPF válido gerado para testes (não pertence a ninguém real)
    // CPF válido sem formatação (somente dígitos), como recebido na URL
    private static final String VALID_CPF = "52998224725";

    @Test
    void shouldReturnVoteStatusForValidCpf() {
        VoteStatusResponse response = userService.checkVoteStatus(VALID_CPF);
        assertThat(response.status()).isIn("ABLE_TO_VOTE", "UNABLE_TO_VOTE");
    }

    @Test
    void shouldReturnRandomResults() {
        Set<String> results = new java.util.HashSet<>();
        // Com 20 chamadas a probabilidade de obter ambos os resultados é > 99.9%
        for (int i = 0; i < 20; i++) {
            results.add(userService.checkVoteStatus(VALID_CPF).status());
        }
        assertThat(results).containsExactlyInAnyOrder("ABLE_TO_VOTE", "UNABLE_TO_VOTE");
    }

    @Test
    void shouldThrowInvalidCpfExceptionForInvalidCpf() {
        assertThatThrownBy(() -> userService.checkVoteStatus("000.000.000-00"))
                .isInstanceOf(InvalidCpfException.class);
    }

    @Test
    void shouldThrowInvalidCpfExceptionForGibberish() {
        assertThatThrownBy(() -> userService.checkVoteStatus("abc"))
                .isInstanceOf(InvalidCpfException.class);
    }
}
