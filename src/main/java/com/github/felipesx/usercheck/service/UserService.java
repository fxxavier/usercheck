package com.github.felipesx.usercheck.service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.github.felipesx.usercheck.dto.VoteStatusResponse;
import com.github.felipesx.usercheck.exception.InvalidCpfException;
import org.springframework.stereotype.Service;

import java.util.random.RandomGenerator;

@Service
public class UserService {

    // false = aceita CPF sem formatação (somente dígitos)
    private final CPFValidator cpfValidator = new CPFValidator(false);
    private final RandomGenerator random = RandomGenerator.getDefault();

    public VoteStatusResponse checkVoteStatus(String cpf) {
        if (!isValidCpf(cpf)) {
            throw new InvalidCpfException(cpf);
        }

        return random.nextBoolean()
                ? VoteStatusResponse.ableToVote()
                : VoteStatusResponse.unableToVote();
    }

    private boolean isValidCpf(String cpf) {
        try {
            cpfValidator.assertValid(cpf);
            return true;
        } catch (InvalidStateException e) {
            return false;
        }
    }
}
