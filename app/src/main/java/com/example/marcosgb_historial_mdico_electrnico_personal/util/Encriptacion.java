package com.example.marcosgb_historial_mdico_electrnico_personal.util;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encriptacion {
    private static final String ALGORITMO = "AES";
    private static final String TRANSFORMACION = "AES";

    public static String encriptar(String input, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMACION);
        cipher.init(Cipher.ENCRYPT_MODE, obtenerClave(key));
        byte[] encryptedBytes = cipher.doFinal(input.getBytes());
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    public static String desencriptar(String input, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMACION);
        cipher.init(Cipher.DECRYPT_MODE, obtenerClave(key));
        byte[] decodedBytes = Base64.decode(input, Base64.DEFAULT);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    private static SecretKeySpec obtenerClave(String key) throws Exception {
        byte[] keyBytes = key.getBytes("UTF-8");
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITMO);
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return new SecretKeySpec(keyBytes, ALGORITMO);
    }
}