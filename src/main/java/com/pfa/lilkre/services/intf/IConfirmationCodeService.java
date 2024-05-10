package com.pfa.lilkre.services.intf;

import com.pfa.lilkre.entities.ConfirmationCodeEntity;

import java.util.Optional;

public interface IConfirmationCodeService {
    public void saveConfirmationCode(ConfirmationCodeEntity code);

    public Optional<ConfirmationCodeEntity> getCodeByEmail(String email);
}
