/**
 * Copyright (c) 2001, Sun Microsystems, Inc.
 * ALL RIGHTS RESERVED
 *
 * This class was taken from the Core Java 2 Volume II - Advanced
 * Features book, Prentice Hall, 2000
 * ISBN 0-13-081934-4
 * Chapter 7 - Advanced AWT
 *
 * @author Cay S. Horstmann & Gary Cornell
 * Modified By: John J. Blum
 * File: PrintPreviewDialog.java
 * @version v1.0
 * Date: 19 March 2002
 * Modification Date: 19 March 2002
 * @since Java 2
 */

package org.java.awt.print;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class PrintPreviewDialog extends JDialog implements ActionListener
{

  private JButton nextButton;
  private JButton previousButton;
  private JButton closeButton;

  private PrintPreviewCanvas canvas;

  public PrintPreviewDialog(Printable  p,
                            PageFormat pf,
                            int        pages)
  {
    Book book = new Book();

    book.append(p, pf, pages);
    layoutUI(book);
  }

  public PrintPreviewDialog(Book b)
  {
    layoutUI(b);
  }

  public void layoutUI(Book book)
  {
    setSize(200, 200);

    Container contentPane = getContentPane();

    canvas = new PrintPreviewCanvas(book);
    contentPane.add(canvas, "Center");

    JPanel buttonPanel = new JPanel();

    nextButton = new JButton("Next");
    buttonPanel.add(nextButton);
    nextButton.addActionListener(this);

    previousButton = new JButton("Previous");
    buttonPanel.add(previousButton);
    previousButton.addActionListener(this);

    closeButton = new JButton("Close");
    buttonPanel.add(closeButton);
    closeButton.addActionListener(this);

    contentPane.add(buttonPanel, "South");
  }

  public void actionPerformed(ActionEvent event)
  {
    Object source = event.getSource();

    if (source == nextButton)
      canvas.flipPage(1);
    else if (source == previousButton)
      canvas.flipPage(-1);
    else if (source == closeButton)
      setVisible(false);
  }

}

class PrintPreviewCanvas extends JPanel
{

  private int currentPage;

  private Book book;

  public PrintPreviewCanvas(Book b)
  {
    book = b;
    currentPage = 0;
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;

    PageFormat pageFormat = book.getPageFormat(currentPage);

    double xoff; // x offset of page start in window
    double yoff; // y offset of page start in window
    double scale; // scale factor to fit page in window
    double px = pageFormat.getWidth();
    double py = pageFormat.getHeight();
    double sx = getWidth() - 1;
    double sy = getHeight() - 1;

    if (px / py < sx / sy) // center horizontally
    {
      scale = sy / py;
      xoff = 0.5 * (sx - scale * px);
      yoff = 0;
    }
    else // center vertically
    {
      scale = sx / px;
      xoff = 0;
      yoff = 0.5 * (sy - scale * py);
    }

    g2.translate((float)xoff, (float)yoff);
    g2.scale((float)scale, (float)scale);

    // draw page outline (ignoring margins)
    Rectangle2D page = new Rectangle2D.Double(0, 0, px, py);

    g2.setPaint(Color.white);
    g2.fill(page);
    g2.setPaint(Color.black);
    g2.draw(page);

    Printable printable = book.getPrintable(currentPage);

    try
    {
      printable.print(g2, pageFormat, currentPage);
    }
    catch (PrinterException exception)
    {
      g2.draw(new Line2D.Double(0, 0, px, py));
      g2.draw(new Line2D.Double(0, px, 0, py));
    }
  }

  public void flipPage(int by)
  {
    int newPage = currentPage + by;

    if (0 <= newPage && newPage < book.getNumberOfPages())
    {
      currentPage = newPage;
      repaint();
    }
  }

}

