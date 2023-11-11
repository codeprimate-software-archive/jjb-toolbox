/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: JButtonInsetsTest.java
 * @version v1.0
 * Date: 17 July 2002
 * Modification Date: 17 July 2002
 * @since Java 2
 */

import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;

public class JButtonInsetsTest
{

  public static void main(String[] args)
  {
    final JButton button = new JButton();

    final Insets margin = new Insets(0,0,0,0);

    System.out.println("Insets: "+button.getInsets());
    System.out.println("Margin: "+button.getMargin());

    button.setMargin(margin);

    System.out.println("Insets: "+button.getInsets());
    System.out.println("Margin: "+button.getMargin());

    /**final JLabel label = new JLabel();

    System.out.println("Insets: "+label.getInsets());*/
  }

}

