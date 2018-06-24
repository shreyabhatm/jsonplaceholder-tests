package org.sbhat.rest;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.sbhat.data.pojo.Post;
import java.util.Arrays;
import static org.sbhat.rest.PostsClient.URL.POSTS_URI;

/**
 * APIs related to /posts of Jsonplaceholder.
 */
public class PostsClient extends RestClient {
  /**
   * Default constructor.
   */
  public PostsClient() {
    super();
  }

  /**
   * Constructor to connect to customized base api.
   * @param baseAPI base API to connect to
   */
  public PostsClient(String baseAPI) {
    super(baseAPI);
  }

  /**
   * Request to get all posts.
   * @return Response object for request
   */
  public Response requestToGetAllPosts() {
    return get(POSTS_URI);
  }

  /**
   * Create a post.
   * @param postToBeCreated Post to be created
   * @return Response object for request
   */
  public Response requestCreatingAPost(Object postToBeCreated) {
    return post(POSTS_URI, postToBeCreated);
  }

  /**
   * Update a post.
   * @param postID post id to be updated
   * @param updateTo post to update to
   * @return Response object for request
   */
  public Response requestToUpdateAPost(int postID, Object updateTo) {
    return put(String.format("%s/%d", POSTS_URI, postID), updateTo);
  }

  /**
   * Send request to get a post.
   * @param postID Post id to get
   * @return Response object for request
   */
  public Response requestToGetAPost(int postID) {
    return get(String.format("%s/%d", POSTS_URI, postID));
  }

  /**
   * Get a post.
   * @param postID Post id to get
   * @return Response object for request
   */
  public Post getAPost(int postID) {
    return requestToGetAPost(postID).as(Post.class);
  }

  /**
   * Delete a post.
   * @param postID post ID to be deleted
   * @return Response object for request
   */
  public Response requestToDeleteAPost(Integer postID) {
    return delete(String.format("%s/%d", POSTS_URI, postID));
  }

  /**
   * Send a query for a post.
   * @param queryKey Query key to be updated
   * @param queryValue Query value
   * @return Response object for request
   */
  public Response sendQuery(String queryKey, String... queryValue) {
    return get(POSTS_URI, queryKey, queryValue);
  }

  /**
   * Patch a post.
   * @param postID post id to be updated
   * @param updateTo post to update to
   * @return Response object for request
   */
  public Response requestToPatchAPost(int postID, Object updateTo) {
    return patch(String.format("%s/%d", POSTS_URI, postID), updateTo);
  }

  /**
   * Get start point of range of valid ids.
   * @return A valid id, i.e, one that hasn't been used by previous posts.
   */
  public Integer getStartOfValidIDRange() {
    Post[] posts = requestToGetAllPosts().as(Post[].class);
    if (posts.length == 0) {
      return 1;
    }
    Arrays.sort(posts);
    return posts[posts.length -1].getId() + 1;
  }

  /**
   * Get a randomized string with a valid id.
   * @return Post object with valid id and random title and body
   */
  public Post getValidRandomizedPost() {
    return new Post(
        RandomUtils.nextInt(200, 500),
        getStartOfValidIDRange(),
        RandomStringUtils.randomAlphabetic(20),
        RandomStringUtils.randomAlphabetic(20));
  }

  /**
   * Get all posts in the system.
   * @return array of posts in the system
   */
  public Post[] getAllPostsInSystem() {
    return requestToGetAllPosts().as(Post[].class);
  }

  /**
   * Class with all URLs used for the api class.
   */
  final class URL {
    static final String POSTS_URI = "posts";
    /**
     * Default constructor.
     */
    private URL() {

    }
  }
}
