public class Couple {
    private Etat2 e1;
    private Etat2 e2;

    public Couple(Etat2 e1, Etat2 e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public Etat2 getFather() {
        return e1;
    }

    public Etat2 getMother() {
        return e2;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        try {
            return "(" + e1.getCircuit() + "," + e2.getCircuit() + ")";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "?";
    }
}
