package samples;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sbhat.data.pojo.Post;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import utilities.LogManager;

/**
 * Class with all default posts.
 */
public final class DefaultPosts {
  public final static Post[] ALL;
  private final static String DATA_DIR =
      System.getProperty("user.dir") + File.separator + ".." + File.separator + "data";
  private final static String JSON_DATA =
      DATA_DIR + File.separator + "defaults" + File.separator + "defaultposts.json";
  private final static Logger LOGGER = LogManager.getLogger(DefaultPosts.class.getSimpleName());

  /**
   * Static block to read data from json file for default posts
   */
  static {
    Post[] posts = new Post[100];
    try {
      ObjectMapper mapper = new ObjectMapper();
      posts = mapper.readValue(new File(JSON_DATA), Post[].class);
    } catch (IOException e) {
      LOGGER.error("Error while reading default posts json {}", e);
    }
    ALL = posts;
  }

  /**
   * Default constructor.
   */
  private DefaultPosts() {

  }
}
