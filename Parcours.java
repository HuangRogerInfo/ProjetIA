
//import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class Parcours {
    private GrapheComplet g;
    private int current_city;
    private LinkedList<Integer> explored;
    private int total_cost;

    /**
     * Constructeur
     * 
     * @param g     le graphe associé
     * @param start la ville de départ
     */
    public Parcours(GrapheComplet g, int start) {
        this.g = g;
        this.total_cost = 0;
        this.current_city = start;
        this.explored = new LinkedList<Integer>();
    }

    /**
     * Algorithme informee de recherche d'une instance de voyageur de commerce
     * 
     * @return une liste ordonnee des villes explorees
     * @throws Exception en cas de depassement d'indice
     */
    public LinkedList<Integer> A_star() throws Exception {
        explored.add(current_city);

        while (actions().size() > 0) {
            HashSet<Pair> actions = actions();
            int previous_city = current_city;
            current_city = minimum(actions).getS();
            explored.add(current_city);
            total_cost += g.getPoids(previous_city, current_city);
        }

        return explored;
    }

    /**
     * Retourne l'ensemble des actions possibles
     * 
     * @return un ensemble de paires d'action/cout
     * @throws Exception en cas de dépassement d'indices
     */
    public HashSet<Pair> actions() throws Exception {
        HashSet<Pair> actions = new HashSet<Pair>();
        for (int i = 0; i < g.getTaille(); i++) {
            for (int j = 0; j < g.getTaille(); j++) {
                if (!explored.contains(j) && j != i) {
                    int gn = g.getPoids(i, j);
                    // int hn = heuristiqueMST(j);
                    int hn = heuristiqueNulle(j);
                    actions.add(new Pair(j, gn + hn));
                }
            }
        }
        return actions;
    }

    /**
     * Retourne l'action avec le cout minimal
     * 
     * @param actions un ensemble d'actions
     * @return la paire action/cout minimal
     */
    public Pair minimum(HashSet<Pair> actions) {
        return Collections.min(actions);
    }

    /**
     * Retourne la distance totale parcourue
     * 
     * @return une distance
     */
    public int getCost() {
        return this.total_cost;
    }

    /**
     * Une heuristique nulle
     * 
     * @param sommet
     * @return un entier
     */
    public int heuristiqueNulle(int sommet) {
        return 0;
    }

    public int heuristiqueMST(int sommet) throws Exception {
        int new_taille = g.getTaille() - explored.size() - 1;
        int[][] new_matrice = new int[new_taille][new_taille];

        for (int i = 0, new_i = 0; i < g.getTaille(); i++, new_i++) {
            while (explored.contains(i) || i == sommet) {
                i++;
            }
            for (int j = 0, new_j = 0; j < g.getTaille(); j++, new_j++) {
                while (explored.contains(j) || j == sommet) {
                    j++;
                }
                if (j < g.getTaille() && i < g.getTaille()) {
                    new_matrice[new_i][new_j] = g.getPoids(i, j);
                }
            }
        }

        GrapheComplet new_G = new GrapheComplet(new_matrice);
        return new_G.getpoidsACM();
    }
}
