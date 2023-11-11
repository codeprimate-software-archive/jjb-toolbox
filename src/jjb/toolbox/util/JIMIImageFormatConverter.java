/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: JIMIImageFormatConverter.java
 * @version v1.0
 * Date: 22 July 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.util;

import java.awt.*;
import java.io.*;
import java.util.*;
import com.sun.jimi.core.*;

public class JIMIImageFormatConverter
{

  /**
   * convertImage reads an image file and then writes the image file using a
   * different format.
   *
   * @param inputFile:Ljava.lang.String is the path and filename of the image
   * to convert.
   * @param outputFile:Ljava.lang.String is the path and filename of the converted
   * image.
   */
  public static void convertImage(String inputFile,
                                  String outputFile)
  {
    printTest(inputFile,outputFile);

    try
    {
      JimiReader jreader = Jimi.createJimiReader(inputFile,Jimi.SYNCHRONOUS | Jimi.IN_MEMORY);

      Image img = jreader.getImage();

      JimiWriter jwriter = Jimi.createJimiWriter(outputFile);

      jwriter.setSource(img);
      jwriter.putImage(outputFile);
    }
    catch (JimiException je)
    {
      je.printStackTrace(System.out);
    }
  }

  /**
   * printTest is used in native applications to test that the JIMIImageConverter class
   * is loaded and running.
   *
   * @param inputFile:Ljava.lang.String is the path and filename of the image
   * to convert.
   * @param outputFile:Ljava.lang.String is the path and filename of the converted
   * image.
   */
  public static void printTest(String inputFile,
                               String outputFile)
  {
    try
    {
      PrintWriter logFile = new PrintWriter(new FileOutputStream("c:/temp/JIMILog.txt"),true);

      logFile.println(new Date().toString()+"::JIMIImageFormatConverter");
      logFile.println("Reading "+inputFile+" and writing to "+outputFile);

      logFile.flush();
      logFile.close();
    }
    catch (FileNotFoundException ignore)
    {
    }
  }

  /**
   * The main method loads the JAIImageFormatConverter and calls the
   * convertImage routine.  This method takes two arguments, the inputfile
   * and the output file.
   * 
   * @param args:[Ljava.lang.String array containing the arguments to this
   * program.
   */
  public static void main(String[] args) {
    if (args.length != 2)
    {
      System.out.println("java JIMI_ImageConverter <inputFile.mimetype> <outputFile.mimetype>\n");
      System.out.println("xxxFile	: driveLetter:\\path\\filename.ext");
      System.out.println("mimetype: BMP, JPG, TIFF, XPM, XBM, PNG, PSD, PICT, PCX, TGA");

      return;
    }

    String inputFile  = args[0],
           outputFile = args[1];

    convertImage(inputFile,outputFile);

    System.exit(0);
  }

}

