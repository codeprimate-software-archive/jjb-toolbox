/**
 * Copyright (c) 2001, JB Innovations
 * ALL RIGHTS RESERVED
 *
 * @author John J. Blum
 * File: CurrencyFieldTest.java
 * @version v1.0
 * Date: 5 December 2001
 * Modification Date: 5 December 2001
 * @since Java 2
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import jjb.toolbox.swing.JCurrencyField;

public class CurrencyFieldTest extends JFrame
{

  public CurrencyFieldTest()
  {
    super("JCurrencyField");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(200,100));
    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
    getContentPane().add(new JCurrencyField());
    show();
  }

  public static void main(String[] args)
  {
    new CurrencyFieldTest();
  }

}

