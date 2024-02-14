package console;

public class Module {
  
  public static void log(String message){
    System.out.println(message);
  }

  public static void log(int num){
    System.out.println(num);
  }

  public static void log(boolean flag){
    System.out.println(flag);
  }

  public static void error(String message){
    System.err.println(message);
  }
  
}
