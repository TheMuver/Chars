package com.muver.chars.lib;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

public class DesEncoder {
    public static String encoding(String mess, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKey desKey = new SecretKeySpec(key.getBytes(), "DES");
        Cipher desCipher = Cipher.getInstance("DES");
        desCipher.init(Cipher.ENCRYPT_MODE, desKey);
        byte[] byte_enc = desCipher.doFinal(mess.getBytes());
        String output = "";
        for (byte b: byte_enc) {
            System.out.print(b + " ");
            output += Integer.toString(b + 1127).substring(1);
        }
        return output;
    }

    public static String decoding(String mess, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKey desKey = new SecretKeySpec(key.getBytes(), "DES");
        Cipher desCipher = Cipher.getInstance("DES");
        desCipher.init(Cipher.DECRYPT_MODE, desKey);
        byte[] byte_enc = new byte[mess.length()/3];
        for (int i = 0; i < mess.length()/3; i += 1) {
            byte_enc[i] = (byte)(Integer.parseInt(mess.substring(i*3, i*3+3)) - 127);
            System.out.print(byte_enc[i]+ " ");
        }
        return new String(desCipher.doFinal(byte_enc), StandardCharsets.UTF_8);
    }
}
