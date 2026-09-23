package main;

import kolejka.*;
import komunikacja.*;
import java.util.Scanner;

public class Symulacja {
    private static final int poczatekDniaSymulacji = 360; // Godzina 6:00
    private static final int koniecPrzychodzeniaNaPrzystanki = 720; // Godzina 12:00
    private static final int koniecOdjazdow = 1380; // Godzina 23:00

    private int liczbaDni;
    private int pojemnoscPrzystanku;
    private int liczbaPrzystankow;
    private Przystanek[] przystanki;
    private int pojemnoscTramwaju;
    private int liczbaPasazerow;
    private Pasazer[] pasazerowie;
    private int liczbaLinii;
    private Linia[] linie;
    private KolejkaZdarzen kolejka;
    private int wszystkiePrzejazdy;
    private int wszystkieOczekiwania;
    private long lacznyCzasOczekiwania;
    private float sredniCzasOczekiwania;
    private int[] dzienneCzasyOczekiwania;
    private int[] dzienneLiczbyPrzejazdow;

    public Symulacja() {
        this.kolejka = new KolejkaTablicowa();
        this.wszystkiePrzejazdy = 0;
        this.wszystkieOczekiwania = 0;
        this.lacznyCzasOczekiwania = 0;
        this.sredniCzasOczekiwania = 0.f;
    }

    // Wczytywanie danych
    public void wczytajDane() {
        Scanner sc = new Scanner(System.in);
        liczbaDni = sc.nextInt();
        if (liczbaDni < 0) {
            throw new IllegalArgumentException("Liczba dni nie może być ujemna.");
        }
        dzienneCzasyOczekiwania = new int[liczbaDni];
        dzienneLiczbyPrzejazdow = new int[liczbaDni];
        pojemnoscPrzystanku = sc.nextInt();
        if (pojemnoscPrzystanku < 0) {
            throw new IllegalArgumentException("Pojemność przystanku nie może być ujemna.");
        }
        liczbaPrzystankow = sc.nextInt();
        if (liczbaPrzystankow < 0) {
            throw new IllegalArgumentException("Liczba przystanków nie może być ujemna.");
        }
        przystanki = new Przystanek[liczbaPrzystankow];
        for (int i = 0; i < liczbaPrzystankow; i++) {
            String nazwa = sc.next();
            for (int j = 0; j < i; j++) {
                if (przystanki[j].getNazwa().equals(nazwa)) {
                    throw new IllegalArgumentException(
                            "Powtórzona nazwa przystanku: " + nazwa);
                }
            }
            przystanki[i] = new Przystanek(nazwa, pojemnoscPrzystanku);
        }
        liczbaPasazerow = sc.nextInt();
        if (liczbaPasazerow < 0) {
            throw new IllegalArgumentException("Liczba pasażerów nie może być ujemna.");
        }
        if (liczbaPasazerow > 0 && liczbaPrzystankow == 0) {
            throw new IllegalArgumentException(
                    "Pasażerowie wymagają co najmniej jednego przystanku.");
        }
        pasazerowie = new Pasazer[liczbaPasazerow];
        for (int i = 0; i < liczbaPasazerow; i++) {
            int nrLosowegoPrzystanku = Losowanie.losuj(0, liczbaPrzystankow - 1);
            pasazerowie[i] = new Pasazer(i, przystanki[nrLosowegoPrzystanku]);
        }
        pojemnoscTramwaju = sc.nextInt();
        if (pojemnoscTramwaju < 0) {
            throw new IllegalArgumentException("Pojemność tramwaju nie może być ujemna.");
        }
        liczbaLinii = sc.nextInt();
        if (liczbaLinii < 0) {
            throw new IllegalArgumentException("Liczba linii nie może być ujemna.");
        }
        linie = new Linia[liczbaLinii];
        for (int i = 0; i < liczbaLinii; i++) {
            int liczbaTramwajow = sc.nextInt();
            if (liczbaTramwajow <= 0) {
                throw new IllegalArgumentException("Liczba tramwajów na linii musi być dodatnia.");
            }
            int dlugoscTrasy = sc.nextInt();
            if (dlugoscTrasy < 2) {
                throw new IllegalArgumentException("Trasa musi mieć co najmniej dwa przystanki.");
            }
            Przystanek[] przystankiNaTrasie = new Przystanek[dlugoscTrasy];
            int[] czasyDojazdu = new int[dlugoscTrasy];
            for (int j = 0; j < dlugoscTrasy; j++) {
                String nazwaPrzystanku = sc.next();
                int tmp = -1;
                for (int k = 0; k < przystanki.length; k++) {
                    if (przystanki[k].getNazwa().equals(nazwaPrzystanku)) {
                        tmp = k;
                        break;
                    }
                }
                if (tmp == -1) {
                    throw new IllegalArgumentException("Nieznany przystanek: " + nazwaPrzystanku);
                }
                przystankiNaTrasie[j] = przystanki[tmp];
                czasyDojazdu[j] = sc.nextInt();
                if (j < dlugoscTrasy - 1 && czasyDojazdu[j] <= 0) {
                    throw new IllegalArgumentException("Czas przejazdu musi być dodatni.");
                }

                if (j == dlugoscTrasy - 1 && czasyDojazdu[j] < 0) {
                    throw new IllegalArgumentException("Czas postoju nie może być ujemny.");
                }
            }
            linie[i] = new Linia(i, liczbaTramwajow, pojemnoscTramwaju,
                    dlugoscTrasy, przystankiNaTrasie, czasyDojazdu);
        }
    }

    // Wypisywanie wczytanych danych
    public void wypiszDane() {
        System.out.println("WCZYTANE PARAMETRY:");
        System.out.println("- Liczba dni symulacji: " + liczbaDni);
        System.out.println("- Pojemność przystanku: " + pojemnoscPrzystanku);
        System.out.println("- Liczba przystanków: " + liczbaPrzystankow);
        System.out.print("- Przystanki: ");
        for (int i = 0; i < liczbaPrzystankow; i++) {
            System.out.print(przystanki[i].getNazwa() + " ");
        }
        System.out.println();
        System.out.println("- Liczba pasażerów: " + liczbaPasazerow);
        System.out.println("- Pojemność tramwaju: " + pojemnoscTramwaju);
        System.out.println("- Liczba linii tramwajowych: " + liczbaLinii);
        for (int i = 0; i < liczbaLinii; i++) {
            System.out.println("- Linia nr " + i + ":");
            System.out.println("  * Liczba tramwajów: " +
                    linie[i].getLiczbaTramwajow());
            System.out.println("  * Długość trasy: " +
                    linie[i].getDlugoscTrasy());
            System.out.print("  * Trasa: ");
            for (int j = 0; j < linie[i].getDlugoscTrasy(); j++) {
                System.out.print(linie[i].getPrzystanek(j).getNazwa() + " ");
                System.out.print(linie[i].getCzasDojazdu(j) + " ");
            }
            System.out.println();
        }
    }

    //Symulacja ruchu miejskiego
    public void symuluj() {
        System.out.println();
        System.out.println("SYMULACJA:");
        for (int i = 0; i < liczbaDni; i++) {
            System.out.println("Dzień " + (i + 1) + ":");

            // Wrzucenie do kolejki zdarzeń związanych z dotarciem pasażerów na przystanki
            for (int j = 0; j < liczbaPasazerow; j++) {
                int godzinaDotarcia = Losowanie.losuj(
                        poczatekDniaSymulacji, koniecPrzychodzeniaNaPrzystanki);
                pasazerowie[j].setCzasDotarciaNaPrzystanek(godzinaDotarcia);
                kolejka.wrzucNaKoniec(
                        new ZdarzeniePasazer(godzinaDotarcia, pasazerowie[j]));
            }

            // Wrzucenie do kolejki zdarzeń związanych z ruchem tramwajów
            for (int j = 0; j < liczbaLinii; j++) {
                int czas = poczatekDniaSymulacji;
                for (int k = 0; k < linie[j].getLiczbaTramwajow() - 1; k += 2) {
                    jazdaTramwaju(linie[j], k, czas);
                    jazdaTramwaju(linie[j], k + 1, czas);
                    czas += linie[j].getOdstep();
                }
                if (linie[j].getLiczbaTramwajow() % 2 == 1) {
                    jazdaTramwaju(linie[j],
                            linie[j].getLiczbaTramwajow() - 1, czas);
                }
            }

            // Posortowanie kolejki oraz wykonanie zdarzeń
            kolejka.posortujSie();
            while (!kolejka.czyPusta()) {
                Zdarzenie zdarzenie = kolejka.pobierz();
                dzienneLiczbyPrzejazdow[i] += zdarzenie.wykonaj(i);
            }

            // Opróżnienie przystanków na koniec dnia
            for (int j = 0; j < liczbaPrzystankow; j++) {
                przystanki[j].oproznij();
            }

            // Opróżnienie tramwajów na koniec dnia
            for (int j = 0; j < liczbaLinii; j++) {
                for(int k = 0; k < linie[j].getLiczbaTramwajow(); k++) {
                    linie[j].getTramwaj(k).oproznij();
                }
            }

            // Zebranie danych do statystyk
            for (int j = 0; j < liczbaPasazerow; j++) {
                dzienneCzasyOczekiwania[i] += pasazerowie[j].getCzasOczekiwania();
                pasazerowie[j].czasOczekiwaniaZero();
            }
            lacznyCzasOczekiwania += dzienneCzasyOczekiwania[i];
            wszystkiePrzejazdy += dzienneLiczbyPrzejazdow[i];
        }
    }

    // Symulacja jazdy pojedynczego tramwaju jednego dnia
    private void jazdaTramwaju(Linia linia, int idTramwaju, int czas) {
        while (czas <= koniecOdjazdow) {
            if (linia.getTramwaj(idTramwaju).isCzyDoPrzodu()) {
                czas = jazdaDoPrzodu(linia, idTramwaju, czas);
                czas = jazdaDoTylu(linia, idTramwaju, czas);
            }
            else {
                czas = jazdaDoTylu(linia, idTramwaju, czas);
                czas = jazdaDoPrzodu(linia, idTramwaju, czas);
            }
        }
    }

    // Jazda tramwaju od przystanku pierwszego do ostatniego na trasie
    private int jazdaDoPrzodu(Linia linia, int idTramwaju, int czas) {
        kolejka.wrzucNaKoniec(new ZdarzenieTramwajOdjazd(
                0, linia.getPrzystanek(0), czas, linia.getTramwaj(idTramwaju)));
        czas += linia.getCzasDojazdu(0);
        for (int i = 1; i < linia.getDlugoscTrasy() - 1; i++) {
            kolejka.wrzucNaKoniec(new ZdarzenieTramwajPrzyjazd(
                    i, linia.getPrzystanek(i), czas, linia.getTramwaj(idTramwaju)));
            kolejka.wrzucNaKoniec(new ZdarzenieTramwajOdjazd(
                    i, linia.getPrzystanek(i), czas, linia.getTramwaj(idTramwaju)));
            czas += linia.getCzasDojazdu(i);
        }
        kolejka.wrzucNaKoniec(new ZdarzenieTramwajPrzyjazd(
                linia.getDlugoscTrasy() - 1,
                linia.getPrzystanek(linia.getDlugoscTrasy() - 1), czas,
                linia.getTramwaj(idTramwaju)));
        czas += linia.getCzasDojazdu(linia.getDlugoscTrasy() - 1);
        return czas;
    }

    // Jazda tramwaju od przystanku ostatniego do przystanku pierwszego
    private int jazdaDoTylu(Linia linia, int idTramwaju, int czas) {
        kolejka.wrzucNaKoniec(new ZdarzenieTramwajOdjazd(
                linia.getDlugoscTrasy() - 1,
                linia.getPrzystanek(linia.getDlugoscTrasy() - 1), czas,
                linia.getTramwaj(idTramwaju)));
        czas += linia.getCzasDojazdu(linia.getDlugoscTrasy() - 2);
        for (int i = linia.getDlugoscTrasy() - 2; i > 0; i--) {
            kolejka.wrzucNaKoniec(new ZdarzenieTramwajPrzyjazd(
                    i, linia.getPrzystanek(i), czas, linia.getTramwaj(idTramwaju)));
            kolejka.wrzucNaKoniec(new ZdarzenieTramwajOdjazd(
                    i, linia.getPrzystanek(i), czas, linia.getTramwaj(idTramwaju)));
            czas += linia.getCzasDojazdu(i - 1);
        }
        kolejka.wrzucNaKoniec(new ZdarzenieTramwajPrzyjazd(
                0, linia.getPrzystanek(0), czas, linia.getTramwaj(idTramwaju)));
        czas += linia.getCzasDojazdu(linia.getDlugoscTrasy() - 1);
        return czas;
    }

    // Wypisanie otrzymanych statystyk symulacji
    public void zwrocRaport() {
        for (int i = 0; i < liczbaPasazerow; i++) {
            wszystkieOczekiwania += pasazerowie[i].getLiczbaOczekiwan();
        }
        if (wszystkieOczekiwania > 0) {
            sredniCzasOczekiwania = lacznyCzasOczekiwania /
                    (float) wszystkieOczekiwania;
        }

        System.out.println();
        System.out.println("RAPORT Z SYMULACJI:");

        // Statystyki dzienne
        for (int i = 0; i < liczbaDni; i++) {
            System.out.println("Statystyki symulacji z dnia " + (i + 1) + ":");
            System.out.println("- Łączny czas oczekiwania na przystankach: " +
                    dzienneCzasyOczekiwania[i]);
            System.out.println("- Liczba przejazdów: " +
                    dzienneLiczbyPrzejazdow[i]);
        }

        // Statystyki końcowe
        System.out.println("Końcowe statystyki symulacji:");
        System.out.println("- Średni czas oczekiwania na przystankach: " +
                sredniCzasOczekiwania + " min");
        System.out.println("- Łączna liczba przejazdów: " + wszystkiePrzejazdy);
    }
}