package com.molecoding.nobs;

import com.google.common.io.BaseEncoding;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Arrays;
import java.util.stream.Collectors;

public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");
  }

  SecretKey key(String keyHex) throws Exception {
    byte[] keyBytes = BaseEncoding.base16().decode(keyHex.toUpperCase());

    return SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(keyBytes));
  }

  byte[] padding(byte[] bytes) {
    int len = bytes.length + (bytes.length % 8 == 0 ? 0 : (8 - bytes.length % 8));
    System.out.printf("padding %d to %d\n", bytes.length, len);
    return Arrays.copyOf(bytes, len);
  }

  public String encrypt(String message, String keyHex) throws Exception {
    byte[] bytes = padding(message.getBytes());
    SecretKey secretKey = key(keyHex);

//    IvParameterSpec iv = new IvParameterSpec(keyBytes);

    Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

    desCipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] textEncrypted = desCipher.doFinal(bytes);

    return BaseEncoding.base16().encode(textEncrypted);
  }

  public String decrypt(String encryptedHex, String key) throws Exception {
    SecretKey secretKey = key(key);
    Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

    byte[] encrypted = BaseEncoding.base16().decode(encryptedHex);

    desCipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decrypted = desCipher.doFinal(encrypted);

    return BaseEncoding.base16().encode(decrypted);
  }


  public String crc(String hex) {
    String asciiHex = hex.chars().mapToObj(c -> String.format("%02x", c)).collect(Collectors.joining());

    return Integer.toHexString(ModbusCrc.gen(BaseEncoding.base16().decode(asciiHex))).toUpperCase();
  }
}
