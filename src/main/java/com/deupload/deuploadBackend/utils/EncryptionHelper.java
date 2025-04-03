package com.deupload.deuploadBackend.utils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptionHelper {
    private static final String RSA = "RSA";

    public static byte[] encryptFile(byte[] fileData, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(fileData);
    }

    public static byte[] decryptFile(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedData);
    }

    public PublicKey getPublicKey() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode("PUBLIC_KEY_STRING");
        return KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(keyBytes));
    }

    public PrivateKey getPrivateKey() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode("PRIVATE_KEY_STRING");
        return KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
    }
}
