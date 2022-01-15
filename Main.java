import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Main {
    static int random = (int) (Math.random() * 500);
    // static int random = 5; //Pour fixer le random
    public static Random rnd = new Random(random);

    public static void main(String[] args) {
        int taille = Integer.parseInt(args[0]);
        int nb_etat = Integer.parseInt(args[1]);

        // Création du graphe
        ArrayList<Ville> villes = Ville.getVilles(taille, 100, 100);
        System.out.println("Villes = " + villes);
        System.out.println("---CREATION DU GRAPHE---");
        GrapheComplet graphe = new GrapheComplet(villes);
        graphe.printMatrice();

        try {
            long startTime;
            long endTime;

            // Application d'Astar
            System.out.println("\n---EXECUTION ASTAR---");
            startTime = System.currentTimeMillis();
            Astar a = new Astar(graphe, 0);
            LinkedList<Integer> resultAstar = a.compute();
            endTime = System.currentTimeMillis();

            // Resultat
            System.out.println("Result Astar = " + resultAstar);
            System.out.println("[FINAL COST] = " + graphe.getTotalCost(resultAstar));
            System.out.println("Temps de calcul :" + (endTime - startTime) + "ms");

            // Application de Local Beam
            System.out.println("\n---EXECUTION LOCAL BEAM---");
            startTime = System.currentTimeMillis();
            LocalBeam l = new LocalBeam(graphe, nb_etat);
            Etat2 resultBeam = l.compute(10);
            endTime = System.currentTimeMillis();

            // Resultat
            System.out.println("Result LocalBeam = " + resultBeam.getCircuit());
            System.out.println("[FINAL COST] = " + resultBeam.getTotalCost());
            int ecart = resultBeam.getTotalCost() - graphe.getTotalCost(resultAstar);
            System.out.println("[Ecart a l'optimum] = " + ecart);
            System.out.println("Temps de calcul :" + (endTime - startTime) + "ms");

            // Application de Genetic
            System.out.println("\n---EXECUTION GENETIC---");
            startTime = System.currentTimeMillis();
            Genetic g = new Genetic(graphe, nb_etat);
            Etat2 resultGenetic = g.compute(0.15, 0.2, 10);
            endTime = System.currentTimeMillis();

            // Resultat
            System.out.println("Result Genetic = " + resultGenetic.getCircuit());
            System.out.println("[FINAL COST] = " + resultGenetic.getTotalCost());
            int ecart2 = resultGenetic.getTotalCost() - graphe.getTotalCost(resultAstar);
            System.out.println("[Ecart a l'optimum] = " + ecart2);
            System.out.println("Temps de calcul :" + (endTime - startTime) + "ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
