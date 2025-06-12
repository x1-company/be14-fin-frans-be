package com.x1.frans.user.util;

import java.security.SecureRandom;

public class GenerateRandomPassword {

    private static final String CHAR_POOL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int PASSWORD_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate() {
        StringBuilder randomPassword = new StringBuilder();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            randomPassword.append(CHAR_POOL.charAt(RANDOM.nextInt(CHAR_POOL.length())));
        }

        return randomPassword.toString();
    }
}
