package kolejka;

import komunikacja.Przystanek;
import komunikacja.Tramwaj;
import main.Czas;

// Zdarzenie związanie z przyjazdem tramwaju na przystanek
public class ZdarzenieTramwajPrzyjazd extends ZdarzenieTramwaj {
    public ZdarzenieTramwajPrzyjazd(int idPrzystanku, Przystanek przystanek,
                                    int czas, Tramwaj tramwaj) {
        super(idPrzystanku, przystanek, czas, tramwaj);
    }

    // Tramwaj podjeżdza na przystanek, a następnie wypuszcza pasażerów
    @Override
    public int wykonaj(int nrDnia) {
        System.out.println((nrDnia + 1) + ", " + Czas.podaj(czas) +
                ": Tramwaj linii " + tramwaj.getLinia().getNrLinii() +
                " (nr bocz. " + tramwaj.getNrBoczny() + ")" +
                " przyjechał na przystanek " + przystanek.getNazwa() + ".");
        tramwaj.wypuscPasazerow(przystanek, czas, nrDnia);
        if (tramwaj.getIdObecnego() == 0 ||
                tramwaj.getIdObecnego() == tramwaj.getLinia().getDlugoscTrasy() - 1) {
            tramwaj.changeCzyDoPrzodu();
        }
        return 0;
    }
}
