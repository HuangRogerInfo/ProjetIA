package Utility;

import java.util.ArrayList;

import Main.Main;

public class Ville {
    private int x;
    private int y;

    /**
     * Constructeur d'une ville aleatoire
     * @param rangeX la portee de la position X
     * @param rangeY la portee de la position Y
     */
    public Ville(int rangeX, int rangeY) {
        this.x = Main.rnd.nextInt(rangeX);
        this.y = Main.rnd.nextInt(rangeY);
    }

    /**
     * Retourne la position X de la ville
     * @return un entier
     */
    public int getX() {
        return this.x;
    }

    /**
     * Retourne la position Y de la ville
     * @return un entier
     */
    public int getY() {
        return this.y;
    }

    /**
     * Retourne une liste de villes aleatoires
     * @param size le nombre de villes
     * @param RangeX la portee de la position X
     * @param RangeY la portee de la position Y
     * @return
     */
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

    /**
     * Retourne la distance entre deux villes A et B
     * @param a la ville A
     * @param b la ville B
     * @return
     */
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
