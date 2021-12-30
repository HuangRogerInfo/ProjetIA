public class Main {
    public static void main(String[] args) {
        int[][] matrice_exemple = {
                { 0, 21, 9, 21, 1 },
                { 21, 0, 4, 8, 20 },
                { 9, 4, 0, 6, 7 },
                { 21, 8, 6, 0, 11 },
                { 1, 20, 7, 11, 6 }
        };

        try {
            GrapheComplet graphe = new GrapheComplet(matrice_exemple);
            Parcours p = new Parcours(graphe, 0);
            System.out.println(p.A_star());
            System.out.println(p.getCost());

            LocalBeam l = new LocalBeam(3, graphe);
            Etat2 result = l.compute(2);
            System.out.println(result.getCircuit());
            System.out.println(result.getTotalCost());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
