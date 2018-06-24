package com.sbhat.tests.api.posts.withdefaults;

import com.sbhat.tests.api.posts.PostsBaseTest;
import org.sbhat.data.pojo.Post;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import samples.DefaultPosts;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Tests for posts call.
 */
public class TestPostsCrudPositive extends PostsBaseTest {
  private Post[] postsInSystem;
  private Post defaultPostToBeManipulated;

  /**
   * Setup method to create variables for the test.
   */
  @BeforeClass(alwaysRun = true)
  public void testSetupCreationVariables() {
    postToBeCreated = postsClient.getValidRandomizedPost();
  }

  /**
   * Setup method to create variables for the test.
   */
  @BeforeMethod(alwaysRun = true)
  public void setupVariables() {
    postsInSystem = postsClient.getAllPostsInSystem();
    defaultPostToBeManipulated = postsInSystem[0];
  }

   /**
   * Test that runs first.
   * Ensure that no posts are present at the beginning of the run.
   */
  @Test(priority = -1)
  public void testGetAllPosts() {
    postsInSystem = postsClient.requestToGetAllPosts()
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .response()
        .as(Post[].class);

    ReflectionAssert.assertReflectionEquals(
        DefaultPosts.ALL,
        postsInSystem,
        ReflectionComparatorMode.LENIENT_ORDER);
  }

  /**
   * Test to create a post.
   */
  @Test
  public void testCreatePost() {
    Post postCreated = postsClient.requestCreatingAPost(postToBeCreated)
        .then()
        .assertThat()
        .statusCode(201)
        .extract()
        .response()
        .as(Post.class);
    Assert.assertEquals(postToBeCreated, postCreated, "Creation of post");
  }

  /**
   * Test to check if created post exists in all posts.
   */
  @Test(dependsOnMethods = "testCreatePost")
  public void testGetAllPostsAfterCreate() {
    // Assert that all posts contains the post to be created
    ArrayList postsCreated = postsClient.requestToGetAllPosts()
        .then()
        .assertThat()
        .statusCode(200)
        .and()
        .assertThat()
        .extract()
        .response()
        .as(ArrayList.class);
    Assert.assertTrue(postsCreated.size() == postToBeCreated.getId() &&
            postsCreated.contains(postToBeCreated),
        "Posts created:\n" + postsCreated + " doesn't contain post :\n" + postToBeCreated);
  }

  /**
   * Test to check if created post exists in all posts.
   */
  @Test(dependsOnMethods = "testCreatePost")
  public void testGetCreatedPost() {
    // Assert that all posts contains the post to be created
    Post postsCreated = postsClient.requestToGetAPost(postToBeCreated.getId())
        .then()
        .assertThat()
        .statusCode(200)
        .and()
        .assertThat()
        .extract()
        .response()
        .as(Post.class);
    Assert.assertEquals(postsCreated, postToBeCreated,
        "Post created");
  }

  /**
   * Test to update an existing post's user id.
   */
  @Test
  public void testUpdatePostUserId() {
    Post newPost = new Post(defaultPostToBeManipulated.getUserId()+1,
        defaultPostToBeManipulated.getId(),
        defaultPostToBeManipulated.getTitle(),
        defaultPostToBeManipulated.getBody());
    Post updatedPost = postsClient.requestToUpdateAPost(defaultPostToBeManipulated.getId(), newPost)
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .response()
        .as(Post.class);
    Assert.assertEquals(updatedPost, newPost, "Updating user id of " + defaultPostToBeManipulated);
    Post postAfterUpdate = postsClient.getAPost(defaultPostToBeManipulated.getId());
    Assert.assertEquals(postAfterUpdate, newPost, "Post after update");
  }

  /**
   * Test to update an existing post's title.
   */
  @Test
  public void testUpdatePostTitle() {
    Post newPost = new Post(defaultPostToBeManipulated.getUserId(),
        defaultPostToBeManipulated.getId(),
        defaultPostToBeManipulated.getTitle() + "a",
        defaultPostToBeManipulated.getBody());
    Post updatedPost = postsClient.requestToUpdateAPost(defaultPostToBeManipulated.getId(), newPost)
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .response()
        .as(Post.class);
    Assert.assertEquals(updatedPost, newPost, "Updating title of " + defaultPostToBeManipulated);
    Post postAfterUpdate = postsClient.getAPost(defaultPostToBeManipulated.getId());
    Assert.assertEquals(postAfterUpdate, newPost, "Post after update");
  }

  /**
   * Test to update an existing post's body.
   */
  @Test
  public void testUpdatePostBody() {
    Post newPost = new Post(defaultPostToBeManipulated.getUserId(),
        defaultPostToBeManipulated.getId(),
        defaultPostToBeManipulated.getTitle(),
        defaultPostToBeManipulated.getBody() + "a");
    Post updatedPost = postsClient.requestToUpdateAPost(defaultPostToBeManipulated.getId(), newPost)
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .response()
        .as(Post.class);
    Assert.assertEquals(newPost, updatedPost, "Updating body of " + defaultPostToBeManipulated);
    Post postAfterUpdate = postsClient.getAPost(defaultPostToBeManipulated.getId());
    Assert.assertEquals(postAfterUpdate, newPost, "Post after update");
  }

  /**
   * Test to update an existing post's id.
   */
  @Test
  public void testUpdatePostID() {
    Post newPost = new Post(postsClient.getStartOfValidIDRange(),
        defaultPostToBeManipulated.getId(),
        defaultPostToBeManipulated.getTitle(),
        defaultPostToBeManipulated.getBody());
    Post updatedPost = postsClient.requestToUpdateAPost(defaultPostToBeManipulated.getId(), newPost)
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .response()
        .as(Post.class);
    Assert.assertEquals(newPost, updatedPost, "Updating id of " + defaultPostToBeManipulated);
    Post postAfterUpdate = postsClient.getAPost(defaultPostToBeManipulated.getId());
    Assert.assertEquals(postAfterUpdate, newPost, "Post after update");
  }

  /**
   * Test to update an existing post.
   */
  @Test
  public void testPatchPost() {
    int newUserID = defaultPostToBeManipulated.getUserId() + 100;
    String newTitle = defaultPostToBeManipulated.getTitle() + "a";
    Post newPost = new Post(newUserID, newTitle);
    Post expectedPost =
        new Post(newUserID,
        defaultPostToBeManipulated.getId(),
        newTitle,
        defaultPostToBeManipulated.getBody());
    Post updatedPost = postsClient.requestToPatchAPost(defaultPostToBeManipulated.getId(), newPost)
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .response()
        .as(Post.class);
    Assert.assertEquals(expectedPost, updatedPost, "Post after patch");
    Post postAfterUpdate = postsClient.getAPost(defaultPostToBeManipulated.getId());
    Assert.assertEquals(postAfterUpdate, newPost, "Post after update");
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
}
