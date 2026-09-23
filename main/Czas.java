package main;

public class Czas {
    // Wypisanie czasu w formacie godzina:minuta
    public static String podaj(int czas) {
        int godzina = czas / 60;
        int minuta = czas % 60;
        if (minuta < 10) {
            return godzina + ":0" + minuta;
        }
        else {
            return godzina + ":" + minuta;
        }
    }
}
