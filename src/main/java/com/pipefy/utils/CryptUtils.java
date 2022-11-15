package com.pipefy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

public class CryptUtils {

    private static Logger log = LoggerFactory.getLogger(CryptUtils.class);

    public static String encrypt(String value, String key) {
        try {
            Cipher cipher = config(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedResult = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedResult);
        } catch (Exception error) {
            log.error(error.toString());
            return null;
        }
    }

    public static String decrypt(String value, String key)  {
        try {
            Cipher cipher = config(Cipher.DECRYPT_MODE, key);
            byte[] decryptedResult = cipher.doFinal(Base64.getDecoder().decode(value));
            return new String(decryptedResult);
        } catch (Exception error) {
            log.error(error.toString());
            return null;
        }
    }

    private static Cipher config(int cipherMode, String key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(cipherMode, createKey(key), createIv());
        return cipher;
    }

    private static Key createKey(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] secretKey = messageDigest.digest(key.getBytes("UTF-8"));
        return new SecretKeySpec(secretKey, "AES");
    }

    private static AlgorithmParameterSpec createIv() throws UnsupportedEncodingException {

        return new IvParameterSpec("0000000000000000".getBytes("UTF-8"));
    }

    public static void main(String[] args) {

        String plainText = "SOME_TEXT";
        String key = System.getenv("AUTOMATION_SECRET");

        String decrypted = CryptUtils.decrypt(plainText, key);
        System.out.println("Decrypted text: " + decrypted);

        String encrypted = CryptUtils.encrypt(plainText, key);
        System.out.println("Encrypted text: " + encrypted);

        decrypted = CryptUtils.decrypt(encrypted, key);
        System.out.println("Decrypted text: " + decrypted);
    }
}