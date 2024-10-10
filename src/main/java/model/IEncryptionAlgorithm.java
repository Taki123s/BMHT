package model;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public interface IEncryptionAlgorithm {
    public String encryptText(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException;
    public String decryptText(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException;
    public boolean encryptFile(String srcFile,String destFile) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException;
    public boolean decryptFile(String srcFile,String destFile) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException;
    public SecretKey generateKey(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException;
    public void importKey(String keyInput);
    public String exportKey();
    public String[] getValidKeySize();
}
