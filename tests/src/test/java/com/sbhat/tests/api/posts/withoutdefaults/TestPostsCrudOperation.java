package com.sbhat.tests.api.posts.withoutdefaults;

import com.sbhat.tests.api.posts.PostsBaseTest;
import org.sbhat.data.pojo.Post;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Arrays;

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
public class TestPostsCrudOperation extends PostsBaseTest {

  /**
   * Test that runs first.
   * Ensure that no posts are present at the beginning of the run.
   */
  @Test(priority = -1)
  public void testGetAllPosts() {
    ArrayList posts = postsClient.requestToGetAllPosts()
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .response()
        .as(ArrayList.class);
    Assert.assertTrue(posts.size() == 0);
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
    Assert.assertEquals(postToBeCreated, postCreated, "Post as a response");
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
    Assert.assertTrue(postsCreated.size() == 1 && postsCreated.contains(postToBeCreated),
        "Posts created:\n" + postsCreated + " doesn't contain post :\n" + postToBeCreated);
  }

  /**
   * Test to get a post.
   */
  @Test(dependsOnMethods = "testCreatePost")
  public void testGetPost() {
    Post response = postsClient.requestCreatingAPost(postToBeCreated.getId())
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .response()
        .as(Post.class);
    Assert.assertTrue(response.equals(postToBeCreated));
  }

  /**
   * Test to create a post with an existing id.
   */
  @Test(dependsOnMethods = "testCreatePost")
  public void testCreateADuplicatePost() {
    postsClient.requestCreatingAPost(postToBeCreated)
        .then()
        .assertThat()
        .statusCode(500);
  }

  /**
   * Test to update an existing post.
   */
  @Test(dependsOnMethods = "testCreatePost")
  public void testUpdatePost() {
    Post newPost = new Post(postToBeCreated.getUserId()+1,
        postToBeCreated.getId(),
        postToBeCreated.getTitle(),
        postToBeCreated.getBody());
    Post updatedPost = postsClient.requestToUpdateAPost(postToBeCreated.getId(), newPost)
        .then()
        .assertThat()
        .statusCode(201)
        .extract()
        .response()
        .as(Post.class);
    Assert.assertEquals(newPost, updatedPost, "Post after update");
    Post postAfterUpdate = postsClient.getAPost(updatedPost.getId());
    Assert.assertEquals(postAfterUpdate, newPost, "Post after update");
    postToBeCreated = newPost;
  }

  /**
   * Test to delete an existing post.
   */
  @Test(dependsOnMethods = "testCreatePost")
  public void testDeletePost() {
    Post deletedPost = postsClient.requestToDeleteAPost(postToBeCreated.getId())
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .response()
        .as(Post.class);
    Assert.assertEquals(postToBeCreated, deletedPost, "Deleting a post");
    Assert.assertFalse(Arrays.asList(postsClient.getAllPostsInSystem()).contains(deletedPost),
        "Delete of a post");

  }

}
