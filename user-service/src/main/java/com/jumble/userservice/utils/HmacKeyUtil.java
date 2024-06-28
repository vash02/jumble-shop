package com.jumble.userservice.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HmacKeyUtil {

    public SecretKey generateHmacKey() throws NoSuchAlgorithmException {
        // Generate a random secret key for HMAC
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecureRandom random = new SecureRandom();
        keyGen.init(random);
        return keyGen.generateKey();
    }

    // Alternatively, if you have a byte array representing your secret key:
    public SecretKey createSecretKey(byte[] secretKeyBytes) {
        return new SecretKeySpec(secretKeyBytes, "HmacSHA256");
    }
}

