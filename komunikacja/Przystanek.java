package komunikacja;

// Implementacja przystanku tramwajowego
public class Przystanek {
    private static final int KONIEC_DNIA = 1440; // Godzina 24:00

    private String nazwa;
    private int pojemnosc;
    private Pasazer[] pasazerowie;
    private int liczbaPasazerow;

    public Przystanek(String nazwa, int pojemnosc) {
        this.nazwa = nazwa;
        this.pojemnosc = pojemnosc;
        this.pasazerowie = new Pasazer[pojemnosc];
        this.liczbaPasazerow = 0;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getPojemnosc() {
        return pojemnosc;
    }

    public int getLiczbaPasazerow() {
        return liczbaPasazerow;
    }

    // Dodanie pasażera do przystanku
    public void dodajPasazera(Pasazer pasazer) {
        assert liczbaPasazerow < pojemnosc;
        pasazer.incLiczbaOczekiwan();
        pasazerowie[liczbaPasazerow] = pasazer;
        liczbaPasazerow++;
    }

    // Usunięcie pasażera z przystanku
    public Pasazer dajPasazera() {
        assert liczbaPasazerow > 0;
        liczbaPasazerow--;
        return pasazerowie[liczbaPasazerow];
    }

    public boolean czyPelny() {
        return liczbaPasazerow == pojemnosc;
    }

    public boolean czyPusty() {
        return liczbaPasazerow == 0;
    }

    // Opróżnienie przystanku na koniec dnia
    public void oproznij() {
        for(int i = 0; i < liczbaPasazerow; i++) {
            pasazerowie[i].setCzasOczekiwania(
                    KONIEC_DNIA - pasazerowie[i].getCzasDotarciaNaPrzystanek());
            pasazerowie[i] = null;
        }
        liczbaPasazerow = 0;
    }
}
