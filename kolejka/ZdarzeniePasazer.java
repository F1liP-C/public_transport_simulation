package kolejka;

import komunikacja.Pasazer;
import main.Czas;

// Zdarzenie związane z dotarciem pasażera na przystanek
public class ZdarzeniePasazer extends Zdarzenie {
    private Pasazer pasazer;
    public ZdarzeniePasazer(int czas, Pasazer pasazer) {
        super(czas);
        this.pasazer = pasazer;
    }

    // Pasażer zajmuje miejsce na przystanku, jeśli nie jest on pełny
    @Override
    public int wykonaj(int nrDnia) {
        if (!pasazer.getNajblizszyPrzystanek().czyPelny()) {
            pasazer.getNajblizszyPrzystanek().dodajPasazera(pasazer);
            System.out.println((nrDnia + 1) + ", " + Czas.podaj(czas) +
                    ": Pasażer " + pasazer.getId() + " dotarł na przystanek " +
                    pasazer.getNajblizszyPrzystanek().getNazwa() + ".");
        }
        return 0;
    }
}
