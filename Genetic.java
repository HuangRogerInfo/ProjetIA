import java.util.ArrayList;
import java.util.LinkedList;

public class Genetic {
    private GrapheComplet g;
    private ArrayList<Etat2> population;
    private int populationSize;

    /**
     * Constructeur initialisant une population aleatoire
     * 
     * @param g un graphe
     * @param k la taille de la population
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
        System.out.println("Population de depart : ");
        for (Etat2 e: random_etats) {
            System.out.println(e);
        }
        this.population = random_etats;
        this.g = g;
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
        System.out.println("Couples formes=");
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
            Couple enfants = formerEnfants(c.getC1(), c.getC2());
            Etat2 enfant1 = enfants.getC1();
            Etat2 enfant2 = enfants.getC2();
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
        //A chaque itération
        for (int j = 0; j < nb_iteration; j++) {
            System.out.println("\nGENERATION "+j);
            System.out.println("Etats initiaux :");
            for (Etat2 e : population) {
                System.out.println(e);
            }
            // On fait une selection en fonction de la fitness puis on forme des enfants
            ArrayList<Etat2> nwPopulation = crossover(selection());
            System.out.println("Nouvelle population :");
            for (Etat2 e : nwPopulation) {
                System.out.println(e.getCircuit() + " (" + e.getTotalCost() + ")");
            }
            // Ces enfant subissent aleatoirement des mutations
            for (int i = 0; i < nwPopulation.size(); i++) {
                if (Math.random() < mutationRate) {
                    Etat2 before = nwPopulation.get(i);
                    Etat2 after = before.mutation();
                    System.out.println("mutated from" +before+after );
                    nwPopulation.set(i, after);
                }
            }
            // On stocke dans un tableau un % des meilleurs de la generation precedente
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
            //Ce % de meilleurs remplacent les pires de la nouvelle generation
            ArrayList<Etat2> copy2 = new ArrayList<Etat2>(nwPopulation);
            for(int i = 0; i<meilleurs_etats.size();i++){
                Etat2 etatMax = copy2.get(0);
                for (int z = 0; z < copy2.size(); z++) {
                    Etat2 concurrent = copy2.get(z);
                    if (concurrent.getTotalCost() > etatMax.getTotalCost()) {
                        etatMax = concurrent;
                    }
                }
                copy2.remove(etatMax);
                nwPopulation.remove(etatMax);
                nwPopulation.add(meilleurs_etats.get(i));
            }
            // On passe a la generation suivante
            population = nwPopulation;
        }

        // A la fin on retourne le meilleur etat de la population finale
        Etat2 meilleurEtat = population.get(0);
        for (int i = 0; i < populationSize; i++) {
            Etat2 concurrent = population.get(i);
            if (concurrent.getTotalCost() < meilleurEtat.getTotalCost()) {
                meilleurEtat = concurrent;
            }
        }
        System.out.println("\nResult genetic = " + meilleurEtat.getCircuit());
        System.out.println("[FINAL COST] = " + meilleurEtat.getTotalCost());
        return meilleurEtat.getCircuit();
    }

    /**
     * Retourne un individu aleatoirement proportionnellement a sa fitness
     * @param population
     * @return un indice
     * @throws Exception
     */
    public int choixAleatoire(ArrayList<Etat2> population) throws Exception {
        int somme_cout = 0;
        for (int i = 0; i < population.size(); i++) {
            somme_cout += population.get(i).getTotalCost();
        }
        // Tableau de probabilite en fonction de la fitness
        ArrayList<Float> proba = new ArrayList<Float>();
        float somme_proba = 0;
        for (int i = 0; i < population.size(); i++) {
            float fitness_score = 1 - (population.get(i).getTotalCost() / somme_cout);
            somme_proba += fitness_score;
            proba.add(fitness_score);
        }
        // Nombre aleatoire entre 0 et somme du tableau
        double rand = Math.random() * somme_proba;

        // Choix de l'individu
        float cumul = 0;
        for (int j = 0; j < proba.size(); j++) {
            cumul += (float) proba.get(j);
            if (rand < cumul) {
                return j;
            }
        }
        return proba.size() - 1;
    }

    /**
     * Crée deux enfants à partir d'un père et d'une mère
     * @param pere
     * @param mere
     * @return un couple
     * @throws Exception
     */
    public Couple formerEnfants(Etat2 pere, Etat2 mere) throws Exception {
        int decoupage = (int) (Math.floor(Math.random() * g.getTaille()));
        
        Etat2 enfant1 = new Etat2(g);
        ArrayList<Integer> contenuPere = new ArrayList<Integer>();
        for (int i = 0; i < decoupage; i++) {
            enfant1.setSommetCircuit(i, pere.getSommetCircuit(i));
            contenuPere.add(pere.getSommetCircuit(i));
        }

        int start_insertion = decoupage;
        for (int j = 0; j < mere.getCircuit().size(); j++) {
            if (!contenuPere.contains(mere.getSommetCircuit(j))) {
                enfant1.setSommetCircuit(start_insertion, mere.getSommetCircuit(j));
                contenuPere.add(mere.getSommetCircuit(j));
                start_insertion++;
            }
        }

        Etat2 enfant2 = new Etat2(g);
        ArrayList<Integer> contenuMere = new ArrayList<Integer>();
        for (int i = 0; i < decoupage; i++) {
            enfant2.setSommetCircuit(i, mere.getSommetCircuit(i));
            contenuMere.add(mere.getSommetCircuit(i));
        }

        start_insertion = decoupage;
        for (int j = 0; j < pere.getCircuit().size(); j++) {
            if (!contenuMere.contains(pere.getSommetCircuit(j))) {
                enfant2.setSommetCircuit(start_insertion, pere.getSommetCircuit(j));
                contenuMere.add(pere.getSommetCircuit(j));
                start_insertion++;
            }
        }
        return new Couple(enfant1, enfant2);
    }
}