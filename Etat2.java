import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class Etat2 {
    private GrapheComplet g;
    private HashSet<Arete> circuit;

    /**
     * Constructeur produisant un Etat aleatoire à partir d'un graphe
     * @param g un graphe
     */
    public Etat2(GrapheComplet g){
        this.g = g;
        
        //Circuit aleatoire
        LinkedList<Integer> circuit_aleatoire = new LinkedList<Integer>();
        for(int i=0;i<g.getTaille();i++){
            circuit_aleatoire.add(i);
        }
        Collections.shuffle(circuit_aleatoire);

        this.circuit = this.getSetAretes(circuit_aleatoire);
    }

    /**
     * Constructeur produisant un Etat défini par un circuit hamiltonien donné
     * @param g un graphe
     * @param circuit un ensemble d'arete
     */
    public Etat2(GrapheComplet g, HashSet<Arete> circuit){
        this.g = g;
        this.circuit = circuit;
    }

    /**
     * Constructeur 2 produisant un Etat défini par un circuit hamiltonien donné
     * @param g un graphe
     * @param circuit une liste chainée d'entier
     */
    public Etat2(GrapheComplet g, LinkedList<Integer> circuit){
        this.g = g;
        this.circuit = this.getSetAretes(circuit);
    }

    /**
     * Retourne le cout total du circuit hamiltonien
     * @return un entier
     * @throws Exception
     */
    public int getTotalCost() throws Exception{
        int total_cost = 0;
        for(Arete a:circuit){
            total_cost += g.getPoids(a.getS1(),a.getS2());
        }
        return total_cost;
    }

    /**
     * Retourne l'ensemble d'arete correspondant à un circuit hamiltonien donné
     * @param circuit_aleatoire
     * @return un ensemble d'arete
     */
    public HashSet<Arete> getSetAretes(LinkedList<Integer> circuit_aleatoire){
        HashSet<Arete> aretes = new HashSet<Arete>();
        for(int i=0,j=1; j<circuit_aleatoire.size(); i++,j++){
            aretes.add(new Arete(circuit_aleatoire.get(i),circuit_aleatoire.get(j)));
        }
        return aretes;
    }

    /**
     * Retourne le voisinage possible à partir d'un état
     * @return un ensemble d'état
     */
    public HashSet<Etat2> getVoisinage(){

        HashSet<Etat2> voisinage = new HashSet<Etat2>();

        ArrayList<Arete> list = new ArrayList<Arete>(circuit);
        for(int i=0;i<list.size();i++){
            for(int j =i+1; j<list.size();j++){
                //Pour chaque paires possibles d'aretes on produit un voisin
                Arete arete1 = list.get(i);
                Arete arete2 = list.get(j);
                Arete new_arete1 = new Arete(arete1.getS1(),arete2.getS1());
                Arete new_arete2 = new Arete(arete1.getS2(),arete2.getS2());

                HashSet<Arete> newSet = circuit;
                newSet.remove(arete1);
                newSet.remove(arete2);
                newSet.add(new_arete1);
                newSet.add(new_arete2);
                voisinage.add(new Etat2(g,newSet));
            }
        }

        return voisinage;
    }
}
