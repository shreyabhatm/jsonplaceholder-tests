package com.sbhat.tests.api.posts.withoutdefaults;

import com.sbhat.tests.api.posts.PostsBaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.sbhat.data.pojo.Post;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import static org.apache.commons.lang3.ArrayUtils.subarray;

/**
 * Tests for possible queries with posts.
 */
public class TestPostsQuery extends PostsBaseTest {

  // Map to maintain name to field. This is needed because testNG
  // naming of tests is not straight forward.
  private static HashMap<String, Field> nameToFieldMap = new HashMap<String, Field>();

  /**
   * Method to create sample data for the tests.
   */
  @BeforeClass(alwaysRun = true)
  public void createSample() {
    // Create a post with unique values for all fields
    postToBeCreated = new Post(
        RandomUtils.nextInt(1000, 2000),
        postsClient.getStartOfValidIDRange(),
        RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(10, 19)),
        RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(10, 19)));
    postsClient.requestCreatingAPost(postToBeCreated);

    // Uncomment code below to test for the case that defaults exist in the system
    // and to see how this test works.
    // Post[] postsInSystem = postsClient.getAllPostsInSystem();
    // postToBeCreated = postsInSystem[0];
  }

  /**
   * Method to get list of queries possible for posts.
   * This is to ensure that any new field added to the posts json (and therefore to pojo),
   * will automatically be picked up for testing
   * @return a list of queries valid for posts.
   *
   */
  @DataProvider(name="Queries")
  public static Object[][] queries() {
    ArrayList<Object[]> queries = new ArrayList<Object[]>();
    Field[] allFields = Post.class.getDeclaredFields();

    // Reflectively get all fields for the posts object
    for (Field field : allFields) {
      if (Modifier.isPrivate(field.getModifiers())) {
        nameToFieldMap.put(field.getName(), field);
        queries.add(new Object[]{field.getName()});
      }
    }

    // Create pairs of fields for query building
    //TODO : We could add more than 2 queries for testing.
    for (int i = 0; i < allFields.length; i++) {
      for (int j = i+1; j < allFields.length; j++) {
        queries.add(new Object[]{allFields[i].getName(), allFields[j].getName()});
      }
    }
    return queries.toArray(new Object[queries.size()][]);
  }

  /**
   * Test for queries through api.
   * @param fields Fields to run as queries
   */
  @Test (dataProvider = "Queries")
  public void testForQueries(String... fields) {
    ArrayList<String> queries = new ArrayList<>();
    for (String fieldName : fields) {
      Field field = nameToFieldMap.get(fieldName);
      try {
        // Add to queries the name of the query.
        queries.add(fieldName);
        // Add expected value of the query.
        field.setAccessible(true);
        queries.add(String.valueOf(field.get(postToBeCreated)));
      } catch (IllegalAccessException e) {
        logger.error("Cannot access {}, exception thrown: {} ",
            fieldName, ExceptionUtils.getStackTrace(e));
      }
    }
    logger.debug("Queries sent: {}", queries);
    String[] queriesArray = new String[queries.size()];
    queriesArray = queries.toArray(queriesArray);
    Post[] postsFromQuery = postsClient.sendQuery(queries.get(0),
        subarray(queriesArray, 1, queries.size()))
        .then()
        .statusCode(200)
        .extract()
        .response()
        .as(Post[].class);
    Assert.assertEquals(postsFromQuery.length, 1,"Number of queries returned");
    Assert.assertEquals(postToBeCreated, postsFromQuery[0], "Query returned");
  }
}
