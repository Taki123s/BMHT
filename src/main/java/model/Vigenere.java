package model;

import java.util.Random;

public class Vigenere {

    private String key;
    private String alphabets;
    public String encrypt(String plaintext) {
        plaintext = plaintext.trim();
        key = key.trim();
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0, j = 0; i < plaintext.length(); i++) {
            char p = plaintext.charAt(i);
            int charIndex = alphabets.indexOf(p);
            if (charIndex == -1) {
                continue;
            }
            char k = key.charAt(j);
            int kIndex = alphabets.indexOf(k);
            if (kIndex == -1) {
                continue;
            }
            int cIndex = (charIndex + kIndex) % alphabets.length();
            char c = alphabets.charAt(cIndex);
            ciphertext.append(c);
            j++;
            if (j == key.length()) {
                j = 0;
            }
        }
        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        ciphertext = ciphertext.trim();
        key = key.trim();
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0, j = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            int cIndex = alphabets.indexOf(c);
            if (cIndex == -1) {
                continue;
            }
            char k = key.charAt(j);
            int kIndex = alphabets.indexOf(k);
            if (kIndex == -1) {
                continue;
            }
            int pIndex = (cIndex - kIndex + alphabets.length()) % alphabets.length();
            char p = alphabets.charAt(pIndex);
            plaintext.append(p);
            j++;
            if (j == key.length()) {
                j = 0;
            }
        }

        return plaintext.toString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAlphabets() {
        return alphabets;
    }

    public void setAlphabets(String alphabets) {
        this.alphabets = alphabets;
    }
    public String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabets.length());
            char c = alphabets.charAt(index);
            key.append(c);
        }
        setKey(key.toString());
        return key.toString();
    }

}

