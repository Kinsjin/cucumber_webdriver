/**
 * Created by dingfan on 2016/7/15.
 */
public class StaticTest {
    static{
        System.out.println("staic block");
    }
    public static int value = 123;
    public StaticTest(){
    }
    public static void test(){
        System.out.println("Method");
    }

    /*public static void main(String[] arg){
        new StaticTest().test();
    }*/
}

