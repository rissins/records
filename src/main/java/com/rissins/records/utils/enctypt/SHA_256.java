package com.rissins.records.utils.enctypt;

import lombok.experimental.UtilityClass;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA_256 {


    public String encryptBySha256(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());

        return bytesToHex(md.digest());
    }


    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

//    public static void main(String[] args) throws NoSuchAlgorithmException {
//        Encrypt encrypt = new Encrypt();
//        String s = encrypt.encryptBySha256("1234");
//        System.out.println("s = " + s);
//
//    }

}


