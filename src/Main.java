import service.CipherEngine;

public class Main {
    public static void main(String[] args) {
        CipherEngine test = new CipherEngine();

        IO.println("Shift: " + test.calculateShift("DumenSifre"));
        int laShift = test.calculateShift("DumenSifre");
        IO.println("Encrypt: " + test.encrypt("DumenSifre", laShift));

        String Crypted = test.encrypt("DumenSifre", laShift);
        IO.println("Decrypt: " + test.decrypt(Crypted, laShift));
    }
}
