public class Action{
    int ville;
    int cout;

    public Action(int v, int c){
        this.ville = v;
        this.cout = c;
    }

    public int getCout(){
        return this.cout;
    }

    public int getVille(){
        return this.ville;
    }

    @Override
    public String toString() {
        return "goTo("+ville+","+cout+")";
    }
    
}
