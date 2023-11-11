
public class ExceptionTest {

  public static int foo2() {
    int i = 0;
    try {
      i++;
      return i;
    }
    finally {
      i++;
    }
  }

  public static int foo() {
    try {
      //throw new NullPointerException("NULL!");'
      throw new Error("Error!");
    }
    finally {
      return 8;
    }
  }

  public static void main(String[] args) {
    try {
      System.out.println("Foo Returns: "+foo());
      System.out.println("Foo2 Returns: "+foo2());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}

