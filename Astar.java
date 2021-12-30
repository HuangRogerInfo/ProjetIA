import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Astar {
    private Etat currentState;
    private HashSet<Etat> exploredState;

    public Astar(int start, GrapheComplet g) {
        this.currentState = new Etat(g, start);
        this.exploredState = new HashSet<Etat>();
    }

    /**
     * Algorithme informee de recherche d'une instance de voyageur de commerce
     * 
     * @return une liste ordonnee des villes explorees
     * @throws Exception en cas de depassement d'indice
     */
    public LinkedList<Integer> compute() throws Exception {
        exploredState.add(currentState);

        while (!currentState.isSolved()) {
            HashSet<Etat> frontier = new HashSet<Etat>();
            for (Etat e : exploredState) {
                for (Etat e1 : e.getFrontier()) {
                    if (!exploredState.contains(e1)) {
                        frontier.add(e1);
                    }
                }
            }

            System.out.println("size=" + frontier.size());
            for (Etat e : frontier) {
                System.out.println(e.getVisited());
            }

            Iterator<Etat> it = frontier.iterator();
            Etat etatMax = it.next();

            while (it.hasNext()) {
                Etat newEtat = it.next();
                if (newEtat.getTotalCost() < etatMax.getTotalCost()) {
                    etatMax = newEtat;
                }
            }
            System.out.println("bestcost=" + etatMax.getTotalCost());
            currentState = etatMax;
            exploredState.add(currentState);
        }
        return currentState.getVisited();
    }

}
