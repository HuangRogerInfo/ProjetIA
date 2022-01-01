import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class Etat2 {
    private GrapheComplet g;
    private LinkedList<Integer> circuit;

    /**
     * Constructeur d'un état aleatoire
     * 
     * @param g un graphe
     */
    public Etat2(GrapheComplet g) {
        this.g = g;

        // Circuit aleatoire
        LinkedList<Integer> circuit_aleatoire = new LinkedList<Integer>();
        for (int i = 0; i < g.getTaille(); i++) {
            circuit_aleatoire.add(i);
        }
        Collections.shuffle(circuit_aleatoire);
        this.circuit = circuit_aleatoire;
    }

    /**
     * Constructeur d'un état défini par un circuit hamiltonien donné
     * 
     * @param g       un graphe
     * @param circuit une liste chainée d'entier
     */
    public Etat2(GrapheComplet g, LinkedList<Integer> circuit) {
        this.g = g;
        this.circuit = circuit;
    }

    /**
     * Retourne l'ensemble des états atteignables à partir de l'état courant
     * 
     * @return un ensemble d'état
     */
    public HashSet<Etat2> getFrontier() {
        HashSet<Etat2> frontier = new HashSet<Etat2>();

        // Application de 2-opt pour trouver les voisins
        LinkedList<Integer> reversed = new LinkedList<Integer>(circuit);
        Collections.reverse(reversed);

        for (int i = 0; i < circuit.size(); i++) {
            for (int j = i + 1; j < circuit.size(); j++) {
                // Pour toutes paires (i,j) possibles
                LinkedList<Integer> new_list = new LinkedList<Integer>(circuit);

                // On crée un nouveau voisin en inversant l'ordre de parcours entre i et j
                int delta = circuit.size() - (j + 1);
                for (int w = i, z = delta; w <= j; w++, z++) {
                    new_list.set(w, reversed.get(z));
                }

                // On l'ajoute à la frontière
                frontier.add(new Etat2(g, new_list));
            }
        }
        return frontier;
    }

    /**
     * Retourne un état atteignable aléatoire à partir de l'état courant
     * 
     * @return un état
     */
    public Etat2 mutation() {
        int i = (int) Math.random() * (circuit.size() - 1);
        int j = (int) Math.random() * (circuit.size() - 1);

        LinkedList<Integer> new_list = new LinkedList<Integer>(circuit);

        new_list.set(i, circuit.get(j));
        new_list.set(j, circuit.get(i));

        return new Etat2(g, new_list);
    }

    /**
     * Retourne la distance totale du circuit
     * 
     * @return un entier
     * @throws Exception
     */
    public int getTotalCost() throws Exception {
        int total_cost = 0;
        for (int i = 0, j = 1; j < circuit.size(); i++, j++) {
            total_cost += g.getPoids(circuit.get(i), circuit.get(j));
        }
        total_cost += g.getPoids(circuit.getLast(), circuit.getFirst());
        return total_cost;
    }

    public void setVisited(int position, int valeur) throws Exception {
        if (position < 0 || position >= g.getTaille()) {
            throw new Exception("Problème d'indice");
        }
        this.circuit.set(position, valeur);
    }

    public int getVisited(int position) throws Exception {
        if (position < 0 || position >= g.getTaille()) {
            throw new Exception("Problème d'indice");
        }
        return this.circuit.get(position);
    }

    /**
     * Retourne le circuit hamiltonien de l'état courant
     * 
     * @return une liste d'entier
     */
    public LinkedList<Integer> getCircuit() {
        return this.circuit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Etat2 p = (Etat2) o;
        return (circuit.equals(p.getCircuit()));
    }
}
