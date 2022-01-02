public class Couple {
    private Etat2 C1;
    private Etat2 C2;

    public Couple(Etat2 C1, Etat2 C2) {
        this.C1 = C1;
        this.C2 = C2;
    }

    public Etat2 getC1() {
        return C1;
    }

    public Etat2 getC2() {
        return C2;
    }

    @Override
    public String toString() {
        return "(" + C1.getCircuit().toString() + "," + C2.getCircuit().toString() + ")";
    }
}
