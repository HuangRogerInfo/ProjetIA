import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Genetic {
    private GrapheComplet g;
    private ArrayList<Etat2> population;
    private int populationSize;

    /**
     * Constructeur initialisant une population aleatoire
     * 
     * @param g un graphe
     * @param k un nombre d'états traqués
     * @throws Exception
     */
    public Genetic(GrapheComplet g, int k) throws Exception {
        int nb_pair = k;
        if (k % 2 != 0) {
            nb_pair++;
        }
        this.populationSize = nb_pair;

        ArrayList<Etat2> random_etats = new ArrayList<Etat2>();
        while (random_etats.size() < nb_pair) {

            Etat2 random_etat = new Etat2(g);
            while (random_etats.contains(random_etat)) {
                random_etat = new Etat2(g);
            }
            random_etats.add(random_etat);
        }

        for (Etat2 etat2 : random_etats) {
            System.out.println(etat2.getCircuit() + " (" + etat2.getTotalCost() + ")");
        }
        System.out.println("/");

        this.population = random_etats;
        this.g = g;
    }

    /**
     * Retourne un individu aleatoirement proportionnellement a sa fitness
     * 
     * @return un etat
     * @throws Exception
     */
    public int choixAleatoire(ArrayList<Etat2> population) throws Exception {
        ArrayList<Float> proba = new ArrayList<Float>();

        int somme_cout = 0;
        for (int i = 0; i < population.size(); i++) {
            somme_cout += population.get(i).getTotalCost();
        }

        // Tableau de probabilité en fonction de la fitness
        for (int i = 0; i < population.size(); i++) {
            float cout_i = 1 - (population.get(i).getTotalCost() / somme_cout);
            proba.add(cout_i);
        }

        // Nombre aleatoire entre 0 et somme du tableau
        float range = 0;
        for (int i = 0; i < proba.size(); i++) {
            range += proba.get(i);
        }
        double rand = Math.random() * range;

        // Choix de l'individu
        float cumul = 0;
        for (int j = 0; j < proba.size(); j++) {
            cumul += proba.get(j).floatValue();
            if (rand < cumul) {
                return j;
            }
        }
        return proba.size() - 1;
    }

    /**
     * Cree une liste de couples a partir de la population
     * 
     * @return une liste de couple
     * @throws Exception
     */
    public ArrayList<Couple> selection() throws Exception {
        ArrayList<Couple> couples = new ArrayList<Couple>();

        Etat2 father = new Etat2(g);
        Etat2 mother = new Etat2(g);
        ArrayList<Etat2> copy = new ArrayList<Etat2>(population);

        while (couples.size() != populationSize / 2) {

            father = copy.get(choixAleatoire(copy));
            copy.remove(father);
            mother = copy.get(choixAleatoire(copy));

            couples.add(new Couple(father, mother));
        }

        for (Couple c : couples) {
            System.out.println(c);
        }
        return couples;
    }

    /**
     * Cree une nouvelle population avec une liste de couples
     * 
     * @param parents une liste de couple
     * @return une liste d'etat (individus)
     * @throws Exception
     */
    public ArrayList<Etat2> crossover(ArrayList<Couple> parents) throws Exception {
        ArrayList<Etat2> children = new ArrayList<Etat2>();

        for (Couple c : parents) {
            Couple enfants = getChildren(c.getFather(), c.getMother());

            Etat2 enfant1 = enfants.getFather();
            Etat2 enfant2 = enfants.getMother();

            System.out.println("Enfants cree");
            System.out.println(enfant1.getCircuit());
            System.out.println(enfant2.getCircuit());

            children.add(enfant1);
            children.add(enfant2);
        }

        return children;
    }

    public Couple getChildren(Etat2 pere, Etat2 mere) throws Exception {
        int decoupage = (int) (Math.random() * (g.getTaille() - 1));
        System.out.println("decoupage=" + decoupage);

        Etat2 enfant1 = new Etat2(g);
        ArrayList<Integer> contient = new ArrayList<Integer>();

        for (int i = 0; i < decoupage; i++) {
            enfant1.setVisited(i, pere.getVisited(i));
            contient.add(pere.getVisited(i));
        }

        int decalage = decoupage;

        for (int j = 0; j < mere.getCircuit().size(); j++) {

            if (!contient.contains(mere.getVisited(j))) {
                enfant1.setVisited(decalage, mere.getVisited(j));
                contient.add(mere.getVisited(j));
                decalage++;
            }
        }

        Etat2 enfant2 = new Etat2(g);
        ArrayList<Integer> contient2 = new ArrayList<Integer>();

        for (int i = 0; i < decoupage; i++) {
            enfant1.setVisited(i, mere.getVisited(i));
            contient2.add(mere.getVisited(i));
        }

        decalage = decoupage;

        for (int j = 0; j < pere.getCircuit().size(); j++) {

            if (!contient2.contains(pere.getVisited(j))) {
                enfant1.setVisited(decalage, pere.getVisited(j));
                contient2.add(pere.getVisited(j));
                decalage++;
            }
        }

        return new Couple(enfant1, enfant2);
    }

    /**
     * Algorithme de recherche local d'une instance de voyageur de commerce
     * 
     * @param mutationRate le % de chance de muter
     * @param elitistRate  le % des meilleurs de la generation precedente a garder
     * @param nb_iteration le nombre de generation
     * @return le meilleur etat apres toutes les generations
     * @throws Exception
     */
    public LinkedList<Integer> compute(double mutationRate, double elitistRate, int nb_iteration) throws Exception {

        for (int j = 0; j < nb_iteration; j++) {
            // On fait une selection en fonction de la fitness puis on crée des enfants
            ArrayList<Etat2> nwPopulation = crossover(selection());

            // Ces enfant subissent aléatoirement des mutations
            for (int i = 0; i < nwPopulation.size(); i++) {
                if (Math.random() < mutationRate) {
                    nwPopulation.set(i, nwPopulation.get(i).mutation());
                }
            }

            // On stocke dans un tableau % des meilleurs de la génération précédente
            int nb_conserve = (int) (elitistRate * population.size());

            ArrayList<Etat2> meilleurs_etats = new ArrayList<Etat2>();
            ArrayList<Etat2> copy = new ArrayList<Etat2>(population);

            while (meilleurs_etats.size() != nb_conserve) {
                Etat2 etatMin = copy.get(0);
                for (int i = 0; i < copy.size(); i++) {
                    Etat2 concurrent = copy.get(i);
                    if (concurrent.getTotalCost() < etatMin.getTotalCost()) {
                        etatMin = concurrent;
                    }
                }
                copy.remove(etatMin);
                meilleurs_etats.add(etatMin);
            }

            // On garde ce % de meilleurs dans la génération suivante
            LinkedList<Integer> indice_aleatoire = new LinkedList<Integer>();
            for (int i = 0; i < populationSize; i++) {
                indice_aleatoire.add(i);
            }
            Collections.shuffle(indice_aleatoire);

            for (int i = 0; i < nb_conserve; i++) {
                nwPopulation.set(indice_aleatoire.get(i), meilleurs_etats.get(i));
            }

            // On passe à la génération suivante
            population = nwPopulation;

            System.out.println("new_generation :");
            for (Etat2 e : population) {
                System.out.println(e.getCircuit() + " (" + e.getTotalCost() + ")");
            }

            // A la fin on retourne le meilleur état parmi les états traqués
            Etat2 meilleurEtat = population.get(0);
            for (int i = 0; i < populationSize; i++) {
                Etat2 concurrent = population.get(i);
                if (concurrent.getTotalCost() < meilleurEtat.getTotalCost()) {
                    meilleurEtat = concurrent;
                }
            }
            System.out.println("Result genetic = " + meilleurEtat.getCircuit());
            System.out.println("[FINAL COST] = " + meilleurEtat.getTotalCost());

        }

        // A la fin on retourne le meilleur état parmi les états traqués
        Etat2 meilleurEtat = population.get(0);
        for (int i = 0; i < populationSize; i++) {
            Etat2 concurrent = population.get(i);
            if (concurrent.getTotalCost() < meilleurEtat.getTotalCost()) {
                meilleurEtat = concurrent;
            }
        }
        System.out.println("Result genetic = " + meilleurEtat.getCircuit());
        System.out.println("[FINAL COST] = " + meilleurEtat.getTotalCost());
        return meilleurEtat.getCircuit();
    }
}