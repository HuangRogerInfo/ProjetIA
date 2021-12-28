public class Arete {
    int s1;
    int s2;

    public Arete(int s1,int s2){
        this.s1 = s1;
        this.s2 = s2;
    }

    public int getS1(){
        return s1;
    }

    public int getS2(){
        return s2;
    }

    @Override
    public String toString() {
        return "("+s1+","+s2+")";
    }
}
