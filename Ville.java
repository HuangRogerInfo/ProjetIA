public class Ville {
    private int x;
    private int y;

    public Ville(int rangeX, int rangeY) {
        this.x = Main.rnd.nextInt(rangeX);
        this.y = Main.rnd.nextInt(rangeY);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
