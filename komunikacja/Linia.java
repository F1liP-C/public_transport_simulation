package komunikacja;

// Implementacja linii tramwajowej
public class Linia {
    private int nrLinii;
    private int liczbaTramwajow;
    private int pojemnoscTramwaju;
    private Tramwaj[] tramwaje;
    private int dlugoscTrasy;
    private Przystanek[] przystankiNaTrasie;
    private int[] czasyDojazdu;
    private int odstep;

    public Linia(int nrLinii, int liczbaTramwajow, int pojenoscTramwaju,
                 int dlugoscTrasy, Przystanek[] przystankiNaTrasie,
                 int[] czasyDojazdu) {
        this.nrLinii = nrLinii;
        this.liczbaTramwajow = liczbaTramwajow;
        this.pojemnoscTramwaju = pojenoscTramwaju;
        this.dlugoscTrasy = dlugoscTrasy;
        this.przystankiNaTrasie = przystankiNaTrasie;
        this.czasyDojazdu = czasyDojazdu;
        int czasCalegoPrzejazdu = 0;
        for (int i = 0; i < dlugoscTrasy; i++)
            czasCalegoPrzejazdu += czasyDojazdu[i];
        this.odstep = 2 * czasCalegoPrzejazdu / liczbaTramwajow;
        stworzTramwaje();
    }

    private void stworzTramwaje() {
        tramwaje = new Tramwaj[liczbaTramwajow];
        for (int i = 0; i < liczbaTramwajow; i++) {
            boolean czyDoPrzodu = (i % 2 == 0);
            int idObecnego;
            if (czyDoPrzodu) {
                idObecnego = 0;
            }
            else {
                idObecnego = dlugoscTrasy - 1;
            }
            tramwaje[i] = new Tramwaj(i, this, czyDoPrzodu, idObecnego);
        }
    }

    public int getNrLinii() {
        return nrLinii;
    }

    public int getLiczbaTramwajow() {
        return liczbaTramwajow;
    }

    public int getPojemnoscTramwaju() {
        return pojemnoscTramwaju;
    }

    public Tramwaj getTramwaj(int id) {
        return tramwaje[id];
    }

    public int getDlugoscTrasy() {
        return dlugoscTrasy;
    }

    public Przystanek getPrzystanek(int i) {
        return przystankiNaTrasie[i];
    }

    public int getCzasDojazdu(int i) {
        return czasyDojazdu[i];
    }

    public int getOdstep() {
        return odstep;
    }
}
