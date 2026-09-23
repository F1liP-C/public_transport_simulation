package kolejka;

import komunikacja.Przystanek;
import komunikacja.Tramwaj;

// Zdarzenie związane z przyjazdem/odjazdem tramwaju
public abstract class ZdarzenieTramwaj extends Zdarzenie {
    protected Tramwaj tramwaj;
    protected Przystanek przystanek;
    protected int idPrzystanku;

    public ZdarzenieTramwaj(int idPrzystanku, Przystanek przystanek, int czas,
                            Tramwaj tramwaj) {
        super(czas);
        this.idPrzystanku = idPrzystanku;
        this.tramwaj = tramwaj;
        this.przystanek = przystanek;
    }
}
