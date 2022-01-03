import java.util.Random;

public class Main {
    public static Random rnd = new Random(2);
    public static void main(String[] args) {
        int[][] matrice_exemple = {
            { 0, 21, 9, 21, 1 },
            { 21, 0, 4, 8, 20 },
            { 9, 4, 0, 6, 7 },
            { 21, 8, 6, 0, 11 },
            { 1, 20, 7, 11, 6 }
        };

        int[][] matrice_exemple2 = {
            {0,   462, 931,  380,  488,  746,  219,  348,  143, 522, 293,  575,  710,  759,  589},
            {462, 0,   472,  681,  494,  302,  689,  718,  487, 62,  594,  107,  251,  299,  970},
            {931, 472, 0,    1145, 790,  326,  1159, 1186, 955, 491, 1062, 466,  281,  178,  1441},
            {380, 681, 1145, 0,    860,  824,  598,  107,  516, 662, 88,   786,  874,  972,  298},
            {488, 494, 790,  860,  0,    791,  549,  827,  347, 552, 773,  534,  740,  787,  1070},
            {746, 302, 326,  824,  791,  0,    963,  895,  787, 322, 771,  297,  56,   154,  1121},
            {219, 689, 1159, 598,  549,  963,  0,    572,  199, 750, 511,  803,  938,  986,  760},
            {348, 718, 1186, 107,  827,  895,  572,  0,    483, 699, 128,  823,  949,  1013, 241},
            {143, 487, 955,  516,  347,  787,  199,  483,  0,   548, 429,  600,  735,  783,  725},
            {522, 62,  491,  662,  552,  322,  750,  699,  548, 0,   576,  155,  271,  319,  952},
            {293, 594, 1062, 88,   773,  771,  511,  128,  429, 576, 0,    700,  825,  890,  378},
            {575, 107, 466,  786,  534,  297,  803,  823,  600, 155, 700,  0,    246,  293,  1076},
            {710, 251, 281,  874,  740,  56,   938,  949,  735, 271, 825,  246,  0,    108,  1171},
            {759, 299, 178,  972,  787,  154,  986,  1013, 783, 319, 890,  293,  108,  0,    1269},
            {589, 970, 1441, 298,  1070, 1121, 760,  241,  725, 952, 378,  1076, 1171, 1269, 0},
        };

        // Matrice de 5 villes (exemple du sujet)
        try {
            long startTime;
            long endTime;

            // Création du graphe
            System.out.println("---CREATION DU GRAPHE---");
            GrapheComplet graphe = new GrapheComplet(matrice_exemple);
            graphe.printMatrice();

            // Application d'Astar
            System.out.println("\n---EXECUTION ASTAR---");
            startTime = System.currentTimeMillis();
            Astar a = new Astar(graphe, 0);
            a.compute();
            endTime = System.currentTimeMillis();
            System.out.println("Temps de calcul :" + (endTime - startTime) + "ms");

            // Application de Local Beam
            System.out.println("\n---EXECUTION LOCAL BEAM---");
            startTime = System.currentTimeMillis();
            LocalBeam l = new LocalBeam(graphe, 3);
            l.compute(10);
            endTime = System.currentTimeMillis();
            System.out.println("Temps de calcul :" + (endTime - startTime)+ "ms");

            // Application de Genetic
            System.out.println("\n---EXECUTION GENETIC---");
            startTime = System.currentTimeMillis();
            Genetic g = new Genetic(graphe, 5);
            g.compute(0.1, 0.3, 10);
            endTime = System.currentTimeMillis();
            System.out.println("Temps de calcul :" + (endTime - startTime)+ "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Matrice de 15 villes
        try {
            long startTime;
            long endTime;
            
            // Création du graphe
            System.out.println("\n---CREATION DU GRAPHE---");
            GrapheComplet graphe2 = new GrapheComplet(matrice_exemple2);
            graphe2.printMatrice();
            
            // Application d'Astar 
            /*System.out.println("\n---EXECUTION ASTAR---");
            startTime = System.currentTimeMillis();
            Astar a = new Astar(graphe2, 0);
            a.compute();
            endTime = System.currentTimeMillis();
            System.out.println("Temps de calcul :" + (endTime - startTime) + "ms");*/

            // Application de Local Beam
            int nb_iteration = 10;
            int nb_tracked = 8;

            System.out.println("\n---EXECUTION LOCAL BEAM---");
            startTime = System.currentTimeMillis();
            LocalBeam l = new LocalBeam(graphe2, nb_tracked);
            l.compute(nb_iteration);
            endTime = System.currentTimeMillis();
            System.out.println("Temps de calcul :" + (endTime - startTime)+ "ms");

            // Application de Genetic
            int taille_population = 150;
            nb_iteration = 20;
            double taux_mutation = 0.1;
            double taux_elitisme = 0.2;

            System.out.println("\n---EXECUTION GENETIC---");
            startTime = System.currentTimeMillis();
            Genetic g = new Genetic(graphe2, taille_population);
            g.compute(taux_mutation, taux_elitisme, nb_iteration);
            endTime = System.currentTimeMillis();
            System.out.println("Temps de calcul :" + (endTime - startTime)+ "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
