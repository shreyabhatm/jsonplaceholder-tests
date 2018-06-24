package com.sbhat.tests.api.posts.withdefaults;

import com.sbhat.tests.api.posts.PostsBaseTest;
import org.sbhat.data.pojo.Post;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Arrays;

/**
 * Tests for posts call.
 */
public class TestPostsCrudNegative extends PostsBaseTest {
  private Post[] postsInSystem;
  private Post defaultPostToBeManipulated;

  /**
   * Setup method to create variables needed for the tests.
   */
  @BeforeMethod(alwaysRun = true)
  public void setupVariables() {
    postsInSystem = postsClient.getAllPostsInSystem();
    defaultPostToBeManipulated = postsInSystem[0];
  }

  /**
   * Test to update an existing post's id to a duplicate id.
   */
  @Test
  public void testUpdatePostIDToDuplicateID() {
    Post newPost = new Post(postsInSystem[1].getUserId(),
        defaultPostToBeManipulated.getId(),
        defaultPostToBeManipulated.getTitle(),
        defaultPostToBeManipulated.getBody());
    postsClient.requestToUpdateAPost(defaultPostToBeManipulated.getId(), newPost)
        .then()
        .assertThat()
        .statusCode(500);
  }

  /**
   * Test to delete an existing post.
   * Setting priority 1 to ensure it runs last
   */
  @Test(priority = 1)
  public void testDeletePost() {
    postsClient.requestToDeleteAPost(defaultPostToBeManipulated.getId())
        .then()
        .assertThat()
        .statusCode(200);
    Post[] posts = postsClient.getAllPostsInSystem();
    Assert.assertTrue(!Arrays.asList(posts).contains(defaultPostToBeManipulated),
        "Post " + defaultPostToBeManipulated + " not deleted");
  }

  /**
   * Test to delete an existing post.
   * Setting priority -1 to ensure it runs last
   */
  @Test(dependsOnMethods = "testDeletePost")
  public void testDeleteAnAlreadyDeletedPost() {
    postsClient.requestToDeleteAPost(defaultPostToBeManipulated.getId())
        .then()
        .assertThat()
        .statusCode(404);
  }


  /**
   * Test to delete an existing post.
   * Setting priority 1 to ensure it runs last
   */
  @Test
  public void testDeleteANonExistentPost() {
    postsClient.requestToDeleteAPost(postsClient.getStartOfValidIDRange())
        .then()
        .assertThat()
        .statusCode(404);
  }

  /**
   * Test to create a post with a duplicate id.
   */
  @Test
  public void testCreateADuplicatePost() {
    postsClient.requestCreatingAPost(defaultPostToBeManipulated)
        .then()
        .assertThat()
        .statusCode(500);
  }
}
