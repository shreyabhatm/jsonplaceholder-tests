package org.sbhat.rest;

import io.restassured.response.Response;
import org.sbhat.data.pojo.Comment;
import static org.sbhat.rest.CommentsClient.URL.COMMENTS_URI;

/**
 * APIs related to /posts of Jsonplaceholder.
 */
public class CommentsClient extends RestClient {
  /**
   * Default constructor.
   */
  public CommentsClient() {
    super();
  }

  /**
   * Constructor for customized base API.
   * @param baseAPI base API to connect to
   */
  public CommentsClient(String baseAPI) {
    super(baseAPI);
  }

  /**
   * Request to get all comments.
   * @return Response object for request
   */
  public Response getAllComments() {
    return get(COMMENTS_URI);
  }

  /**
   * Create a comment.
   * @param commentToBeCreated Comment to be created
   * @return Response object for request
   */
  public Response createAComment(Object commentToBeCreated) {
    return post(COMMENTS_URI, commentToBeCreated);
  }

  /**
   * Get start point of range of valid ids.
   * @return A valid id, i.e, one that hasn't been used by previous posts.
   */
  public Integer getStartOfValidIDRange() {
    Comment[] comments = getAllComments().as(Comment[].class);
    if (comments.length == 0) {
      return 1;
    }
    return comments[comments.length -1].getId() + 1;
  }


  /**
   * Class with all URLs used for the api class.
   */
  final class URL {
    static final String COMMENTS_URI = "comments";

    /**
     * Default constructor.
     */
    private URL() {

    }
  }
}
