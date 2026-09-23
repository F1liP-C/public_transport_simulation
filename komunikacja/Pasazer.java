package komunikacja;

// Implementacja pasażera tramwaju
public class Pasazer {
    private int id;
    private Przystanek najblizszyPrzystanek;
    private Przystanek przystanekDocelowy;
    private int czasDotarciaNaPrzystanek;
    private int czasOczekiwania;
    private int liczbaOczekiwan;

    public Pasazer(int id, Przystanek przystanek) {
        this.id = id;
        this.najblizszyPrzystanek = przystanek;
        this.czasOczekiwania = 0;
        this.czasDotarciaNaPrzystanek = 0;
        this.liczbaOczekiwan = 0;
    }

    public int getId() {
        return id;
    }

    public Przystanek getNajblizszyPrzystanek() {
        return najblizszyPrzystanek;
    }

    public Przystanek getPrzystanekDocelowy() {
        return przystanekDocelowy;
    }

    public void setPrzystanekDocelowy(Przystanek przystanekDocelowy) {
        this.przystanekDocelowy = przystanekDocelowy;
    }

    public int getCzasDotarciaNaPrzystanek() {
        return czasDotarciaNaPrzystanek;
    }

    public void setCzasDotarciaNaPrzystanek(int czasDotarciaNaPrzystanek) {
        this.czasDotarciaNaPrzystanek = czasDotarciaNaPrzystanek;
    }

    public int getCzasOczekiwania() {
        return czasOczekiwania;
    }

    public void setCzasOczekiwania(int czas) {
        czasOczekiwania += czas;
    }

    public void czasOczekiwaniaZero() {
        czasOczekiwania = 0;
    }

    public int getLiczbaOczekiwan() {
        return liczbaOczekiwan;
    }

    public void incLiczbaOczekiwan() {
        liczbaOczekiwan++;
    }
}
