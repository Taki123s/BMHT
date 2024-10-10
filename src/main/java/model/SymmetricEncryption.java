package model;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Base64;
import java.util.HashMap;

public class SymmetricEncryption {
    public static final HashMap<String,String[]> symmetricAlgorithm = new HashMap<>();
    public static String DES,AES,TripleDES,Blowfish,IDEA,Camellia;
    public static String currentAlgorithm;
    static {
        DES = "DES";
        AES = "AES";
        TripleDES = "DESede";
        Blowfish = "Blowfish";
        IDEA = "IDEA";
        Camellia = "Camellia";

        symmetricAlgorithm.put(DES,new String[]{"56"});
        symmetricAlgorithm.put(AES,new String[]{"128", "192", "256"});
        symmetricAlgorithm.put(TripleDES,new String[]{"112","168"});
        symmetricAlgorithm.put(Blowfish,new String[]{"128"});
        symmetricAlgorithm.put(IDEA,new String[]{"128"});
        symmetricAlgorithm.put(Camellia,new String[]{"128","192","256"});
        currentAlgorithm = DES;
    }
    public SymmetricEncryption() {
    Security.addProvider(new BouncyCastleProvider());
    }

    public String encryptText(String text, String base64Key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        if(base64Key==null) return "";
        SecretKey key = importKey(base64Key);
        Cipher cipher = Cipher.getInstance(currentAlgorithm,"BC");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] before = text.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(cipher.doFinal(before));

    }
    public String decryptText(String text,String base64Key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        if(base64Key==null) return "";
        SecretKey key = importKey(base64Key);
        Cipher cipher = Cipher.getInstance(currentAlgorithm,"BC");
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] data = Base64.getDecoder().decode(text);
        return new String(cipher.doFinal(data),StandardCharsets.UTF_8);
    }
    public boolean encryptFile(String srcFile, String destFile,String base64Key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        File input = new File(srcFile);
        if(new File(destFile).exists()) return false;
        SecretKey key = importKey(base64Key);
        if(input.exists()){
            if(input.isFile()){
                Cipher cipher = Cipher.getInstance(currentAlgorithm,"BC");
                cipher.init(Cipher.ENCRYPT_MODE,key);
                BufferedInputStream bIps = new BufferedInputStream(new FileInputStream(input));
                BufferedOutputStream bOps = new BufferedOutputStream(new FileOutputStream(destFile));
                byte[] byteRead = new byte[64000];
                int data = -1;
                while((data=bIps.read(byteRead))>-1){
                    byte[] encryptData = cipher.update(byteRead,0,data);
                    if(encryptData!=null) bOps.write(encryptData);
                }
                byte[] finalBytes = cipher.doFinal();
                bOps.write(finalBytes);
                bIps.close();
                bOps.flush();
                bOps.close();
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }

    }

    public boolean decryptFile(String srcFile, String destFile,String base64Key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        File input = new File(srcFile);
        if(new File(destFile).exists()) return false;
        SecretKey key = importKey(base64Key);
        if(input.exists()){
            if(input.isFile()){
                Cipher cipher = Cipher.getInstance(currentAlgorithm,"BC");
                cipher.init(Cipher.DECRYPT_MODE,key);
                BufferedInputStream bIps = new BufferedInputStream(new FileInputStream(input));
                BufferedOutputStream bOps = new BufferedOutputStream(new FileOutputStream(destFile));
                byte[] byteRead = new byte[64000];
                int data = -1;
                while((data=bIps.read(byteRead))>-1){
                    byte[] encryptData = cipher.update(byteRead,0,data);
                    if(encryptData!=null) bOps.write(encryptData);
                }
                byte[] finalBytes = cipher.doFinal();
                bOps.write(finalBytes);
                bIps.close();
                bOps.flush();
                bOps.close();
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }

    }

    public SecretKey generateKey(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator generator = KeyGenerator.getInstance(currentAlgorithm,"BC");
        generator.init(keySize);
        return generator.generateKey();
    }
    public SecretKey importKey(String keyInput){
        if(keyInput.trim().equals("")) return null;
        byte[] decodedKeyBytes = Base64.getDecoder().decode(keyInput);
        return new SecretKeySpec(decodedKeyBytes, currentAlgorithm);
    }
    public String exportKey(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

}
