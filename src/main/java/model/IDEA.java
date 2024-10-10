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

public class IDEA extends AEncryptionAlgorithm implements IEncryptionAlgorithm{
    public IDEA() {
        validKeySize = new String[]{"128"};
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public String encryptText(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        if(key==null) return "";
        Cipher cipher = Cipher.getInstance("IDEA", "BC");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] before = text.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString( cipher.doFinal(before));

    }

    @Override
    public String decryptText(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        if(key==null) return "";
        Cipher cipher = Cipher.getInstance("IDEA", "BC");
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] data = Base64.getDecoder().decode(text);
        return new String(cipher.doFinal(data),StandardCharsets.UTF_8);
    }

    @Override
    public boolean encryptFile(String srcFile, String destFile) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        File input = new File(srcFile);
        if(input.exists()){
            if(input.isFile()){
                Cipher cipher = Cipher.getInstance("IDEA", "BC");
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

    @Override
    public boolean decryptFile(String srcFile, String destFile) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        File input = new File(srcFile);
        if(input.exists()){
            if(input.isFile()){
                Cipher cipher = Cipher.getInstance("IDEA", "BC");
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
                System.out.println("decrypted");
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public SecretKey generateKey(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("IDEA", "BC");
        keyGenerator.init(keySize);
        setKey(keyGenerator.generateKey());
        return key;
    }

    @Override
    public void importKey(String keyInput){
        if (keyInput.trim().equals("")) return;
        byte[] decodedKeyBytes = Base64.getDecoder().decode(keyInput);
        SecretKey secretKey = new SecretKeySpec(decodedKeyBytes, "IDEA");
        setKey(secretKey);
    }

    @Override
    public String exportKey() {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
