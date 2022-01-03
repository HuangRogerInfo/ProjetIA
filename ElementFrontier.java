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
    
}