import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Astar {
    private GrapheComplet g;
    private Etat currentState;
    private HashSet<Etat> exploredState;

    /**
     * Constructeur initialisant l'état initial
     * @param g
     * @param start
     */
    public Astar(GrapheComplet g, int start) {
        this.g = g;
        this.currentState = new Etat(g, start);
        this.exploredState = new HashSet<Etat>();
        exploredState.add(currentState);
    }

    /**
     * Algorithme informee de recherche d'une instance de voyageur de commerce
     * @return un circuit optimal
     * @throws Exception
     */
    public LinkedList<Integer> compute() throws Exception {
        int size_max_frontiere = 0;

        while (!currentState.isSolved()) {
            HashSet<ElementFrontier> frontier_total = new HashSet<ElementFrontier>();
            
            //On construit la frontiere des états explorés
            for(Etat explored : exploredState){

                //Pour chaque etats explores on calcule les villes atteignables
                HashSet<Integer> villes = explored.getActions();
                
                //Pour chaque villes atteignables on cree un etat voisin
                for(Integer ville : villes){
                    Etat newEtat = explored.goTO(ville);

                    //Si il n'a pas deja ete explore, on l'ajoute dans la frontiere
                    if(!exploredState.contains(newEtat)){
                        int cn = g.getTotalCost(newEtat.getChemin());
                        int hn = newEtat.getHeuristiqueMST();
                        ElementFrontier new_EF = new ElementFrontier(newEtat,cn+hn);
                        frontier_total.add(new_EF);
                    }
                }
            }
            if(frontier_total.size()>size_max_frontiere){
                size_max_frontiere = frontier_total.size();
            }

            //On choisit le minimum des etats
            Iterator<ElementFrontier> it = frontier_total.iterator();
            ElementFrontier min = it.next();
            while(it.hasNext()){
                ElementFrontier concurrent = it.next();
                if(concurrent.getCout()<min.getCout()){
                    min = concurrent;
                }
            }

            //On transite vers ce minimum
            exploredState.add(min.getEtat());
            currentState = min.getEtat();
            System.out.println(currentState);
        }

        LinkedList<Integer> chemin = currentState.getChemin();

        System.out.println("Result Astar = " + chemin);
        System.out.println("[FINAL COST] = " + g.getTotalCost(chemin));
        System.out.println("[FRONTIER MAX] = " + size_max_frontiere);
        return chemin;
    }
}
