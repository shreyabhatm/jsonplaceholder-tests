package com.sbhat.tests.api.common;

import com.sbhat.tests.api.BaseTest;
import org.sbhat.data.pojo.Comment;
import org.sbhat.data.pojo.Post;
import org.sbhat.rest.CommentsClient;
import org.sbhat.rest.NestedResourcesClient;
import org.sbhat.rest.PostsClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Test for nested resources.
 */
public class TestNestedResources extends BaseTest{
  private CommentsClient commentsClient = new CommentsClient();
  private PostsClient postsClient = new PostsClient();
  private NestedResourcesClient nestedResourcesClient = new NestedResourcesClient();

  /**
   * Test to get comments for posts using nested resources.
   */
  @Test
  public void testToGetCommentsForPosts() {
    // Uncomment the following when testing on clean environment.
    // Post post = postsClient.getValidRandomizedPost();
    // Comment commentToBeCreated = new Comment(
    //   post.getId(),
    //    commentsClient.getStartOfValidIDRange(),
    // RandomStringUtils.random(10),
    //    String.format( "%s@%s.com", RandomStringUtils.random(2), RandomStringUtils.random(3)),
    //   RandomStringUtils.random(10));
    // Comment commentCreated = commentsClient.createAComment(commentToBeCreated).as(Comment.class);
    // Comment[] commentReceived = nestedResourcesClient.getCommentForPost(post.getId()).as(Comment[].class);
    // Assert.assertEquals(1, commentReceived.length, "Comment received");
    // Assert.assertEquals(commentReceived, commentCreated, "Comment from nested resources");

    Comment commentCreated = commentsClient.getAllComments().as(Comment[].class)[0];
    Post post = postsClient.requestCreatingAPost(commentCreated.getPostId()).as(Post.class);
    Comment[] commentReceived = nestedResourcesClient.getCommentForPost(post.getId()).as(Comment[].class);
    Assert.assertEquals(5, commentReceived.length, "Comment received");
    Assert.assertTrue(Arrays.asList(commentReceived).contains(commentCreated),
        "Comment from nested resources");
  }
}
