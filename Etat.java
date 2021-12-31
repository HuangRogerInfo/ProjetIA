import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

public class Etat {
    private GrapheComplet g;
    private LinkedList<Integer> visited;
    private int goal;

    /**
     * Constructeur d'un état initial
     * 
     * @param g     le graphe associé
     * @param start la ville de départ
     */
    public Etat(GrapheComplet g, int start) {
        this.g = g;
        this.visited = new LinkedList<Integer>();
        this.goal = start;
    }

    /**
     * Constructeur d'un état quelconque
     * @param g
     * @param start
     * @param visited
     */
    public Etat(GrapheComplet g,  int goal, LinkedList<Integer> visited) {
        this.g = g;
        this.visited = visited;
        this.goal = goal;
    }

    /**
     * Retourne vrai si l'état est résolu, faux sinon
     * @return un booléen
     */
    public boolean isSolved() {
        return this.visited.size() == g.getTaille() && this.visited.getLast() == goal;
    }

    /**
     * Retourne la liste ordonnée des villes visitées
     * @return une liste d'entier
     */
    public LinkedList<Integer> getVisited() {
        return this.visited;
    }

    /**
     * Retourne l'ensemble des états atteignables à partir de l'état courant
     * @return un ensemble d'état
     * @throws Exception
     */
    public HashSet<Etat> getFrontier() throws Exception {
        HashSet<Etat> frontier = new HashSet<Etat>();

        for (int j = 0; j < g.getTaille(); j++) {
            if (!visited.contains(j)) {
                if( j!=goal || (j==goal && visited.size()==g.getTaille()-1)){
                    LinkedList<Integer> new_visited = new LinkedList<Integer>(visited);
                    new_visited.add(j);

                    frontier.add(new Etat(g, goal, new_visited));
                }
            }
        }
        return frontier;
    }

    /**
     * Retourne la distance totale parcourue
     * 
     * @return un entier
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
     * Retourne la valeur de l'heuristique MST pour l'état courant
     * 
     * @return un entier
     * @throws Exception
     */
    public int getHeuristiqueMST() throws Exception {
        //Si c'est résolu alors le sous graphe contient uniquement la ville_initiale ce qui donne un poids ACM=0
        if(this.isSolved()){
            return 0;
        }

        //Sinon construction du sous graphe pour estimer le cout du chemin partiel
        int ville_actuelle = visited.getLast();

        //On enleve du graphe les villes visités sauf la ville_actuelle
        HashSet<Integer> toDelete = new HashSet<Integer>();
        for(int i=0; i<g.getTaille(); i++){
            if(visited.contains(i) && i!=ville_actuelle){
                toDelete.add(i);
            }
        }
        
        int new_taille = g.getTaille() - visited.size() + 1;
        int[][] new_matrice = new int[new_taille][new_taille];

        for (int i = 0, new_i = 0; new_i < new_taille; i++, new_i++) {
            for (int j = 0, new_j = 0; new_j < new_taille; j++, new_j++) {
                
                if(toDelete.contains(i)) {
                    i++;
                }
                else if(toDelete.contains(j)) {
                    j++;
                }
                new_matrice[new_i][new_j] = g.getPoids(i, j);
            }
        }

        //On retourne le poids ACM du sous graphe construit
        return new GrapheComplet(new_matrice).getpoidsACM();
    }

    /**
     * Retourne la valeur d'une heuristique nulle
     * @return 0
     */
    public int getHeuristiqueNulle(){
        return 0;
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
