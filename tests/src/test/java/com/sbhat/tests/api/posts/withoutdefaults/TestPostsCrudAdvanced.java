package com.sbhat.tests.api.posts.withoutdefaults;
import com.sbhat.tests.api.posts.PostsBaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.sbhat.data.pojo.Post;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for posts call.
 * We need to make decisions for how to automate tests for posts calls.
 * The 2 ways to test it is :
 * 1. Create 4 independent test cases for CRUD operations. For read, just before the get,
 * we create a post to be read in the same test.
 * 2. Create a chain of tests that are dependent on each other.
 * We create a test to add a new post, a dependent test to update the new post,
 * a dependent test to delete the new post. This saves us time and lines of code. If there is
 * an issue with adding a new post, the above mentioned scenario will also fail.
 * I am, therefore, taking path#2.
 *
 */
public class TestPostsCrudAdvanced extends PostsBaseTest {
  /**
   * Test to create a post with localized data.
   */
  @Test
  public void testCreateAPostWithLocalizedData() {
    Post post = new Post(1, postsClient.getStartOfValidIDRange(),
        RandomStringUtils.random(10),
        RandomStringUtils.random(10));
    Post postCreated = postsClient.requestCreatingAPost(post)
        .then()
        .assertThat()
        .statusCode(201)
        .extract()
        .response()
        .as(Post.class);
    Assert.assertEquals(post, postCreated, "Localised data fails at creation");
    Post postInSystem = postsClient.getAPost(postCreated.getId());
    Assert.assertEquals(postInSystem, postCreated, "Post in system");
  }


  /**
   * Test to check if id is uniquely and incrementally created when not dynamically created and not provided in the post
   * body.
   */
  @Test
  public void testCreateAPostWithoutID() {
    Post post = new Post(1,
        RandomStringUtils.random(10),
        RandomStringUtils.random(10));
    Post postCreated = postsClient.requestCreatingAPost(post)
        .then()
        .assertThat()
        .statusCode(201)
        .extract()
        .response()
        .as(Post.class);
    Assert.assertEquals(postsClient.getStartOfValidIDRange(), postCreated.getId(),
        "Non incremental id not generated.");
    Post postInSystem = postsClient.getAPost(postCreated.getId());
    Assert.assertEquals(postInSystem, postCreated, "Post in system");
  }

  /**
   * Test to check if no user id, body or title is provided, the fields are not dynamically generated.
   */
  @Test
  public void testCreateAPostWithOnlyID() {
    Post post = new Post(postsClient.getStartOfValidIDRange());
    Post postCreated = postsClient.requestCreatingAPost(post)
        .then()
        .assertThat()
        .statusCode(201)
        .extract()
        .response()
        .as(Post.class);
    Assert.assertTrue(postCreated.getBody() == null && postCreated.getTitle() == null && postCreated.getUserId() ==
        null);
    Post postInSystem = postsClient.getAPost(postCreated.getId());
    Assert.assertEquals(postInSystem, postCreated, "Post in system");
  }


}
