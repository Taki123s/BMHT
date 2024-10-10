package model;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    public String[] validKeySize;

    public RSA() {
        validKeySize = new String[]{"1024","2048","4096"};
    }

    public void generateKey(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(keySize);
        keyPair = keyGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }
    public String encryptText(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] output = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(output);

    }
    public String decryptText(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] output = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(output,StandardCharsets.UTF_8);

    }

    public void encryptFile(String inputPath, String outputPath) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
           KeyGenerator keyGen = KeyGenerator.getInstance("AES");
           keyGen.init(256);
           byte[] iv = new byte[16];
           IvParameterSpec spec = new IvParameterSpec(iv);
           SecretKey secretKey = keyGen.generateKey();
           Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
           cipher.init(Cipher.ENCRYPT_MODE,secretKey,spec);
           CipherInputStream inputStream = new CipherInputStream(new BufferedInputStream(new FileInputStream(inputPath)),cipher);
           DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputPath)));
           String keyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println(keyString);
           dataOutputStream.writeUTF(encryptText(keyString));
           dataOutputStream.writeLong(new File(inputPath).length());
           dataOutputStream.writeUTF(Base64.getEncoder().encodeToString(iv));
           byte[] byteRead = new byte[1024];
           int data;
           while((data=inputStream.read(byteRead))!=-1){
               dataOutputStream.write(byteRead,0,data);
           }
           inputStream.close();
           dataOutputStream.flush();
           dataOutputStream.close();

    }
    public void decryptFile(String inputPath, String outputPath) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(inputPath)));
        String keyString = dataInputStream.readUTF();
        long size = dataInputStream.readLong();
        byte[] iv = Base64.getDecoder().decode(dataInputStream.readUTF());
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(decryptText(keyString)),"AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,secretKey,new IvParameterSpec(iv));
        CipherInputStream cis = new CipherInputStream(dataInputStream,cipher);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputPath));
        byte[] byteRead = new byte[1024];
        System.out.println(keyString);
        int data;
        while((data=cis.read(byteRead))!=-1){
            bufferedOutputStream.write(byteRead,0,data);
        }
        cis.close();
        bufferedOutputStream.close();

    }

    public String exportPublicKey() {
        return publicKey==null?"":Base64.getEncoder().encodeToString(publicKey.getEncoded());

    }
    public String exportPrivateKey() {

        return privateKey==null?"":Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }
    public void importPublicKey(String keyInput) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (keyInput.trim().equals("")) return;
        byte[] publicKeyBytes = Base64.getDecoder().decode(keyInput);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        this.publicKey = keyFactory.generatePublic(keySpec);
    }
    public void importPrivateKey(String keyInput) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (keyInput.trim().equals("")) return;
        byte[] privateKeyBytes = Base64.getDecoder().decode(keyInput);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        this.privateKey= keyFactory.generatePrivate(keySpec);
    }
    public String[] getValidKeySize() {
        return validKeySize;
    }


}
