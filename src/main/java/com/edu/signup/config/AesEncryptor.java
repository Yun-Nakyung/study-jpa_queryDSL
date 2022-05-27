package com.edu.signup.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AesEncryptor {

    private String key = "12345678901234567890123456789012";
    private String iv = key.substring(0,16);


    public String encrypt(String plainText) throws Exception{
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        c.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

        String enText = new String(Base64.getEncoder().encodeToString(c.doFinal(plainText.getBytes(
            StandardCharsets.UTF_8))));
        return enText;
    }


    public String decrypt(String cipherText) throws Exception{

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

        c.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

        return new String(c.doFinal(Base64.getDecoder().decode(cipherText)),"UTF-8");
    }


}
