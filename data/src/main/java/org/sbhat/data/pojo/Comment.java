package org.sbhat.data.pojo;

/**
 * POJO for the comment response.
 */
public class Comment {
  private Integer postId;
  private Integer id;
  private String name;
  private String email;
  private String body;

  /**
   * Default constructor.
   */
  private Comment() {
  }

  /**
   * Constructor for comment.
   * @param postId Post id related to comment
   * @param id comment id
   * @param name name of comment
   * @param email email of comment
   * @param body body of comment
   */
  public Comment(Integer postId, Integer id, String name, String email, String body) {
    this.postId = postId;
    this.id = id;
    this.name = name;
    this.email = email;
    this.body = body;
  }

  /**
   * Get post id of the comment.
   * @return post id of the comment
   */
  public Integer getPostId() {
    return postId;
  }

  /**
   * Get comment id.
   * @return comment id
   */
  public Integer getId() {
    return id;
  }

  /**
   * Get name of the comment.
   * @return comment name
   */
  public String getName() {
    return name;
  }

  /**
   * Get email of the comment.
   * @return email of the comment
   */
  public String getEmail() {
    return email;
  }

  /**
   * Get body of the comment.
   * @return body of the comment
   */
  public String getBody() {
    return body;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Comment comment = (Comment) o;

    if (postId != null ? !postId.equals(comment.postId) : comment.postId != null) {
      return false;
    }
    if (id != null ? !id.equals(comment.id) : comment.id != null) {
      return false;
    }
    if (name != null ? !name.equals(comment.name) : comment.name != null) {
      return false;
    }
    if (email != null ? !email.equals(comment.email) : comment.email != null) {
      return false;
    }
    return body != null ? body.equals(comment.body) : comment.body == null;
  }

  @Override
  public int hashCode() {
    int result = postId != null ? postId.hashCode() : 0;
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (body != null ? body.hashCode() : 0);
    return result;
  }
}