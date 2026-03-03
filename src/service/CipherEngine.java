package service;

public class CipherEngine {

    public int calculateShift(String masterPassword) {
        int total = 0;
        for (int i = 0; i < masterPassword.length(); i++) {
            total = total + masterPassword.charAt(i);
        }
        total = total % 95;
        return total;
    }

    public String encrypt(String masterPassword, int shift) {
        StringBuilder enPass = new StringBuilder();
        for (int i = 0; i < masterPassword.length(); i++) {
            char currentChar = masterPassword.charAt(i);
            char encryptedChar = (char) ((((currentChar - 32) + shift) % 95) + 32) ;
            enPass.append(encryptedChar);
        }
        return enPass.toString();
    }

    public String decrypt(String EncryptedPass, int shift) {
        StringBuilder dePass = new StringBuilder();
        for (int i = 0; i < EncryptedPass.length(); i++) {
            char currentChar = EncryptedPass.charAt(i);
            int val = (currentChar - 32 - shift);
            while (val < 0) {
                val += 95;
            }
            char decryptedChar = (char) ((val % 95) + 32);
            dePass.append(decryptedChar);
        }
        return dePass.toString();
    }
}
