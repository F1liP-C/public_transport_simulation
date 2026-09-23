package komunikacja;


// Implementacja pojazdu komunikacji miejskiej
public abstract class Pojazd {
    protected int nrBoczny;
    protected Linia linia;

    public Pojazd(int nrBoczny, Linia linia) {
        this.nrBoczny = nrBoczny;
        this.linia = linia;
    }

    public int getNrBoczny() {
        return nrBoczny;
    }

    public Linia getLinia() {
        return linia;
    }
}
