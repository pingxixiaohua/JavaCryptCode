package demo01;

public class Tester {
//    public static void main(String[] args) {
//        int x = 5;
//        Integer x1 = x;
//        Integer x2 = x;
//        int x3 = new Integer(5);
//        System.out.print(x1.equals(x));
//        System.out.print(x1 == x);
//        System.out.print(x2.equals(x1));
//        System.out.print(x2 == x1);
//        System.out.print(x2 == x3);
//        System.out.print(x2.equals(x3));
//    }
}

class Test3{
    public static void main(String[] args) {
        boolean x = true;
        boolean y = false;
        short z = 20;
        if ((x == true) && (y = true))
            z++;
        System.out.println(z);
        if ((y == true) || (++z == 22)) {
            System.out.println(z);
            z++;
            System.out.println(z);
        }
        System.out.println("z=" + z);

    }
}
