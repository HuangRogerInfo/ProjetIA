import java.util.ArrayList;
import java.util.LinkedList;

public class GrapheComplet {
    private int taille;
    private int[][] matrice;

    /**
     * Constructeur d'un graphe complet dont les poids valent 1 de taille n
     * 
     * @param n
     */
    public GrapheComplet(int n) {
        this.taille = n;
        this.matrice = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrice[i][j] = 1;
                if (i == j) {
                    matrice[i][j] = 0;
                }
            }
        }
    }

    /**
     * Constructeur d'un graphe complet à partir d'une matrice d'adjacence
     * 
     * @param matrice
     * @throws Exception
     */
    public GrapheComplet(int[][] matrice) throws Exception {

        int nb_ligne = matrice.length;
        int nb_colonne = 0;
        if (nb_ligne > 0) {
            nb_colonne = matrice[0].length;
        }

        if (nb_ligne != nb_colonne) {
            throw new Exception("Seulement les matrices carées sont autorisées.");
        }

        this.taille = matrice.length;
        this.matrice = matrice;
    }

    /**
     * Constructeur d'un graphe complet à partir d'une liste de villes
     * 
     * @param villes une liste
     * @throws Exception
     */
    public GrapheComplet(ArrayList<Ville> villes) {
        this.taille = villes.size();
        this.matrice = new int[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                matrice[i][j] = Ville.distanceEuclidienne(villes.get(i), villes.get(j));
            }
        }
    }

    /**
     * Retourne le poids de l'arbre couvrant minimal
     * 
     * @return un entier
     */
    public int getpoidsACM() {
        if (taille == 0) {
            return 0;
        }

        int poids = 0;
        ArrayList<Integer> arbre = new ArrayList<Integer>();
        arbre.add(0);

        while (arbre.size() != taille) {
            int minimum = Integer.MAX_VALUE;
            int minimum_id = -1;
            for (int i = 0; i < arbre.size(); i++) {
                // On check tous les sommets sortants
                for (int j = 0; j < taille; j++) {
                    // On filtre les sommets appartenant aux cocycles
                    if (!arbre.contains(j)) {
                        int valeur = matrice[arbre.get(i)][j];
                        // On cherche le minimum
                        if (valeur > 0 && valeur < minimum) {
                            minimum = valeur;
                            minimum_id = j;
                        }
                    }
                }
            }
            poids += minimum;
            arbre.add(minimum_id);
        }
        return poids;
    }

    /**
     * Modifie le poids entre deux sommets
     * 
     * @param sommet1
     * @param sommet2
     * @param distance
     * @throws Exception
     */
    public void modifierPoids(int sommet1, int sommet2, int distance) throws Exception {
        if (sommet1 >= taille || sommet1 < 0 || sommet2 >= taille || sommet2 < 0) {
            throw new Exception("Probleme d'indice");
        }

        matrice[sommet1][sommet2] = distance;
        matrice[sommet2][sommet1] = distance;
    }

    /**
     * Retourne la distance totale parcourue à partir d'un chemin
     * 
     * @return un entier
     * @throws Exception
     */
    public int getTotalCost(LinkedList<Integer> chemin) throws Exception {
        if (chemin.size() == 0) {
            return 0;
        }
        int total_cost = 0;
        for (int i = 0, j = 1; j < chemin.size(); i++, j++) {
            total_cost += getPoids(chemin.get(i), chemin.get(j));
        }
        return total_cost;
    }

    /**
     * Retourne le poids entre deux sommets
     * 
     * @param sommet1
     * @param sommet2
     * @return un entier
     * @throws Exception
     */
    public int getPoids(int sommet1, int sommet2) throws Exception {
        if (sommet1 >= taille || sommet1 < 0 || sommet2 >= taille || sommet2 < 0) {
            throw new Exception("Probleme d'indicee");
        }

        return matrice[sommet1][sommet2];
    }

    /**
     * Retourne la taille du graphe
     * 
     * @return un entier
     */
    public int getTaille() {
        return taille;
    }

    /**
     * Affiche la matrice d'adjacence du graphe
     */
    public void printMatrice() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                System.out.print(matrice[i][j] + "\t");
            }
            System.out.println("");
        }
    }
}