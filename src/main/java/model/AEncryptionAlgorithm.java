package model;

import javax.crypto.SecretKey;

public abstract class AEncryptionAlgorithm {
    protected SecretKey key;
    protected String[] validKeySize;
    public AEncryptionAlgorithm() {
    }

    public String[] getValidKeySize() {
        return validKeySize;
    }

    public void setValidKeySize(String[] validKeySize) {
        this.validKeySize = validKeySize;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }




    @Override
    public String toString() {
        return "AEncryptionAlgorithm{" +
                ", key=" + key ;
    }
}
