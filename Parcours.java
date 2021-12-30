
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
            HashSet<Action> actions = actions();
            int previous_city = current_city;
            current_city = minimum(actions).getS();
            System.out.println(actions);

            explored.add(current_city);
            System.out.println("explored:" + explored);
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
    public HashSet<Action> actions() throws Exception {
        HashSet<Action> actions = new HashSet<Action>();
        // pour chaque autres villes
        for (int i : explored) {
            for (int j = 0; j < g.getTaille(); j++) {
                if (!explored.contains(j) && j != i) {
                    int gn = g.getPoids(i, j);
                    int hn = 0;
                    // int hn = heuristiqueMST(j);
                    actions.add(new Action(j, gn + hn));
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
    public Action minimum(HashSet<Action> actions) {
        return Collections.min(actions);
    }

    /**
     * Retourne la distance totale parcourue
     * 
     * @return une distance
     * @throws Exception
     */
    public int getCost() throws Exception {
        int total_cost = 0;
        for (int i = 0, j = 1; j < explored.size(); i++, j++) {
            total_cost += g.getPoids(explored.get(i), explored.get(j));
        }
        total_cost += g.getPoids(explored.getLast(), explored.getFirst());
        return total_cost;
    }

    /**
     * Heuristique
     * 
     * @param sommet
     * @return
     * @throws Exception
     */
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
