package model;

import java.util.Arrays;
import java.util.Random;

public class HillCipher {
    public static final String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ !@#$%^&*()-_=+[{]}|;:'\\\",<.>/?`~\n";
    public static final String VIETNAMESE_ALPHABET = "aáàảãạăắằẳẵặâấầẩẫậbcdđeéèẻẽẹêếềểễệghiíìỉĩịklmnoóòỏõọôốồổỗộơởỡớờợpqrstuúùủũụưứừửữựvxyýỳỷỹỵ1234567890AÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬBCDĐEÉÈẺẼẸÊẾỀỂỄỆGHIÍÌỈĨỊKLMNOÓÒỎÕỌÔỐỒỔỖỘƠỞỠỚỜỢPQRSTUÚÙỦŨỤƯỨỪỬỮỰVXYÝỲỶỸỴ !@#$%^&*()-_=+[{]}|;:'\\\",<.>/?`~\n";
    private String alphabets;
    private int[] keyMatrix;


    public String encrypt(String msg) {
        int matSize = (int) Math.sqrt(keyMatrix.length);
        int i;
        StringBuilder c = new StringBuilder();
        if (msg.length() == 0) {
            throw new Error("Dữ liệu đầu vào không được bỏ trống");
        }
        if (keyMatrix.length < (matSize * matSize)) {
            throw new Error("Ma trận đầu vào không đúng kích thước");

        }
        int determinant = getDeterminant(keyMatrix);
        if (determinant == 0 || gcd(alphabets.length(), determinant) > 1) {
            throw new Error("Ma trận đầu vào không phù hợp");
        }
        int needPadding =msg.length()%matSize==0?0: matSize-(msg.length()%matSize);
        int[] messageMatrix = messageToMatrix(msg,alphabets,needPadding);
        int len = messageMatrix.length;
        if (matSize == 2) {
            for (i = 0; i < len; i += 2) {
                c.append(getChar((((messageMatrix[i] * keyMatrix[0]) + (messageMatrix[i+1] * keyMatrix[1])) % alphabets.length()),alphabets));
                c.append(getChar((((messageMatrix[i] * keyMatrix[2]) + (messageMatrix[i+1] * keyMatrix[3])) % alphabets.length()),alphabets));
            }
        } else {

            for (i = 0; i < len; i += 3) {
                c.append(getChar((((messageMatrix[i] * keyMatrix[0]) + (messageMatrix[i+1] * keyMatrix[1])+(messageMatrix[i+2] * keyMatrix[2])) % alphabets.length()),alphabets));
                c.append(getChar((((messageMatrix[i] * keyMatrix[3]) + (messageMatrix[i+1] * keyMatrix[4])+(messageMatrix[i+2] * keyMatrix[5])) % alphabets.length()),alphabets));
                c.append(getChar((((messageMatrix[i] * keyMatrix[6]) + (messageMatrix[i+1] * keyMatrix[7])+(messageMatrix[i+2] * keyMatrix[8])) % alphabets.length()),alphabets));

            }
        }

        return c.toString();
    }
    public String decrypt(String msg) {
        int matSize = (int) Math.sqrt(keyMatrix.length);
        int i;
        StringBuilder c = new StringBuilder();
        if (msg.length() == 0) {
            return "";
        }
        if (keyMatrix.length < (matSize * matSize)) {
            return "";
        }
        int determinant = getDeterminant(keyMatrix);
        if (determinant == 0 || gcd(alphabets.length(), determinant) > 1) {
            return "";
        }
        int needPadding =msg.length()%matSize==0?0: matSize-(msg.length()%matSize);
        int[] messageMatrix = messageToMatrix(msg,alphabets,needPadding);
        int len = messageMatrix.length;
        int detInverse = 0;
        for (int j = 1; j <= alphabets.length(); j++) {
            if ((j * determinant) % alphabets.length() == 1) {
                detInverse = j;
            }
        }
        int[] adjugate = new int[keyMatrix.length];
        if (matSize == 2) {
            adjugate[0] = keyMatrix[3];
            adjugate[1] = (-1 * keyMatrix[1]) + alphabets.length();
            adjugate[2] = (-1 * keyMatrix[2]) + alphabets.length();
            adjugate[3] = keyMatrix[0];
        } else {
            int[] adj1 = new int[4];
            int[] adj2 = new int[4];
            int[] adj3 = new int[4];
            int[] adj4 = new int[4];
            int[] adj5 = new int[4];
            int[] adj6 = new int[4];
            int[] adj7 = new int[4];
            int[] adj8 = new int[4];
            int[] adj9 = new int[4];
            adj1[0] = keyMatrix[4];
            adj1[1] = keyMatrix[5];
            adj1[2] = keyMatrix[7];
            adj1[3] = keyMatrix[8];
            adj2[0] = keyMatrix[1];
            adj2[1] = keyMatrix[2];
            adj2[2] = keyMatrix[7];
            adj2[3] = keyMatrix[8];
            adj3[0] = keyMatrix[1];
            adj3[1] = keyMatrix[2];
            adj3[2] = keyMatrix[4];
            adj3[3] = keyMatrix[5];
            adj4[0] = keyMatrix[3];
            adj4[1] = keyMatrix[5];
            adj4[2] = keyMatrix[6];
            adj4[3] = keyMatrix[8];
            adj5[0] = keyMatrix[0];
            adj5[1] = keyMatrix[2];
            adj5[2] = keyMatrix[6];
            adj5[3] = keyMatrix[8];
            adj6[0] = keyMatrix[0];
            adj6[1] = keyMatrix[2];
            adj6[2] = keyMatrix[3];
            adj6[3] = keyMatrix[5];
            adj7[0] = keyMatrix[3];
            adj7[1] = keyMatrix[4];
            adj7[2] = keyMatrix[6];
            adj7[3] = keyMatrix[7];
            adj8[0] = keyMatrix[0];
            adj8[1] = keyMatrix[1];
            adj8[2] = keyMatrix[6];
            adj8[3] = keyMatrix[7];
            adj9[0] = keyMatrix[0];
            adj9[1] = keyMatrix[1];
            adj9[2] = keyMatrix[3];
            adj9[3] = keyMatrix[4];
            adjugate[0] = getDeterminant(adj1);
            adjugate[1] = -1 * getDeterminant(adj2) + alphabets.length();
            adjugate[2] = getDeterminant(adj3);
            adjugate[3] = -1 * getDeterminant(adj4) + alphabets.length();
            adjugate[4] = getDeterminant(adj5);
            adjugate[5] = -1 * getDeterminant(adj6) + alphabets.length();
            adjugate[6] = getDeterminant(adj7);
            adjugate[7] = -1 * getDeterminant(adj8) + alphabets.length();
            adjugate[8] = getDeterminant(adj9);
        }
        int[] matrxInverse = new int[adjugate.length];
        for (int k = 0; k < adjugate.length; k++) {
            matrxInverse[k] = (adjugate[k] * detInverse) % alphabets.length();
        }
        if (matSize == 2) {
            for (i = 0; i < len; i += 2) {
                c.append(getChar((((messageMatrix[i] * matrxInverse[0]) + (messageMatrix[i+1] * matrxInverse[1])) % alphabets.length()),alphabets));
                c.append(getChar((((messageMatrix[i] * matrxInverse[2]) + (messageMatrix[i+1] * matrxInverse[3])) % alphabets.length()),alphabets));
            }
        } else {
            for (i = 0; i < len; i += 3) {
                c.append(getChar((((messageMatrix[i] * matrxInverse[0]) + (messageMatrix[i+1] * matrxInverse[1])+(messageMatrix[i+2] * matrxInverse[2])) % alphabets.length()),alphabets));
                c.append(getChar((((messageMatrix[i] * matrxInverse[3]) + (messageMatrix[i+1] * matrxInverse[4])+(messageMatrix[i+2] * matrxInverse[5])) % alphabets.length()),alphabets));
                c.append(getChar((((messageMatrix[i] * matrxInverse[6]) + (messageMatrix[i+1] * matrxInverse[7])+(messageMatrix[i+2] * matrxInverse[8])) % alphabets.length()),alphabets));

            }
        }

        return c.toString();

    }
    public int[] messageToMatrix(String message,String alphabets,int needPadding){
           int[] result =  new int[message.length()+needPadding];
            for(int m = 0;m<needPadding;m++){
                result[message.length()+m] = 23;
            }
        for(int i=0;i<message.length();i++) {
            result[i] = indexOfChar(message.charAt(i), alphabets);
        }

        return result;
    }

    public int gcd(int a, int b) {
        if (b != 0) {
            return gcd(b, a % b);
        } else {
            return Math.abs(a);
        }
    }

    public int getModuloRemainder(int num, int modulo) {
        return ((num % modulo) + modulo) % modulo;
    }

    public int getDeterminant(int[] matKey) {
        int det;
        if (matKey.length == 4) {
            det = matKey[0] * matKey[3] - matKey[1] * matKey[2];
        } else {
            det = matKey[0] * matKey[4] * matKey[8] + matKey[1] * matKey[5] * matKey[6] + matKey[2] * matKey[3] * matKey[7];
            det -= (matKey[2] * matKey[4] * matKey[6] + matKey[0] * matKey[5] * matKey[7] + matKey[1] * matKey[3] * matKey[8]);
        }

        det = getModuloRemainder(det, alphabets.length());

        return det;
    }

    public int indexOfChar(char c,String alphabet){
        return alphabet.indexOf(c);
    }
    public char getChar(int index,String alphabet){
        return  alphabet.charAt(index);
    }

    public int[] generateRandomMatrix(int matSize,int limit) {
        Random random = new Random();
        int[] matrix = new int[matSize*matSize];
        int det;
        int gcd;
        do {
           for(int i=0;i<matrix.length;i++){
               matrix[i] = random.nextInt(limit);
           }
            det = getDeterminant(matrix);
            gcd = gcd(alphabets.length(), det);
        } while (det == 0 || gcd > 1);
        setKeyMatrix(matrix);
        return matrix;
    }

    public String getAlphabets() {
        return alphabets;
    }

    public void setAlphabets(String alphabets) {
        this.alphabets = alphabets;
    }

    public int[] getKeyMatrix() {
        return keyMatrix;
    }

    public void setKeyMatrix(int[] keyMatrix) {
        this.keyMatrix = keyMatrix;
    }

//    public static void main(String[] args) {
//        HillCipher algo = new HillCipher();
//        int[] key= new int[]{3,1,1,2};
////        int[] key3 = new int[]{25,15,20,7,8,22,7,13,13};
//        int[] key3 = new int[]{31,20,16,12,47,43,16,46,25};
//        String msg ="concac s";
//        String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ !@#$%^&*()-_=+[{]}|;:'\\\",<.>/?`~\n";
//        String VIETNAMESE_ALPHABET = "aáàảãạăắằẳẵặâấầẩẫậbcdđeéèẻẽẹêếềểễệghiíìỉĩịklmnoóòỏõọôốồổỗộơởỡớờợpqrstuúùủũụưứừửữựvxyýỳỷỹỵ1234567890AÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬBCDĐEÉÈẺẼẸÊẾỀỂỄỆGHIÍÌỈĨỊKLMNOÓÒỎÕỌÔỐỒỔỖỘƠỞỠỚỜỢPQRSTUÚÙỦŨỤƯỨỪỬỮỰVXYÝỲỶỸỴ !@#$%^&*()-_=+[{]}|;:'\\\",<.>/?`~\n";
//        algo.setKeyMatrix(key);
//        algo.setAlphabets(VIETNAMESE_ALPHABET);
//        System.out.println(algo.encrypt(msg));
//        System.out.println(algo.decrypt(algo.encrypt(msg)));
//
//
//
//    }
}

