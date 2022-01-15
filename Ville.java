import java.util.ArrayList;

public class Ville {
    private int x;
    private int y;

    public Ville(int rangeX, int rangeY) {
        this.x = Main.rnd.nextInt(rangeX);
        this.y = Main.rnd.nextInt(rangeY);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public static ArrayList<Ville> getVilles(int size, int RangeX, int RangeY) {
        ArrayList<Ville> villes = new ArrayList<Ville>();
        for (int i = 0; i < size; i++) {
            Ville newVille = new Ville(RangeX, RangeY);
            while (villes.contains(newVille)) {
                newVille = new Ville(RangeX, RangeY);
            }
            villes.add(newVille);
        }
        return villes;
    }

    public static int distanceEuclidienne(Ville a, Ville b) {
        return (int) Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Ville v = (Ville) o;
        return v.getX() == this.x && v.getY() == this.y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
