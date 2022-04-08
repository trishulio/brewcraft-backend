package io.company.brewcraft.migration;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomGeneratorImpl implements RandomGenerator {
    @Override
    public String string(int len) {
        String capitalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseChars = capitalChars.toLowerCase();
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";

        String values = capitalChars + lowercaseChars + numbers + symbols;

        SecureRandom random = null;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        char[] password = new char[len];
        for (int i = 0; i < len; i++) {
            password[i] = values.charAt(random.nextInt(values.length()));
        }

        return new String(password);
    }
}
