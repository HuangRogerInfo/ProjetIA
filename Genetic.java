import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Genetic {
    private GrapheComplet g;
    private ArrayList<Etat2> population;
    private int populationSize;

    /**
     * Constructeur initialisant k états aléatoires
     * 
     * @param g un graphe
     * @param k un nombre d'états traqués
     */
    public Genetic(GrapheComplet g, int k) {
        int nb_pair = k;
        if (k % 2 != 0) {
            nb_pair++;
        }
        this.populationSize = nb_pair;

        ArrayList<Etat2> random_etats = new ArrayList<Etat2>();
        while (random_etats.size() < nb_pair) {
            random_etats.add(new Etat2(g));
        }
        this.population = random_etats;
        this.g = g;
    }

    /**
     * Retourne un individu aleatoirement proportionnellement a sa fitness
     * 
     * @return un etat
     * @throws Exception
     */
    public int choixAleatoire() throws Exception {
        ArrayList<Float> proba = new ArrayList<Float>();
        float somme = 0;

        for (int i = 0; i < populationSize; i++) {
            int cout_i = population.get(i).getTotalCost();
            somme += cout_i;
            proba.add(somme);
        }

        double rand = Collections.max(proba) + Collections.min(proba) - (Math.random() * somme);
        for (int j = 0; j < proba.size(); j++) {
            if (rand > proba.get(j)) {
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

        while (couples.size() != populationSize / 2) {
            do {
                father = population.get(choixAleatoire());
                mother = population.get(choixAleatoire());
            } while (!father.equals(mother));

            couples.add(new Couple(father, mother));
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
            Etat2 enfant1 = c.getFather();
            Etat2 enfant2 = c.getMother();

            for (int i = 0; i < g.getTaille(); i++) {
                // Father
                if (Math.random() > 0.5) {
                    enfant1.setVisited(i, c.getFather().getVisited(i));
                }
                // Mother
                else {
                    enfant1.setVisited(i, c.getMother().getVisited(i));
                }
            }

            for (int i = 0; i < g.getTaille(); i++) {
                // Father
                if (Math.random() > 0.5) {
                    enfant2.setVisited(i, c.getFather().getVisited(i));
                }
                // Mother
                else {
                    enfant2.setVisited(i, c.getMother().getVisited(i));
                }
            }
            children.add(enfant1);
            children.add(enfant2);
        }
        return children;
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
                if (Math.random() > mutationRate) {
                    nwPopulation.set(i, nwPopulation.get(i).mutation());
                }
            }

            // On stocke dans un tableau % des meilleurs de la génération précédente
            int nb_conserve = (int) elitistRate * population.size();
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