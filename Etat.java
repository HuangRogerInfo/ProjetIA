import java.util.HashSet;
import java.util.Set;

public class Etat {
    private int current_city;
    private Set<Integer> explored;
    private Set<Pair> actions;

    public Etat(int v) {
        this.current_city = v;
        this.explored = new HashSet<Integer>();
        this.actions = new HashSet<Pair>();
    }

    public boolean isSolved() {
        if (actions.size() == 0) {
            return true;
        }
        return false;
    }

}