package demo01;

public class Top {
    static int x= 1;
    public Top(int y){
        x *= 3;
    }

}

class Passer{
    static final int x= 5;

    public static void main(String[] args) {
        new Passer().go(x);
        System.out.print(x);
    }
    void go(int x){
        System.out.print(x++);
    }
}

class Wrench{
    public static void main(String[] args) {
        Wrench w=new Wrench();
        Wrench w2=new Wrench();
        w2=go(w, w2);
        System.out.print(w2==w);
    }
    static Wrench go(Wrench wrl,Wrench wr2){
        Wrench wr3=wrl;
        wrl = wr2;
        wr2 = wr3;
        return wr3;
    }
}

class Test2{
    public static void main(String[] args) {
        short a, b, c;
        a = 1;
        b = 2;
        a += 2;
        c = (short) (a + b);
    }
}

class Test4{
    public static void main(String[] args){
        boolean x=true;
        boolean y=false;
        short z=42;
        if((z++==42)&&(y=true))
            z++;
        if((x=false)||(++z==45))
            z++;
        System.out.println("z="+z);
    }
}
