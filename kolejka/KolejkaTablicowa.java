package kolejka;

import java.util.Arrays;

// Tablicowa implementacja kolejki zdarzeń
public class KolejkaTablicowa implements KolejkaZdarzen {
    private static final int POCZATKOWA_POJEMNOSC = 10;

    private Zdarzenie[] zdarzenia;
    private int rozmiar;

    public KolejkaTablicowa() {
        this.zdarzenia = new Zdarzenie[POCZATKOWA_POJEMNOSC];
        this.rozmiar = 0;
    }

    // Wstawienie zdarzenia na koniec kolejki
    public void wrzucNaKoniec(Zdarzenie z) {
        if (rozmiar < zdarzenia.length) {
            zdarzenia[rozmiar] = z;
            rozmiar++;
        }
        else {
            // W przypadku zapełnienia kolejki podwajamy jej rozmiar
            Zdarzenie[] tmp = new Zdarzenie[2 * rozmiar];
            System.arraycopy(zdarzenia, 0, tmp, 0, rozmiar);
            tmp[rozmiar] = z;
            rozmiar++;
            zdarzenia = tmp;
        }
    }

    // Pobranie pierwszego zdarzenia z kolejki
    public Zdarzenie pobierz() {
        assert !czyPusta();
        Zdarzenie wynik = zdarzenia[0];
        System.arraycopy(zdarzenia, 1, zdarzenia, 0, rozmiar - 1);
        zdarzenia[rozmiar] = null;
        rozmiar--;
        return wynik;
    }

    // Sprawdzenie czy kolejka jest pusta
    public boolean czyPusta() {
        return rozmiar == 0;
    }

    // Sortowanie kolejki ze względu na czas zdarzenia
    public void posortujSie() {
        Arrays.sort(zdarzenia, 0, rozmiar);
    }
}
