/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FileFinder.java
 * @version v1.2
 * Date: 21 September 2001
 * Modification Date: 13 July 2002
 * @since Java 2
 */

package jjb.toolbox.util;

import java.io.File;
import java.io.FileFilter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jjb.toolbox.io.FileUtil;

public class FileFinder
{

  private static final Map FILE_PATH = new HashMap();
  private static final Map FILE_MAP  = new HashMap();

  private boolean     findall    = false;
  private boolean     found      = false;
  private boolean     ignorecase = false;
  private boolean     recurse    = true;
  private boolean     stats      = false;
  private boolean     verbose    = false;

  private File        basePath;

  private PrintStream printer;

  private String      filename;

  /**
   * Creates a new instace of the FileFinder class to
   * locate files in the file system of the localhost.
   * It initializes the FILE_PATH Map to cache lookups
   * for quick retrieval on subsequent duplicate
   * searches.
   * Default contructor for this class.
   */
  public FileFinder()
  {
    printer = System.out;
  }

  /**
   * Creates a new instace of the FileFinder class to
   * locate files in the file system of the localhost.
   * It initializes the FILE_PATH Map to cache lookups
   * for quick retrieval on subsequent duplicate
   * searches.
   *
   * @param findall is a boolean value determing if all
   * instances of the file in the file system of the
   * localhost should be found.
   * @param ignorecase is a boolean value indicating
   * that the FileFinder should ignore case when
   * comparing the file to files in the file system.
   * @param recurse is a boolean used to specify the
   * subdirectoris of the basePath should be searched
   * for the file as well.
   * @param stats is a boolean value used to print
   * program running statistics if true, false
   * otherwise.
   * @param verbose is a boolean value indicating that
   * the search operation should be traced.
   * @param printer is a Ljava.io.PrintStream object
   * used to output the trace on verbose operation.
   */
  public FileFinder(boolean     findall,
                    boolean     ignorecase,
                    boolean     recurse,
                    boolean     stats,
                    boolean     verbose,
                    PrintStream printer    )
  {
    this.findall = findall;
    this.ignorecase = ignorecase;
    this.recurse = recurse;
    this.stats = stats;
    this.verbose = verbose;
    this.printer = printer;
  }

  private class FileExtensionFilter implements FileFilter
  {
    private final Set fileExtensions;

    /**
     * Creates a new instance of the FileExtensionFilter
     * class used to filter the contents of the search
     * directory when calling the listFiles method of the
     * File object.
     *
     * @param extension is a Ljava.lang.String representation
     * of the file extension indicating that only files with
     * the following extension should be included in the list
     * of files determined by this filter.
     */
    private FileExtensionFilter(String fileExtension)
    {
      fileExtensions = new HashSet();
      fileExtensions.add(fileExtension);
    }

    /**
     * Creates a new instance of the FileExtensionFilter
     * class used to filter the contents of the search
     * directory when calling the listFiles method of the
     * File object.
     *
     * @param recurse is a boolean value indicating whether
     * subdirectories should be included in the file list.
     * @param fileExtensions is a [Ljava.lang.String array
     * containing file extensions of files to include when
     * the accept method is called by the listFiles method
     * of the File object.
     */
    private FileExtensionFilter(String[] fileExtensions)
    {
      this(Arrays.asList(fileExtensions));
    }

    /**
     * Creates a new instance of the FileExtensionFilter
     * class used to filter the contents of the search
     * directory when calling the listFiles method of the
     * File object.
     *
     * @param recurse is a boolean value indicating whether
     * subdirectories should be included in the file list.
     * @param fileExtensions is a Ljava.util.LIst object
     * containing file extensions of files to include when
     * the accept method is called by the listFiles method
     * of the File object.
     */
    private FileExtensionFilter(List fileExtensions)
    {
      this.fileExtensions = new HashSet(fileExtensions);
    }

    /**
     * accept tests whether or not the specified abstract pathname
     * should be included in a pathname list.
     *
     * @param pathname:Ljava.io.File is the abstract pathname to be tested.
     * @return a boolean value of true if and only if the pathname should
     * be included.
     */
    public boolean accept(File pathname)
    {
      if (pathname.isDirectory() && recurse)
        return true;

      if (pathname.isFile())
      {
        String fileExtension = FileUtil.getFileExtension(pathname);

        if (fileExtensions == null || fileExtensions.isEmpty() || fileExtensions.contains(fileExtension.toLowerCase()))
          return true;
      }

      return false;
    }
  }

  /**
   * locateFile traverses the file system of the localhost
   * for the file in question.
   *
   * @return a [Ljava.io.File array of the path(s) found
   * by the search.
   */
  private File[] locateFile()
  {
    if (filename == null)
      throw new NullPointerException("The name of the file to search must be specified!");

    return locateFile(filename,basePath);
  }

  /**
   * locateFile traverses the file system of the localhost
   * for the file in question.  The single parameter
   * locateFile method assumes a basepath determined by:
   * File.listRoots()[0].
   *
   * @param file is a Ljava.lang.String parameter indicating
   * the file name of the file to search for.
   * @return a [Ljava.io.File array of the path(s) found.
   */
  public File[] locateFile(String file)
  {
    return locateFile(file,File.listRoots()[0]);
  }

  /**
   * locateFile traverses the file system of the localhost
   * for the file in question.
   *
   * @param file is a Ljava.lang.String parameter indicating
   * the file name of the file to search for.
   * @param path is a Ljava.io.File object referencing the
   * base directory in which to start searching for the
   * desired file.
   * @return a [Ljava.io.File array of the path(s) found.
   */
  public File[] locateFile(String file,
                           File   basePath)
  {
    if (file == null)
      throw new NullPointerException("The file cannot be null.");

    if (basePath == null)
      throw new NullPointerException("The path must be provided to find the file in question.");

    if (FILE_PATH.containsKey(file))
      return (File[]) ((Set) FILE_PATH.get(file)).toArray(new File[0]);

    long t1 = System.currentTimeMillis();

    Set matches = new HashSet();

    found = false;
    locateFile(file,basePath,new FileExtensionFilter(FileUtil.getFileExtension(file)),matches,"");

    long t2 = System.currentTimeMillis();

    if (verbose || stats)
      printer.println("\nTotal Time: "+(t2-t1)+" milliseconds");

    if (found && matches.size() > 0)
    {
      FILE_PATH.put(file,matches);

      return (File[]) matches.toArray(new File[matches.size()]);
    }
    else
      return null;
  }

  /**
   * locateFile traverses the local file system for the file in question.
   * Depending on the program switches/parameters, the program will either
   * find all occurences or the first occurence and may or may not check
   * subdirectories for the file.  Another switch may be used to ignore case
   * which is useful when running this program on Unix/Linux boxes.
   *
   * @param file:Ljava.lang.String parameter indicating the file to search
   * for.
   * @param path:Ljava.io.File is the basePath from which to start looking
   * for the desired file.
   * @param matches:Ljava.util.Vector is a set of locations where the file
   * exists.
   * @param offset:Ljava.lang.String is a space offset in which to show the
   * different paths traversed.
   */
  private void locateFile(String     file,
                          File       path,
                          FileFilter filter,
                          Set        matches,
                          String     offset  )
  {
    try
    {
      if (verbose)
        printer.println(offset+path.toString());

      // NOTE: program is actually faster without the filter.
      File[] fileList = path.listFiles(); //path.listFiles(filter);

      if (fileList == null)
        return;

      for (int index = fileList.length; --index >= 0 && (findall || !found); )
      {
        File f = fileList[index];

        if (f.isDirectory() && recurse)
          locateFile(file,f,filter,matches,offset+"  ");
        else if (f.isFile())
        {
          String filename = f.getName();

          if (ignorecase)
          {
            if (found = filename.equalsIgnoreCase(file))
              matches.add(f);
          }
          else
          {
            if (found = filename.equals(file))
              matches.add(f);
          }

          if (verbose)
            printer.println(offset+f.toString()+(found ? "*" : ""));
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * findFiles
   */
  public Map findFiles(String[] files)
  {
    return findFiles(files,File.listRoots());
  }

  /**
   * findFiles
   */
  public Map findFiles(String[] files,
                       File     basePath)
  {
    if (basePath == null)
      throw new NullPointerException("The base path must be specified to find files.");

    return findFiles(files,new File[] { basePath });
  }

  /**
   * findFiles
   */
  public Map findFiles(String[] files,
                       File[]   roots )
  {
    if (files == null)
      throw new NullPointerException("The set of files cannot be null.");

    if (roots == null)
      throw new NullPointerException("A list of roots must be provided to find files.");

    if (roots.length == 0)
      throw new IllegalArgumentException("The File roots array must contain a path, or paths, to search for files.");

    if (files.length == 0)
      return null;

    FILE_MAP.clear();

    List fileExtensions = new ArrayList((int) (files.length * 1.50));

    String file = null;

    for (int index = files.length; --index >= 0; )
    {
      file = files[index].trim();

      if (!FILE_PATH.containsKey(ignorecase ? file.toLowerCase(): file))
      {
        fileExtensions.add(FileUtil.getFileExtension(files[index]));
        FILE_MAP.put((ignorecase ? file.toLowerCase() : file),null);
      }
    }

    long t1 = System.currentTimeMillis();

    for (int index = roots.length; --index >= 0; )
      findFiles(files,roots[index],new FileExtensionFilter(fileExtensions));

    long t2 = System.currentTimeMillis();

    if (findall)
    {
      Map.Entry entry = null;

      Object value = null;

      for (Iterator iter = FILE_MAP.entrySet().iterator(); iter.hasNext(); )
      {
        entry = (Map.Entry) iter.next();

        if ((value = entry.getValue()) != null)
          FILE_PATH.put(entry.getKey(),value);
      }
    }

    if (stats || verbose)
      System.out.println("\nTotal Time: "+(t2-t1));

    return Collections.unmodifiableMap(FILE_PATH);
  }

  /**
   * findFiles
   */
  private void findFiles(String[]   files,
                         File       basepath,
                         FileFilter filter   )
  {
    File[] fileList = basepath.listFiles(); //basepath.listFiles(filter);

    if (fileList == null)
      return;

    File theFile = null;

    for (int index = fileList.length; --index >= 0 && !FILE_MAP.isEmpty(); )
    {
      theFile = fileList[index];

      if (theFile.isDirectory() && recurse)
        findFiles(files,theFile,filter);
      else if (theFile.isFile())
      {
        String fileName = theFile.getName();

        fileName = (ignorecase ? fileName.toLowerCase() : fileName);

        // Found a file we were looking for if true.
        if (FILE_MAP.containsKey(fileName))
        {
          Set s1 = null;

          if (!findall)
          {
            FILE_MAP.remove(fileName);
            FILE_PATH.put(fileName,(s1 = new HashSet()));
            s1.add(theFile);
          }
          else
          {
            s1 = (Set) FILE_MAP.get(fileName);

            if (s1 == null)
              s1 = new HashSet();

            s1.add(theFile);
          }
        }
      }
    }
  }

  /**
   * init parses the arguments to the program initializing
   * various state properties of the FileFinder class.
   *
   * @param args:[Ljava.lang.String array containing the
   * arguments to this program.
   * @return a boolean value indicating that all parameters
   * were read in correctly and that the program may
   * begin searching.
   */
  private boolean init(String[] args)
  {
    for (int index = 0; index < args.length; index++)
    {
      String arg = args[index];

      if (arg.equalsIgnoreCase("-findall"))
        findall = true;
      else if (arg.equalsIgnoreCase("-ignorecase"))
        ignorecase = true;
      else if (arg.toLowerCase().startsWith("-recurse"))
      {
        int indx = arg.indexOf("=");

        if (indx == -1)
          throw new IllegalArgumentException("Invalid format for the -recurse switch!");

        try
        {
          recurse = Boolean.valueOf(arg.substring(indx+1).trim()).booleanValue();
        }
        catch (IndexOutOfBoundsException ignore)
        {
          recurse = false;
        }
      }
      else if (arg.equalsIgnoreCase("-stats"))
        stats = true;
      else if (arg.equalsIgnoreCase("-verbose"))
        verbose = true;
      else if (arg.equalsIgnoreCase("-?") || arg.equalsIgnoreCase("-help"))
      {
        usage();
        return false;
      }
      else if (!arg.startsWith("-") && basePath == null)
        basePath = new File(arg);
      else if (!arg.startsWith("-"))
        filename = arg;
      else
        throw new IllegalArgumentException(arg+" is an invalid switch!");
    }

    return true;
  }

  /**
   * usage prints program help information.
   */
  private static void usage()
  {
    System.out.println("FileFinder v1.2, Copyright (c) 2001, Code Primate");
    System.out.println("All Rights Reserved\n");
    System.out.println("usage: java FileFinder [options] <basepath> <file>\n");
    System.out.println("options:");
    System.out.println("\t -findall               sets whether FileFinder will find one");
    System.out.println("\t                        occurrence or all occurrences of the");
    System.out.println("\t                        file starting at the basepath.");
    System.out.println("\t -ignorecase            case insensitive file search.");
    System.out.println("\t -recurse=[false|true]  set whether FileFinder searches subdirectories,");
    System.out.println("\t                        default is true.");
    System.out.println("\t -stats                 gives statistics on program run time.");
    System.out.println("\t -verbose               verbose output");
    System.out.println("\t -? -help               print this help message\n");
  }

  /**
   * main is the executable method for this class.
   *
   * @param args is a [Ljava.lang.String array containing
   * the arguments to this program.
   */
  public static void main(String[] args)
  {
    try
    {
      if (args == null || args.length == 0)
      {
        usage();
        System.exit(1);
      }

      final FileFinder fileFinder = new FileFinder();

      boolean run = fileFinder.init(args);

      if (run)
      {
        File[] path = fileFinder.locateFile();

        if (path == null)
          System.out.println("File: "+fileFinder.filename+" NOT FOUND!");
        else
        {
          int numberOfPaths = path.length;

          for (int index = (path == null ? 0 : numberOfPaths); --index >= 0; )
            System.out.println("\nFile Location "+(numberOfPaths - index)+": "+path[index].toString());
        }
      }
    }
    catch (Exception e)
    {
      System.err.println("Error: "+e.getMessage());
    }
  }

}

