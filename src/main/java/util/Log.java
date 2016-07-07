package util;
import org.apache.log4j.Logger;

/**
 * Created by dingfan on 2016/7/6.
 */
public class Log {
    //初始化Logger对象
    private static Logger Log = Logger.getLogger(Log.class.getName());
    //定义静态方法，打印自定义的测试用例开始的日志信息
    public static void startTestCase(String sTestCaseName){
        Log.info("---------------------------------------------------------------------------------");
        Log.info("*********************        " + sTestCaseName + "         ************************");
    }
    //定义静态方法，打印自定义的测试用例结束的日志信息
    public static void endTestCase(String sTestCaseName){
        Log.info("*********************        " + "测试用例执行结束" + "      *********************");
        Log.info("--------------------------------------------------------------------------------");
    }
    //定义静态info方法，打印自定义的info级别的日志信息
    public static void info(String message){
        Log.info(message);
    }
    //定义静态warn方法，打印自定义的warn级别日志信息
    public static void warn(String message){
        Log.warn(message);
    }
    //定义静态error方法，打印自定义的error级别日志信息
    public static void error(String message){
        Log.error(message);
    }
    //定义静态fatal方法，打印自定义的fatal级别日志信息
    public static void fatal(String message){
        Log.fatal(message);
    }
    //定义静态debug方法，打印自定义的debug级别日志信息
    public static void debug(String message){
        Log.debug(message);
    }
}
