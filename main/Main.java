package main;

public class Main {
    public static void main(String[] args) {
        Symulacja symulacja = new Symulacja();
        symulacja.wczytajDane();
        symulacja.wypiszDane();
        symulacja.symuluj();
        symulacja.zwrocRaport();
    }
}
