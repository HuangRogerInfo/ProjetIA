import java.util.ArrayList;

public class GrapheComplet {
    private int taille;
    private int[][] matrice;

    /* Initialise un graphe complet de taille n avec des poids égals à 1 */
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

    /* Initialise un graphe avec une matrice d'adjacence */
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

    /* Modifie le poids de l'arete entre sommet1 et sommet2 */
    public void modifierPoids(int sommet1, int sommet2, int distance) throws Exception {
        if (sommet1 >= taille || sommet1 < 0 || sommet2 >= taille || sommet2 < 0) {
            throw new Exception("Probleme d'indice");
        }

        matrice[sommet1][sommet2] = distance;
        matrice[sommet2][sommet1] = distance;
    }

    /* retourne le poids entre sommet1 et sommet2 */
    public int getPoids(int sommet1, int sommet2) throws Exception {
        if (sommet1 >= taille || sommet1 < 0 || sommet2 >= taille || sommet2 < 0) {
            throw new Exception("Probleme d'indicee");
        }

        return matrice[sommet1][sommet2];
    }

    public GrapheComplet supprimerNoeud(int n) throws Exception {
        if (n >= taille || n < 0) {
            throw new Exception("Probleme d'indice");
        }
        int new_taille = taille - 1;
        int[][] new_matrice = new int[new_taille][new_taille];

        if (new_taille > 0) {
            for (int i = 0, new_i = 0; i < taille; i++, new_i++) {
                if (i == n) {
                    i++;
                }
                for (int j = 0, new_j = 0; j < taille; j++, new_j++) {
                    if (j == n) {
                        j++;
                    }

                    new_matrice[new_i][new_j] = matrice[i][j];
                }
            }
        }
        return new GrapheComplet(new_matrice);
    }

    /* Retourne la taille de la matrice */
    public int getTaille() {
        return taille;
    }

    /* Affiche la matrice d'adjacence */
    public void printMatrice() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                System.out.print(matrice[i][j] + " ");
            }
            System.out.println("");
        }
    }
}