import java.util.List;
import java.util.ArrayList;

public class Villes {
    private List<Ville> villes;

    public Villes(int size, int RangeX, int RangeY) {
        villes = new ArrayList<Ville>();
        for (int i = 0; i < size; i++) {
            villes.add(new Ville(RangeX, RangeY));


        }
    }
}