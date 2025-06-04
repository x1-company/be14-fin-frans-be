package com.x1.frans.email.service;

import com.x1.frans.email.dto.UserCredentialsDTO;

public interface EmailService {

    void sendUserCredentials(UserCredentialsDTO userCredentialsDTO);
}
