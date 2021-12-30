import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

public class Etat {
    private GrapheComplet g;
    private int current_city;
    private LinkedList<Integer> visited;
    private int goal;

    /**
     * Constructeur
     * 
     * @param g     le graphe associé
     * @param start la ville de départ
     */
    public Etat(GrapheComplet g, int start) {
        this.g = g;
        this.current_city = start;
        this.visited = new LinkedList<Integer>();
        this.goal = start;
    }

    public Etat(GrapheComplet g, int start, LinkedList<Integer> visited) {
        this.g = g;
        this.current_city = start;
        this.visited = visited;
        this.goal = start;
    }

    public LinkedList<Integer> getVisited() {
        return this.visited;
    }

    public HashSet<Etat> getFrontier() throws Exception {
        HashSet<Etat> frontier = new HashSet<Etat>();

        for (int j = 0; j < g.getTaille(); j++) {
            if (!visited.contains(j) && j != current_city) {
                LinkedList<Integer> new_visited = new LinkedList<Integer>(visited);
                new_visited.add(j);

                frontier.add(new Etat(g, goal, new_visited));
            }
        }
        return frontier;
    }

    public boolean isSolved() {
        return this.visited.size() == g.getTaille() && this.current_city == goal;
    }

    /**
     * Retourne la distance totale parcourue
     * 
     * @return une distance
     * @throws Exception
     */
    public int getTotalCost() throws Exception {
        int total_cost = 0;
        for (int i = 0, j = 1; j < visited.size(); i++, j++) {
            total_cost += g.getPoids(visited.get(i), visited.get(j));
        }
        total_cost += g.getPoids(visited.getLast(), visited.getFirst());
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
        int new_taille = g.getTaille() - visited.size() - 1;
        int[][] new_matrice = new int[new_taille][new_taille];

        for (int i = 0, new_i = 0; i < g.getTaille(); i++, new_i++) {
            while (visited.contains(i) || i == sommet) {
                i++;
            }
            for (int j = 0, new_j = 0; j < g.getTaille(); j++, new_j++) {
                while (visited.contains(j) || j == sommet) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Etat e = (Etat) o;
        return (this.visited.equals(e.visited));
    }

    @Override
    public int hashCode() {
        return Objects.hash(visited);
    }
}
