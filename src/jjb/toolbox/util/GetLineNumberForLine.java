/**
 * GetLineNumberForLine.java (c) 2002.11.15
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * The GetLineNumberForLine class searches for the first
 * occurence of the specified line in the specified file
 * and returns the line number on which the line was found
 * (contained, is more accurate).
 *
 * The class will return a -1 if the line does not exist
 * in the specified file.
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @see java.io.LineNumberReader
 */

package jjb.toolbox.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class GetLineNumberForLine {

  private static final int getLineNumber(final File file,
                                         final String lineOfText)
  throws IOException {
    if (!file.exists()) {
      throw new FileNotFoundException("The file \""+file.getAbsolutePath()
                                      +"\" does not exist.");
    }

    final LineNumberReader fileReader = new LineNumberReader(
      new BufferedReader(new FileReader(file)));
    String line = null;
    int lineNumber = -1;

    while ((line = fileReader.readLine()) != null) {
      //System.out.println(line);
      if (line.indexOf(lineOfText) != -1) {
        // Since I read the line, getLineNumber will actually
        // return the next line, must subtract 1.
        lineNumber = fileReader.getLineNumber() - 1;
        break;
      }
    }

    fileReader.close();

    return lineNumber;
  }

  private static final void printHelp() {
    final StringBuffer buffer = new StringBuffer();
    buffer.append("\nCopyright (c) 2002, Code Primate ALL RIGHTS RESERVED\n\n");
    buffer.append("use:\n\n");
    buffer.append("> java jjb.toolbox.util.GetLineNumberForLine <file name> <line of text>\n\n");
    buffer.append("where...\n");
    buffer.append("\t<file name>     is the name of the file to search.\n");
    buffer.append("\t<line of text>  is the line of text to report the line number for.\n");
    System.out.println(buffer);
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      printHelp();
      System.exit(1);
    }

    final File file = new File(args[0]);
    final String lineOfText = args[1];

    System.out.println("The line \""+lineOfText+"\" is on line: "+
      getLineNumber(file,lineOfText));
  }

}

