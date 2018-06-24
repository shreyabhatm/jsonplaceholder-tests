package org.sbhat.rest;

import io.restassured.response.Response;

import static org.sbhat.rest.CommentsClient.URL.COMMENTS_URI;
import static org.sbhat.rest.PostsClient.URL.POSTS_URI;

/**
 * Class for APIs that are common.
 */
public class NestedResourcesClient extends RestClient {
  /**
   * Default constructor.
   */
  public NestedResourcesClient() {
    super();
  }

  /**
   * Constructor with customized base api.
   * @param baseAPI customized base api
   */
  public NestedResourcesClient(String baseAPI) {
    super(baseAPI);
  }

  /**
   * Get comment for a post using nested api.
   * @param postId Post id to be received
   * @return Response object for the request
   */
  public Response getCommentForPost(int postId) {
    return get(String.format("%s/%d/%s", POSTS_URI, postId, COMMENTS_URI));
  }
}
