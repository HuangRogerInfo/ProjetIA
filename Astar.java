import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Astar {
    private Etat currentState;
    private HashSet<Etat> exploredState;

    /**
     * Constructeur initialisant l'état initial
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
        exploredState.add(currentState);

        while (!currentState.isSolved()) {
            //On calcule la frontière
            HashSet<Etat> frontier = new HashSet<Etat>();
            for (Etat e : exploredState) {
                for (Etat voisin : e.getFrontier()) {
                    if (!exploredState.contains(voisin)){
                        frontier.add(voisin);
                    }
                }
            }

            //System.out.println("[FRONTIER SIZE]=" + frontier.size());
            /*for (Etat e : frontier) {
                System.out.println(e.getVisited());
            }*/

            //On cherche l'état minimum de la frontière
            Iterator<Etat> it = frontier.iterator();
            Etat etatMin = it.next();
            int cn = etatMin.getTotalCost();
            int hn = etatMin.getHeuristiqueMST();
            hn = etatMin.getHeuristiqueNulle();

            while (it.hasNext()) {
                Etat concurrent = it.next();
                int new_cn = concurrent.getTotalCost();
                int new_hn = concurrent.getHeuristiqueMST();
                new_hn = concurrent.getHeuristiqueNulle();

                if (new_cn+new_hn < cn+hn) {
                    etatMin = concurrent;
                }
            }
            //System.out.println("[new cost]=" + etatMin.getTotalCost());

            //L'état courant devient l'état minimum
            currentState = etatMin;
            exploredState.add(currentState);
        }
        System.out.println("Result Astar=" + currentState.getVisited());
        System.out.println("[FINAL COST]=" + currentState.getTotalCost());
        return currentState.getVisited();
    }
}
