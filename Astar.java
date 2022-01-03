import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Astar {
    private GrapheComplet g;
    private Etat currentState;
    private HashSet<Etat> exploredState;

    /**
     * Constructeur initialisant l'état initial
     * 
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
     * 
     * @return un circuit optimal
     * @throws Exception en cas de depassement d'indice
     */
    public LinkedList<Integer> compute() throws Exception {
        int size_max_frontiere = 0;

        while (!currentState.isSolved()) {

            HashSet<ElementFrontier> frontier_total = new HashSet<ElementFrontier>();

            
            //On construit la frontiere des états explorés
            for(Etat explored : exploredState){
                //Pour chaque états explores on calcule les actions possibles
                HashSet<Action> actions = explored.getActions();
                
                //Pour chaque actions possibles on cree un etat voisin
                for(Action a : actions){
                    Etat newEtat = explored.goTO(a.getVille());

                    //Si il n'a pas deja ete explore
                    if(!exploredState.contains(newEtat)){
                        //On l'ajoute si il est plus interessant en terme de cout
                        int cn = g.getTotalCost(newEtat.getChemin());
                        int hn = newEtat.getHeuristiqueMST();
                        ElementFrontier new_EF = new ElementFrontier(newEtat,cn+hn);
                        
                        boolean present = false;
                        for(ElementFrontier EF: frontier_total){
                            if(EF.getEtat().equals(new_EF.getEtat())){
                                present = true;
                                if(EF.getCout() > new_EF.getCout()){
                                    frontier_total.remove(EF);
                                    frontier_total.add(new_EF);
                                }
                            }
                        }
                        if(!present){
                            frontier_total.add(new_EF);
                        }
                    }
                }
            }
            System.out.println(frontier_total.size());

            //On choisit le minimum des états
            Iterator<ElementFrontier> it = frontier_total.iterator();
            ElementFrontier min = it.next();
            while(it.hasNext()){
                ElementFrontier concurrent = it.next();
                if(concurrent.getCout()<min.getCout()){
                    min = concurrent;
                }
            }

            //On transite
            System.out.println(min.getEtat().getChemin());
            exploredState.add(min.getEtat());
            currentState = min.getEtat();
        }

        LinkedList<Integer> chemin = currentState.getChemin();

        System.out.println("Result Astar = " + chemin);
        System.out.println("[FINAL COST] = " + g.getTotalCost(chemin));
        System.out.println("[FRONTIER MAX] = " + size_max_frontiere);
        return chemin;
    }
}
