package komunikacja;

import main.Czas;
import main.Losowanie;

// Implementacja tramwaju
public class Tramwaj extends Pojazd{
    private Pasazer[] pasazerowie;
    private boolean czyDoPrzodu;
    private int liczbaPasazerow;
    private int idObecnego;

    public Tramwaj(int nrBoczny, Linia linia, boolean czyDoPrzodu, int idObencego) {
        super(nrBoczny, linia);
        this.pasazerowie = new Pasazer[linia.getPojemnoscTramwaju()];
        this.czyDoPrzodu = czyDoPrzodu;
        this.liczbaPasazerow = 0;
        this.idObecnego = idObencego;
    }

    public boolean isCzyDoPrzodu() {
        return czyDoPrzodu;
    }

    public void changeCzyDoPrzodu() {
        czyDoPrzodu = !czyDoPrzodu;
    }

    public boolean czyPusty() {
        return liczbaPasazerow == 0;
    }

    public boolean czyPelny() {
        return liczbaPasazerow == linia.getPojemnoscTramwaju();
    }

    // Opróżnienie tramwaju na koniec dnia
    public void oproznij() {
        for (int i = 0; i < liczbaPasazerow; i++) {
            pasazerowie[i] = null;
        }
        liczbaPasazerow = 0;
    }

    public int getIdObecnego() {
        return idObecnego;
    }

    public void incIdObecnego() {
        idObecnego++;
    }

    public void decIdObecnego() {
        idObecnego--;
    }

    // Wypuszczenie pasażerów na przystanku
    public void wypuscPasazerow(Przystanek przystanek, int czas, int nrDnia) {
        int i = 0;
        while (i < liczbaPasazerow && !przystanek.czyPelny() && !czyPusty()) {
            // Znalezienie wysiadającego pasażera
            while (i < liczbaPasazerow && !pasazerowie[i].getPrzystanekDocelowy().
                    getNazwa().equals(przystanek.getNazwa())) {
                i++;
            }

            // Usunięcie pasażera z tramwaju i dodanie go do przystanku
            if (i < liczbaPasazerow) {
                przystanek.dodajPasazera(pasazerowie[i]);
                pasazerowie[i].setCzasDotarciaNaPrzystanek(czas);
                System.out.println((nrDnia + 1) + ", " + Czas.podaj(czas) +
                        ": Pasażer " + pasazerowie[i].getId() +
                        " wysiadł na przystanku " + przystanek.getNazwa() + ".");
                liczbaPasazerow--;
                pasazerowie[i] = pasazerowie[liczbaPasazerow];
                pasazerowie[liczbaPasazerow] = null;
            }
        }
    }

    // Wpuszczenie pasażerów do tramwaju
    public int wpuscPasazerow(Przystanek przystanek, int czas, int nrDnia) {
        int liczbaWsiadajacych = 0;
        while (!czyPelny() && !przystanek.czyPusty()) {
            // Pobranie pasażera
            Pasazer pasazer = przystanek.dajPasazera();

            // Przydzielenie przystanku docelowego
            int idPrzystankuDocelowego;
            if (czyDoPrzodu)
                idPrzystankuDocelowego = Losowanie.losuj
                        (idObecnego + 1,linia.getDlugoscTrasy() - 1);
            else
                idPrzystankuDocelowego = Losowanie.losuj(0, idObecnego - 1);
            pasazer.setPrzystanekDocelowy(
                    linia.getPrzystanek(idPrzystankuDocelowego));

            // Dodanie pasażera do tramwaju
            pasazer.setCzasOczekiwania(
                    czas - pasazer.getCzasDotarciaNaPrzystanek());
            pasazerowie[liczbaPasazerow] = pasazer;
            liczbaPasazerow++;
            liczbaWsiadajacych++;
            System.out.println((nrDnia + 1) + ", " + Czas.podaj(czas) +
                    ": Pasażer " + pasazer.getId() +
                    " wsiadł do tramwaju linii " + getLinia().getNrLinii() +
                    " (nr boczny " + getNrBoczny() + ")" +
                    " z zamiarem dojechania do przystanku " +
                    pasazer.getPrzystanekDocelowy().getNazwa() + ".");
        }
        return liczbaWsiadajacych;
    }
}
