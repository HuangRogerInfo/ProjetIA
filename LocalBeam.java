import java.util.HashSet;
import java.util.Iterator;

public class LocalBeam {
    private int k;
    private HashSet<Etat2> etats;
    private GrapheComplet g;

    public LocalBeam(int k, GrapheComplet g) {
        this.g = g;
        this.k = k;
        this.etats = new HashSet<Etat2>();
    }

    public Etat2 compute(int nb_iterations) throws Exception {
        // On initilise des etats aleatoires
        HashSet<Etat2> random_etats = new HashSet<Etat2>();
        while (random_etats.size() < k) {
            random_etats.add(new Etat2(g));
        }
        this.etats = random_etats;

        for (int i = 0; i < nb_iterations; i++) {

            // On fait l'union des voisinages de chaques états
            HashSet<Etat2> voisinage = new HashSet<Etat2>();

            for (Etat2 unEtat : etats) {
                voisinage.addAll(unEtat.getVoisinage());
            }

            // Parmis ces voisinages on choisit les k meilleurs
            HashSet<Etat2> meilleurs_etats = new HashSet<Etat2>();

            while (meilleurs_etats.size() < k) {
                Iterator<Etat2> it = voisinage.iterator();
                Etat2 etatMax = it.next();

                while (it.hasNext()) {
                    Etat2 newEtat = it.next();
                    if (newEtat.getTotalCost() < etatMax.getTotalCost()) {
                        etatMax = newEtat;
                    }
                }

                voisinage.remove(etatMax);
                meilleurs_etats.add(etatMax);
            }
            etats = meilleurs_etats;
        }

        Iterator<Etat2> it = etats.iterator();
        Etat2 meilleurEtat = it.next();
        while (it.hasNext()) {
            Etat2 newEtat = it.next();
            if (newEtat.getTotalCost() < meilleurEtat.getTotalCost()) {
                meilleurEtat = newEtat;
            }
        }
        return meilleurEtat;
    }

}
