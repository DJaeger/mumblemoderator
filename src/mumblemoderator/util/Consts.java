package mumblemoderator.util;

/**
 Collected constants of very general utility.

<P> All constants are immutable.
*/
public final class Consts  { 

  /**   Newline separator. This constant is especially useful. 
   Less experienced programmers will create new lines using '\r' and '\n' - but that  
   is faulty, since it's not portable between operating systems.  */
  public static final String NEW_LINE = System.getProperty("line.separator");
  public static final String FILE_SEPARATOR = System.getProperty("file.separator");
  public static final String PATH_SEPARATOR = System.getProperty("path.separator");

  public static final String EMPTY_STRING = "";
  public static final String SPACE = " ";
  public static final String TAB = "\t";

  public static final String APP_NAME = "APP";
  public static final String APP_VERSION = "ver";

  // PRIVATE //
  
  /** Prevent object construction.  */
  private Consts(){
    throw new AssertionError();
  }
}