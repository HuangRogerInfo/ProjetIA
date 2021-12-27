public class Pair implements Comparable<Pair> {
    private int sommet;
    private int cout;

    public Pair(int s, int c) {
        sommet = s;
        cout = c;
    }

    public int getS() {
        return sommet;
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
        Pair p = (Pair) o;
        return (sommet == p.sommet) && (cout == p.cout);
    }

    @Override
    public String toString() {
        return "(" + sommet + "," + cout + ")";
    }

    @Override
    public int compareTo(Pair p) {
        return Integer.compare(this.cout, p.cout);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + sommet;
        hash = 31 * hash + cout;
        return hash;
    }

}