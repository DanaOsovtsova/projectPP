package bsu.labs.ArithmeticsApp.encryptor;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileEncryptor {
    private static final int BUFFER_SIZE = 8192;
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private SecretKey secretKey;
    private IvParameterSpec ivParameterSpec;

    public FileEncryptor(String key) {
        try {
            byte[] keyBytes = key.getBytes("UTF-8");
            secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
            ivParameterSpec = new IvParameterSpec(keyBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void encryptFile(String inputFile, String outputFile) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            try (FileInputStream fis = new FileInputStream("src/main/resources/" + inputFile);
                 FileOutputStream fos = new FileOutputStream("src/main/resources/" + outputFile);
                 CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    cos.write(buffer, 0, bytesRead);
                }
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IOException e) {
            e.printStackTrace();
        }
    }

    public void decryptFile(String inputFile, String outputFile) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

            try (FileInputStream fis = new FileInputStream("src/main/resources/" + inputFile);
                 CipherInputStream cis = new CipherInputStream(fis, cipher);
                 FileOutputStream fos = new FileOutputStream("src/main/resources/" + outputFile)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = cis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IOException e) {
            e.printStackTrace();
        }
    }
}