package utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Log manager class to set log file names and get logger objects.
 */
public final class LogManager {

  /**
   * Default constructor.
   */
  private LogManager() {

  }

  /**
   * Get the logger object with logfilename.
   * @param logFileName file name of the log
   * @return Logger logger to help log messages
   */
  public static Logger getLogger(String logFileName) {
    MDC.put("loggerFileName", logFileName);
    return LoggerFactory.getLogger(logFileName);
  }

  /**
   * Set up log file for initialization process.
   */
  static {
    MDC.put("loggerFileName", "LogInitializer");
  }
}
