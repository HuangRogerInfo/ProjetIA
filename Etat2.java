import java.util.Collections;
import java.util.LinkedList;

public class Etat2 {
    private GrapheComplet g;
    private LinkedList<Integer> circuit;

    public Etat2(GrapheComplet g){
        this.g = g;
        
        LinkedList<Integer> circuit_aleatoire = new LinkedList<Integer>();
        for(int i=0;i<g.getTaille();i++){
            circuit_aleatoire.add(i);
        }
        Collections.shuffle(circuit_aleatoire);

        this.circuit = circuit_aleatoire;
    }

    public Etat2(GrapheComplet g, LinkedList<Integer> circuit){
        this.g = g;
        this.circuit = circuit;
    }

    public int getTotalCost() throws Exception{
        int total_cost = 0;
        for(int i=0, j=1;j<circuit.size();i++,j++){
            total_cost += g.getPoids(circuit.get(i), circuit.get(j));
        }
        return total_cost;
    }
}
