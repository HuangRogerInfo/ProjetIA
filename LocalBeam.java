import java.util.HashSet;

public class LocalBeam {
    private int k;
    private HashSet<Etat2> etats;
    private GrapheComplet g;

    public LocalBeam(int k, GrapheComplet g){
        this.g = g;
        this.k = k;
        HashSet<Etat2> random_etats = new HashSet<Etat2>();

        for(int i=0;i<k;i++){
            random_etats.add(new Etat2(g));
        }

        this.etats = random_etats;
    }

    public Etat2 compute(int nb_iterations){
        Etat2 return_Etat = new Etat2(g);

        HashSet<Etat2> voisinage = new HashSet<Etat2>();
        for(Etat2 unEtat:etats){
            voisinage.addAll(unEtat.getVoisinage());
        }

        HashSet<Etat2> new_etats = new HashSet<Etat2>();
        Iterator it = voisinage.iterator();

        return return_Etat;
    }    
}
