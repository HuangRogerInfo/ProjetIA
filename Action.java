public class Action implements Comparable<Action> {
    private Etat etat;
    private int cout;

    public Action(Etat e, int c) {
        etat = e;
        cout = c;
    }

    public Etat getS() {
        return etat;
    }

    public int getC() {
        return cout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Action p = (Action) o;
        return (this.etat.equals(p.etat) && (cout == p.cout));
    }

    @Override
    public String toString() {
        return "(" + etat + "," + cout + ")";
    }

    @Override
    public int compareTo(Action p) {
        return Integer.compare(this.cout, p.cout);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + etat.hashCode();
        hash = 31 * hash + cout;
        return hash;
    }

}