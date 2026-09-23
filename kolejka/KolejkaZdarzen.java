package kolejka;

public interface KolejkaZdarzen {
    void wrzucNaKoniec(Zdarzenie z);
    Zdarzenie pobierz();
    boolean czyPusta();
    void posortujSie();
}
