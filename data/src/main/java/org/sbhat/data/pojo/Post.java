package org.sbhat.data.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * POJO for the post response.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post implements Comparable {
  private Integer userId;
  private Integer id;
  private String title;
  private String body;
  // private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * Default constructor.
   */
  private Post() {
  }

  /**
   * Constructor for the post object.
   * NOTE : If the # of parameters increase, we could use builders
   * @param userId user id for the post
   * @param id identity of the post
   * @param title title of the post
   * @param body body of the post
   */
  public Post(Integer userId, Integer id, String title, String body) {
    this.userId = userId;
    this.id = id;
    this.title = title;
    this.body = body;
  }

  /**
   * Constructor for the post object.
   * @param userId user id for the post
   * @param title of the post
   */
  public Post(Integer userId, String title) {
    this.userId = userId;
    this.title = title;
  }

  /**
   * Constructor for the post object.
   * @param id identity of the post
   */
  public Post(Integer id) {
    this.id = id;
  }

  /**
   * Constructor for the post object.
   * @param userId user id for the post
   * @param title title of the post
   * @param body body of the post
   */
  public Post(Integer userId, String title, String body) {
    this.userId = userId;
    this.title = title;
    this.body = body;
  }

  /**
   * Get user id of the post.
   * @return user id of the post
   */
  public Integer getUserId() {
    return userId;
  }

  /**
   * Get id of the post.
   * @return id of the post
   */
  public Integer getId() {
    return id;
  }

  /**
   * Get title of the post.
   * @return title of the post
   */
  public String getTitle() {
    return title;
  }

  /**
   * Get body of the post.
   * @return body of the post
   */
  public String getBody() {
    return body;
  }

  /**
   * Overridden equals method for post.
   * @param o Object to be compared with this
   * @return If object o is equal to this
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Post post = (Post) o;

    if (userId != null ? !userId.equals(post.userId) : post.userId != null) {
      return false;
    }
    if (id != null ? !id.equals(post.id) : post.id != null) {
      return false;
    }
    if (title != null ? !title.equals(post.title) : post.title != null) {
      return false;
    }
    return body != null ? body.equals(post.body) : post.body == null;
  }

  /**
   * Overridden hashcode method.
   * @return hashcode of this
   */
  @Override
  public int hashCode() {
    int result = userId != null ? userId.hashCode() : 0;
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (body != null ? body.hashCode() : 0);
    return result;
  }

  @Override
  public int compareTo(Object obj) {
    Post post = (Post) obj;
    return id.compareTo(post.id);
  }

  @Override
  public String toString() {
    return "Post{" +
        "userId=" + userId +
        ", id=" + id +
        ", title='" + title + '\'' +
        ", body='" + body + '\'' +
        '}';
  }

}
