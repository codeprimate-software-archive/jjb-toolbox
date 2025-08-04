/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: JAIImageFormatConverter.java
 * @version v1.0
 * Date: 22 July 2001
 * Modification Date: 17 April 2002
 * @since Java 2
 */

package jjb.toolbox.util;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.media.jai.*;
import com.sun.media.jai.codec.*;

public class JAIImageFormatConverter
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
    convertImage(inputFile,outputFile,"JPEG");
  }

  /**
   * convertImage reads an image file and then writes the image file using a
   * different format specified by the mimetype parameter.
   *
   * @param inputFile:Ljava.lang.String is the path and filename of the image
   * to convert.
   * @param outputFile:Ljava.lang.String is the path and filename of the converted
   * image.
   * @param mimetype:Ljava.lang.String is the mimetype of the file format to
   * write the new image.
   */
  public static void convertImage(String inputFile,
                                  String outputFile,
                                  String mimetype   )
  {
    printTest(inputFile,outputFile);

    if (mimetype == null && mimetype.equals("")) return;

    try
    {
      RenderedOp src = JAI.create("fileload",inputFile);

      // System.out.println("BITS / PIXEL: "+src.getColorModel().getPixelSize());

      FileOutputStream dest = new FileOutputStream(outputFile);

      JAI.create("encode",src,dest,mimetype,null);
    }
    catch (FileNotFoundException fnfe)
    {
      fnfe.printStackTrace(System.out);
    }
  }

  /**
   * printTest is used in native applications to test that the JAIImageConverter class
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
      PrintWriter logFile = new PrintWriter(new FileOutputStream("c:/temp/JAIImageFormatConverterLog.txt"),true);

      logFile.println(new Date().toString()+"::JAIImageFormatConverter");
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
  public static void main(String[] args)
  {
    if (args.length != 2)
    {
      System.out.println("\nUSAGE   : java JAI_ImageConverter <inputFile.mimetype> <outputFile.mimetype> |<mimetype for conversion>|\n");
      System.out.println("xxxFile	: driveLetter:\\path\\filename.ext");
      System.out.println("mimetype: TIFF, PNM, JPEG, PNG, GIF, FPX, BMP\n");

      Enumeration codecs = ImageCodec.getCodecs();

      while (codecs.hasMoreElements())
        System.out.println(codecs.nextElement().getClass().getName());

      System.out.println("\nDefault image format for conversion is JPEG.");

      return;
    }

    String inputFile  = args[0],
           outputFile = args[1];

    convertImage(inputFile,outputFile);
  }

}

