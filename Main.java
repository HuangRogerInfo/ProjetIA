public class Main {
    public static void main(String[] args) {
        // Matrice Exemple
        int[][] matrice_exemple = {
                { 0, 21, 9, 21, 1 },
                { 21, 0, 4, 8, 20 },
                { 9, 4, 0, 6, 7 },
                { 21, 8, 6, 0, 11 },
                { 1, 20, 7, 11, 6 }
        };

        try {
            // Création du graphe
            System.out.println("---CREATION DU GRAPHE---");
            GrapheComplet graphe = new GrapheComplet(matrice_exemple);
            graphe.printMatrice();

            // Application d'Astar
            System.out.println("\n---EXECUTION ASTAR---");
            Astar a = new Astar(graphe, 0);
            a.compute();

            // Application de Local Beam
            System.out.println("\n---EXECUTION LOCAL BEAM---");
            LocalBeam l = new LocalBeam(graphe, 3);
            l.compute(2);

            // Application de Genetic
            System.out.println("\n---EXECUTION GENETIC---");
            Genetic g = new Genetic(graphe, 13);
            g.compute(0.3, 0.5, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
