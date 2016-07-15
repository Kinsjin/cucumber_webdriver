/**
 * Created by dingfan on 2016/7/15.
 */
public class StaticTest2 extends StaticTest {

    static{
        System.out.println("staic blocasdasdasdasdas");
    }

    public static void main(String[] args){
        System.out.println(StaticTest2.value);
    }
}
