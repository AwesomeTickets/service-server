package com.awesome_tickets.util;

import org.springframework.util.DigestUtils;

import java.util.Random;


/**
 * Generate and compute digest of ticket code.
 */
public class CodeFactory {
    private static Random random = new Random();

    /**
     * Generate a ticket code.
     *
     * @return A ticket code with 10 characters
     */
    public static String getCode() {
        double code = random.nextDouble() * 8999999999D + 1000000000D;
        return String.valueOf((long)code);
    }

    /**
     * Generate the digest of a ticket code.
     *
     * @param code The ticket code
     * @return The digest string with 32 characters
     */
    public static String digest(String code) {
        return DigestUtils.md5DigestAsHex(code.getBytes());
    }
}
