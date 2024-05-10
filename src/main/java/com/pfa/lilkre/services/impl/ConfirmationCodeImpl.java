package com.pfa.lilkre.services.impl;

import com.pfa.lilkre.entities.ConfirmationCodeEntity;
import com.pfa.lilkre.services.intf.IConfirmationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConfirmationCodeImpl implements IConfirmationCodeService {

    @Autowired
    com.pfa.lilkre.repository.ConfirmationCodeRepository confirmationCodeRepository;

    public void saveConfirmationCode(ConfirmationCodeEntity code) {
        confirmationCodeRepository.save(code);
    }

    public Optional<ConfirmationCodeEntity> getCodeByEmail(String email) {
        List<ConfirmationCodeEntity> codesByEmail = confirmationCodeRepository.findByEmailOrderByCreatedAtDesc(email);
        if (!codesByEmail.isEmpty()) {
            return Optional.of(codesByEmail.get(0));
        } else {
            return Optional.empty();
        }
    }


}
