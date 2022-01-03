import java.util.Objects;

public class ElementFrontier{
    Etat etat;
    int cout;

    public ElementFrontier(Etat e, int c){
        this.etat= e;
        this.cout = c;
    }

    public int getCout(){
        return this.cout;
    }

    public Etat getEtat(){
        return this.etat;
    }

    @Override
    public String toString() {
        return "goTo("+etat+","+cout+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        ElementFrontier e = (ElementFrontier) o;
        return (this.etat == e.etat && this.cout == e.cout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(etat,cout);
    }
    
}