package com.remessas.remessas.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SegurancaSenha {
    public static String criptografaSenha(String senha) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(senha.getBytes());
        byte[] hashBytes = md.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();

    }
}
