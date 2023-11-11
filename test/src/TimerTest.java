/**
 * TimerTest.java (c) 2002.11.25
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2002.11.25
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

  private static final class MyTimerTask extends TimerTask {
    int callNumber = 0;
    public void run() {
      System.out.println("Call Number: "+(++callNumber));
    }
  }
  
  public static void main(String[] args) {
    new Timer(false).schedule(
      new MyTimerTask(),0,Integer.parseInt(args[0]));
  }
  
}

