package kolejka;

public abstract class Zdarzenie implements Comparable<Zdarzenie> {
    protected int czas;

    public Zdarzenie(int czas) {
        this.czas = czas;
    }

    public int getCzas() {
        return czas;
    }

    public abstract int wykonaj(int nrDnia);

    // Porównywanie zdarzeń ze względu na czas ich wystąpienia
    // Potrzebne do sortowania kolejki
    @Override
    public int compareTo(Zdarzenie z) {
        return Integer.compare(this.czas, z.czas);
    }
}
