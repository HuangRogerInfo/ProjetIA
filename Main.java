public class Main {
    public static void main(String[] args) {
        // Matrice Exemple
        int[][] matrice_exemple = {
                { 0, 5, 8, 7 },
                { 5, 0, 6, 7 },
                { 8, 6, 0, 5 },
                { 7, 7, 5, 0 },
        };

        int[][] matrice_exemple2 = {
                { 0, 61, 62, 203, 64, 53, 68, 164, 169, 188, 61, 91, 264 },
                { 61, 0, 12, 257, 11, 35, 33, 106, 223, 242, 115, 145, 318 },
                { 62, 12, 0, 258, 9, 36, 44, 116, 224, 243, 116, 146, 319 },
                { 203, 257, 258, 0, 259, 249, 264, 359, 41, 60, 145, 112, 62 },
                { 64, 11, 9, 259, 0, 38, 44, 116, 226, 245, 118, 148, 321 },
                { 53, 35, 36, 249, 38, 0, 21, 117, 215, 234, 107, 137, 310 },
                { 68, 33, 44, 264, 44, 21, 0, 109, 230, 249, 122, 152, 325 },
                { 16, 106, 116, 359, 116, 117, 109, 0, 326, 345, 218, 248, 421 },
                { 169, 223, 224, 41, 226, 215, 230, 326, 0, 19, 112, 78, 102 },
                { 188, 242, 243, 60, 245, 234, 249, 345, 19, 0, 131, 97, 121 },
                { 61, 115, 116, 145, 118, 107, 122, 218, 112, 131, 0, 34, 206 },
                { 91, 145, 146, 112, 148, 137, 152, 248, 78, 97, 34, 0, 173 },
                { 264, 318, 319, 62, 321, 310, 325, 421, 102, 121, 206, 173, 0 },
        };

        // MATRICE 1
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
            l.compute(10);

            // Application de Genetic
            System.out.println("\n---EXECUTION GENETIC---");
            Genetic g = new Genetic(graphe, 5);
            g.compute(0.2, 0.3, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // MATRICE 2
        try {
            // Création du graphe
            System.out.println("\n---CREATION DU GRAPHE---");
            GrapheComplet graphe2 = new GrapheComplet(matrice_exemple2);
            graphe2.printMatrice();

            // Application de Local Beam
            System.out.println("\n---EXECUTION LOCAL BEAM---");
            LocalBeam l = new LocalBeam(graphe2, 3);
            l.compute(10);

            // Application de Genetic
            System.out.println("\n---EXECUTION GENETIC---");
            Genetic g = new Genetic(graphe2, 5);
            g.compute(0.2, 0.3, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
