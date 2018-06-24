package com.sbhat.tests.api.posts;

import com.sbhat.tests.api.BaseTest;
import org.sbhat.data.pojo.Post;
import org.sbhat.rest.PostsClient;
import org.testng.annotations.BeforeClass;

/**
 * Base test class for posts related tests.
 */
public class PostsBaseTest extends BaseTest {
  //CHECKSTYLE.OFF: VisibilityModifier
  protected PostsClient postsClient = new PostsClient("https://jsonplaceholder.typicode.com");
  protected Post postToBeCreated;
  //CHECKSTYLE.ON: VisibilityModifier

  /**
   * Setup method to create variables needed for posts related tests.
   */
  @BeforeClass(alwaysRun = true)
  public void setupCreationVariables() {
    postToBeCreated = postsClient.getValidRandomizedPost();
  }

}
