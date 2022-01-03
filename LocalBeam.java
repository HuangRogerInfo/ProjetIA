import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class LocalBeam {
    private int k;
    private HashSet<Etat2> trackedStates;

    /**
     * Constructeur initialisant k états aléatoires
     * @param g un graphe
     * @param k un nombre d'états traqués
     */
    public LocalBeam(GrapheComplet g, int k) {
        this.k = k;

        HashSet<Etat2> random_etats = new HashSet<Etat2>();
        while (random_etats.size() < k) {
            random_etats.add(new Etat2(g));
        }
        this.trackedStates = random_etats;
    }

    /**
     * Algorithme de recherche local d'une instance de voyageur de commerce
     * @param nb_iterations
     * @return un circuit
     * @throws Exception
     */
    public LinkedList<Integer> compute(int nb_iterations) throws Exception {

        for (int i = 0; i < nb_iterations; i++) {
            // on calcule les voisins
            HashSet<Etat2> frontier = new HashSet<Etat2>();
            for (Etat2 unEtat : trackedStates) {
                frontier.addAll(unEtat.getFrontier());
            }

            // On cherche les k meilleurs du voisinage
            HashSet<Etat2> meilleurs_etats = new HashSet<Etat2>();

            while (meilleurs_etats.size() < k) {
                Iterator<Etat2> it = frontier.iterator();
                Etat2 etatMin = it.next();

                while (it.hasNext()) {
                    Etat2 concurrent = it.next();
                    if (concurrent.getTotalCost() < etatMin.getTotalCost()) {
                        etatMin = concurrent;
                    }
                }

                frontier.remove(etatMin);
                meilleurs_etats.add(etatMin);
            }
            trackedStates = meilleurs_etats;
        }

        //A la fin on retourne le meilleur état parmi les états traqués
        Iterator<Etat2> it = trackedStates.iterator();
        Etat2 meilleurEtat = it.next();
        while (it.hasNext()) {
            Etat2 concurrent = it.next();
            if (concurrent.getTotalCost() < meilleurEtat.getTotalCost()) {
                meilleurEtat = concurrent;
            }
        }
        System.out.println("Result LocalBeam = " + meilleurEtat.getCircuit());
        System.out.println("[FINAL COST] = " + meilleurEtat.getTotalCost());
        return meilleurEtat.getCircuit();
    }
}