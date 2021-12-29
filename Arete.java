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

    public Arete inverse(){
        int new_s1 =s2;
        int new_s2 =s1;
        return new Arete(new_s1, new_s2);
    }

    public boolean contains(int s){
        if(s1 == s || s2==s){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "("+s1+","+s2+")";
    }
}
