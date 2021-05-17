package madtitan.projects.eternaldb;

public class Main {

  public static void main(String[] args) {
    String a = "Aman";
    byte[] arr = a.getBytes();
    for(int i=0; i<arr.length; i++) {
      System.out.println(arr[i]);
    }

    System.out.println(arr[0]);
    //System.out.println("Hello World!!");
  }
}
