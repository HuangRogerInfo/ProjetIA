package Etat;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

import Utility.GrapheComplet;

public class Etat {
    private GrapheComplet g;
    private int goal;
    private int currentCity;
    private HashSet<Integer> visited;
    private LinkedList<Etat> previousEtat;

    /**
     * Constructeur d'un etat initial
     * @param g     le graphe associe
     * @param start la ville de depart
     */
    public Etat(GrapheComplet g, int start) {
        this.g = g;
        this.currentCity = start;
        this.goal = start;
        this.visited = new HashSet<Integer>();
        this.previousEtat = new LinkedList<Etat>();
    }

    /**
     * Constructeur d'un etat quelconque
     * @param g le graphe associe
     * @param current_city la ville courante
     * @param goal la ville d'arrivee
     * @param visited les villes visitees
     * @param previousEtat les etats precedents
     */
    public Etat(GrapheComplet g, int current_city, int goal, HashSet<Integer> visited, LinkedList<Etat> previousEtat) {
        this.g = g;
        this.visited = visited;
        this.currentCity = current_city;
        this.goal = goal;
        this.previousEtat = previousEtat;
    }
    
    /**
     * Retourne vrai si l'état est résolu, faux sinon
     * @return un booléen
     */
    public boolean isSolved() {
        return this.visited.size() == g.getTaille() && currentCity == goal;
    }

    /**
     * Retourne l'ensemble des villes atteignables a partir de l'etat courant
     * @return un ensemble d'entier
     * @throws Exception
     */
    public HashSet<Integer> getActions() throws Exception {
        HashSet<Integer> villes = new HashSet<Integer>();

        for(int j = 0; j<g.getTaille(); j++){
            //Pour toutes les villes non visites
            if(!visited.contains(j)){
                if(visited.size()<g.getTaille()-1){
                    if(j!=goal){
                        villes.add(j);
                    }
                }
                else{
                    villes.add(j);
                }
            }
        }
        return villes;
    }

    /**
     * Retourne l'etat courant apres une transition
     * @param ville
     * @return un etat
     */
    public Etat goTO(int ville){
        HashSet<Integer> new_visited = new HashSet<>(visited);
        LinkedList<Etat> new_previousEtat = new LinkedList<Etat>(previousEtat);
        new_visited.add(ville);
        new_previousEtat.add(this);
        return new Etat(g,ville,goal,new_visited,new_previousEtat);
    }

    /**
     * Retourne la valeur de l'heuristique MST pour l'état courant
     * @return un entier
     * @throws Exception
     */
    public int getHeuristiqueMST() throws Exception {
        // Si toutes les villes sont visités alors le sous graphe contient uniquement la ville_initiale
        if (this.visited.size()==g.getTaille()) {
            return 0;
        }

        // Si aucune ville n'a été visitée alors le sous graphe = graphe complet
        if (visited.size() == 0) {
            return this.g.getpoidsACM();
        }

        // Sinon on enleve du graphe complet les villes visités sauf la ville_actuelle
        HashSet<Integer> toDelete = new HashSet<Integer>();
        for (int i = 0; i < g.getTaille(); i++) {
            if (visited.contains(i) && i != currentCity && i != goal) {
                toDelete.add(i);
            }
        }

        int new_taille = g.getTaille() - toDelete.size();
        int[][] new_matrice = new int[new_taille][new_taille];

        for (int i = 0, new_i = 0; new_i < new_taille; i++, new_i++) {
            for (int j = 0, new_j = 0; new_j < new_taille; j++, new_j++) {
                while (toDelete.contains(i)) {
                    i++;
                }
                while (toDelete.contains(j)) {
                    j++;
                }
                new_matrice[new_i][new_j] = g.getPoids(i, j);
            }
        }

        // On retourne le poids ACM du sous graphe construit
        return new GrapheComplet(new_matrice).getpoidsACM();
    }

    /**
     * Retourne le chemin courant
     * @return
     */
    public LinkedList<Integer> getChemin(){
        LinkedList<Integer> chemin = new LinkedList<Integer>();
        for(Etat e: getPreviousEtat()){
            chemin.add(e.getCurrentCity());
        }
        chemin.add(getCurrentCity());
        return chemin;
    }

    /**
     * Retourne une liste ordonnee des etats precedent
     * @return une liste d'etat
     */
    public LinkedList<Etat> getPreviousEtat(){
        return this.previousEtat;
    }

    /**
     * Retourne la ville courante
     * @return
     */
    public Integer getCurrentCity(){
        return this.currentCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Etat e = (Etat) o;
        return (this.visited.equals(e.visited)&&this.currentCity == e.currentCity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visited,currentCity);
    }

    @Override
    public String toString() {
        return "[ville_courante=" + currentCity + " visited=" + visited.toString()+"]";
    }
}
