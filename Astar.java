import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Astar {
    private Etat currentState;
    private HashSet<Etat> exploredState;

    /**
     * Constructeur initialisant l'état initial
     * 
     * @param g
     * @param start
     */
    public Astar(GrapheComplet g, int start) {
        this.currentState = new Etat(g, start);
        this.exploredState = new HashSet<Etat>();
    }

    /**
     * Algorithme informee de recherche d'une instance de voyageur de commerce
     * 
     * @return un circuit optimal
     * @throws Exception en cas de depassement d'indice
     */
    public LinkedList<Integer> compute() throws Exception {
        int size_max_frontiere = 0;
        exploredState.add(currentState);

        while (!currentState.isSolved()) {
            // On calcule la frontière
            HashSet<Etat> frontier = new HashSet<Etat>();
            for (Etat e : exploredState) {
                for (Etat voisin : e.getFrontier()) {
                    if (!exploredState.contains(voisin)) {
                        frontier.add(voisin);
                    }
                }
            }

            // On garde en mémoire la taille de la frontière maximale
            if (frontier.size() > size_max_frontiere) {
                size_max_frontiere = frontier.size();
            }

            // On cherche l'état minimum de la frontière
            Iterator<Etat> it = frontier.iterator();
            Etat etatMin = it.next();

            while (it.hasNext()) {
                int cn = etatMin.getTotalCost();
                int hn = etatMin.getHeuristiqueMST();
                Etat concurrent = it.next();
                int new_cn = concurrent.getTotalCost();
                int new_hn = concurrent.getHeuristiqueMST();

                if (new_cn + new_hn < cn + hn) {
                    etatMin = concurrent;
                }
            }

            // L'état courant devient l'état minimum
            currentState = etatMin;
            System.out.println("Etat courant=" + currentState.getVisited());
            int cn = currentState.getTotalCost();
            int hn = currentState.getHeuristiqueMST();
            int total = cn + hn;
            System.out.println("hn=" + hn + " cn=" + cn + " total=" + total + "\n");
            exploredState.add(currentState);
        }
        System.out.println("Result Astar = " + currentState.getVisited());
        System.out.println("[FINAL COST] = " + currentState.getTotalCost());
        System.out.println("[FRONTIER MAX] = " + size_max_frontiere);
        return currentState.getVisited();
    }
}
