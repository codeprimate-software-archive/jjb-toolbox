
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class FindFinallyClausesWithAReturnStatement {

  private static final FileFilter JAVA_EXT_FILE_FILTER = new JavaFileFilter();

  private static final class JavaFileFilter implements FileFilter {
    public boolean accept(File file) {
      if (file.isDirectory())
        return true;

      final String filename = file.getName();
      int index = filename.indexOf(".");

      if (index == -1)
        return false;

      return filename.substring(index).trim().equalsIgnoreCase(".java");
    }
  }

  private static final boolean containsIndexes(final int beginIndex,
                                               final int endIndex,
                                               final int start,
                                               final int len        ) {
    boolean contains = ((start + len) > beginIndex);
    contains &= (start < endIndex);
    return contains;
  }

  private static final void printLinesBetweenIndexes(final String lineText,
                                                     final int beginIndex,
                                                     final int endIndex    ) {
    final StringTokenizer parser = new StringTokenizer(lineText,"\n");
    String line = "Line Not Found!";
    int lineLength = 0;
    int totalLength = 0;

    while (parser.hasMoreTokens() && (totalLength < endIndex)) {
      line = parser.nextToken();
      lineLength = line.length();

      if (containsIndexes(beginIndex,endIndex,totalLength,lineLength))
        System.out.println("\t"+line);

      totalLength += (lineLength + 1); // add one for the newline character.
    }
  }

  private static final void findReturnsInFinally(final File javaFile,
                                                 final String fileContents) {
    int bracketIndex = 0;
    int index = 0;
    int returnIndex = 0;
    boolean printFilePath = false;
    
    while ((index = fileContents.indexOf("finally",index)) != -1) {
      bracketIndex = fileContents.indexOf("}",index);
      returnIndex = fileContents.substring(index,bracketIndex).indexOf("return");
      /*System.out.println("Index: "+index+" Bracket Index: "
        +bracketIndex+" Return Index: "+returnIndex);*/

      if (returnIndex > 0) {
        if (!printFilePath) {
          System.out.println("Invalid Finally Clause In: "
            +javaFile.getAbsolutePath());
          printFilePath = true;
        }
        printLinesBetweenIndexes(fileContents,index,bracketIndex);
      }

      index += (bracketIndex - index);
    }
  }
  
  private static final void processFile(final File javaFile)
      throws IOException {
    final LineNumberReader fileReader =
      new LineNumberReader(new FileReader(javaFile));

    final StringBuffer buffer = new StringBuffer();
    String line = null;
    
    while ((line = fileReader.readLine()) != null) {
      buffer.append(fileReader.getLineNumber()).append(": ")
        .append(line).append("\n");
    }

    findReturnsInFinally(javaFile,buffer.toString());
    fileReader.close();
  }

  private static final void searchDirectory(final File directory)
      throws IOException {
    if (!directory.isDirectory())
      return;

    final File[] directoryContents = directory.listFiles(JAVA_EXT_FILE_FILTER);

    if (directoryContents == null)
      return;

    for (int index = directoryContents.length; --index >= 0; ) {
      final File theFile = directoryContents[index];
      if (theFile.isDirectory())
        searchDirectory(theFile);
      else
        processFile(theFile);
    }
  }

  private static final String usage() {
    final StringBuffer buffer = new StringBuffer();
    buffer.append("\n> java FindFinallyClausesWithReturnStatement <directory of search>\n");
    return buffer.toString();
  }

  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      System.err.println("Error: Specify a directory to search!");
      usage();
      System.exit(0);
    }
    System.out.println("Processing...\n");
    searchDirectory(new File(args[0]));
    System.out.println("\nDone!");
  }

}
