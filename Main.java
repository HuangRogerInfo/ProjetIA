public class Main {
    public static void main(String[] args) {
        //Matrice Exemple
        int[][] matrice_exemple = {
                { 0, 21, 9, 21, 1 },
                { 21, 0, 4, 8, 20 },
                { 9, 4, 0, 6, 7 },
                { 21, 8, 6, 0, 11 },
                { 1, 20, 7, 11, 6 }
        };

        try {
            //Création du graphe
            GrapheComplet graphe = new GrapheComplet(matrice_exemple);

            //Application d'Astar
            Astar a = new Astar(graphe, 0);
            System.out.println(a.compute());

            //Application de Local Beam
            LocalBeam l = new LocalBeam(graphe, 3);
            System.out.println(l.compute(2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
