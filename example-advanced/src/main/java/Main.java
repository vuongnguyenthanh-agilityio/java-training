import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
  static final Logger logger =  LogManager.getLogger(Main.class.getName());

  public static void main(String[] args) {
    System.out.println("Hello World Maven!");
    logger.error("TEST Logger");
  }
}
