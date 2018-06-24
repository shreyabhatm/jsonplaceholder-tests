package com.sbhat.tests.api;

import org.slf4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utilities.LogManager;

import java.io.File;

/**
 * Base test class.
 */
public class BaseTest {
  //CHECKSTYLE.OFF: VisibilityModifier
  private File logsDirectory;
  protected Logger logger;
  //CHECKSTYLE.ON: VisibilityModifier

  /**
   * Setup log directory and logger.
   */
  @BeforeClass (alwaysRun = true)
  public void setupLogger() {
    logsDirectory = new File(System.getProperty("user.dir") + "/logs");
    logger = LogManager.getLogger(this.getClass().getSimpleName());
    logger.info("Starting test {}", this.getClass().getSimpleName());
  }

  /**
   * Setup log directory and logger.
   */
  @AfterClass(alwaysRun = true)
  public void tearDown() {
    logger.info("Ending test {}", this.getClass().getSimpleName());
  }

}
