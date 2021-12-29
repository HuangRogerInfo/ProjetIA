import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class Etat2 {
    private GrapheComplet g;
    private LinkedList<Integer> circuit;

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
        //circuit_aleatoire.add(circuit_aleatoire.getFirst());

        System.out.println(circuit_aleatoire);
        this.circuit = circuit_aleatoire;
    }

    /**
     * Constructeur 2 produisant un Etat défini par un circuit hamiltonien donné
     * @param g un graphe
     * @param circuit une liste chainée d'entier
     */
    public Etat2(GrapheComplet g, LinkedList<Integer>circuit){
        this.g = g;
        this.circuit = circuit;
    }

    /**
     * Retourne le cout total du circuit hamiltonien
     * @return un entier
     * @throws Exception
     */
    public int getTotalCost() throws Exception{
        int total_cost = 0;
        for(int i=0,j=1;j<circuit.size();i++,j++){
            total_cost+= g.getPoids(circuit.get(i), circuit.get(j));
        }
        return total_cost;
    }

    public LinkedList<Integer> getCircuit(){
        return this.circuit;
    }

    /**
     * Retourne le voisinage possible à partir d'un état
     * @return un ensemble d'état
     */
    public HashSet<Etat2> getVoisinage(){
        //System.out.println("c"+circuit);

        HashSet<Etat2> voisinage = new HashSet<Etat2>();

        LinkedList<Integer> reversed = new LinkedList<Integer>(circuit);
        Collections.reverse(reversed);

        for(int i=0;i<circuit.size();i++){
            for(int j =i+1; j<circuit.size();j++){
                LinkedList<Integer> new_list = new LinkedList<Integer>(circuit);

                //On inverse la partie de la liste entre i et j
                int delta = circuit.size()-(j+1);
                for(int w=i,z=delta;w<=j;w++,z++){
                    new_list.set(w,reversed.get(z));
                }

                //System.out.println("["+i+"/"+j+"]="+new_list);
                voisinage.add(new Etat2(g,new_list));
            }
        }
        return voisinage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Etat2 p = (Etat2) o;
        return (circuit.equals(p.getCircuit()));
    }
}
