package kolejka;

import komunikacja.Przystanek;
import komunikacja.Tramwaj;
import main.Czas;

// Zdarzenie związanie z odjazdem tramwaju z przystanku
public class ZdarzenieTramwajOdjazd extends ZdarzenieTramwaj {
    public ZdarzenieTramwajOdjazd(int idPrzystanku ,Przystanek przystanek,
                                  int czas, Tramwaj tramwaj) {
        super(idPrzystanku, przystanek, czas, tramwaj);
    }

    // Tramwaj wpuszcza pasażerów czekających na przystanku, a następnie odjeżdza
    @Override
    public int wykonaj(int nrDnia) {
        int tmp = tramwaj.wpuscPasazerow(przystanek, czas, nrDnia);
        System.out.println((nrDnia + 1) + ", " + Czas.podaj(czas) +
                ": Tramwaj linii " + tramwaj.getLinia().getNrLinii() +
                " (nr bocz. " + tramwaj.getNrBoczny() + ")" +
                " odjechał z przystanku " + przystanek.getNazwa() + ".");
        if(tramwaj.isCzyDoPrzodu()) {
            tramwaj.incIdObecnego();
        }
        else {
            tramwaj.decIdObecnego();
        }
        return tmp;
    }
}
