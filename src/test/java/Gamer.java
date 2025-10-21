public final class Gamer {

    private static final double PI = Math.PI;
    public static double cos(double x) {
        x = (x + PI / 2) % (2 * PI) - PI / 2;
        if(x < 0) x = -x;
        int scal = 1;
        if(x > PI / 2) {
            scal = -1;
            x = PI - x;
        }
        return scal * (1- x*x/(2) + x*x*x*x/(2 * 3 * 4) - x*x*x*x*x*x/(2 * 3 * 4 * 5 * 6));
    }

    public static void main(final String[] args) {

        //System.out.println(cos(7.7));
        //System.out.println("modulo " + (7.7 - PI / 2) % (2 * PI));
        //System.out.println((7.7 + PI / 2) % (2 * PI) - PI / 2);
        for(double x = -10; x < 10.; x += 0.1) {
            System.out.println(x + " " + cos(x) + " " + Math.cos(x));
        }
    }
}
