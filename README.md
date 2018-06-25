# jsonplaceholder-tests
API tests for jsonplaceholder - a simple fake REST API.
API here : http://jsonplaceholder.typicode.com/
Source code here : https://github.com/typicode/jsonplaceholder

Language used + version:
  Java 1.8

Usage :
  To test the code is check-in ready and doesn't cause checkstyle violations, run :
  mvn -B compile

  To run tests :
    mvn clean install
    mvn test

  Reports should be generated under : tests/target
  Logs should be generated under : tests/logs

Assumptions :
1) As it wasn't clear from the requirements documentation if the environment should be clean, i.e,
   the environment should/shouldn't have default posts/comments etc, tests for posts call are placed in
   two different packages :
   (a)Placed under the package com.sbhat.tests.withoutdefaults:
      Tests written with the assumption that the environment has no default posts/comments.

   (b)Placed under the package com.sbhat.tests.withdefaults.
      Tests written with the assumption that the environment has default posts/comments etc.

   The reason this is important is the initial state of the product decides assertions in the tests.

   Both classes have tests that can be written in either of the packages. But to reduce redundancy, some tests have been
    included in only one of the packages.

2) Some of the tests can be covered in unit testing.
3) The tests here are functional tests ONLY. Long running, load testing and other non functional tests are not included.

Tools used :
1) Rest assured client : An equivalent alternative was to use html client from apache. As this is an established restful
 product,
it
is better to choose a client written specifically for restful end points. To ensure that a change in the future doesn't
require a lot of changes in the automation framework, a wrapper class is written for all api calls.
2) TestNG : An equivalent alternative would be Junit. TestNG is richer in features with support for clean dataproviders,
 parallelism, groups, dependency test and ordering of tests.

BUGS found :
1. Default posts present at the beginning of testing.
   Associated testcase : withoutdefaults.PostsTests.getAllPosts
2. When creating a post, it doesn't get added to list of posts.
   Associated testcase : withoutdefaults.PostsTests.getAllPostsAfterCreate
3. The error message when creating a post with existing id is not restful.
   The error message should have been in the json/xml error format but is in html format.
   Associated testcase : withdefaults.PostsTests.createADuplicatePost
4. Updating an existing post's id to an id used by another post using put call doesn't throw an error.
   Associated testcase : withdefaults.PostsTests.updatePostIDToDuplicateID
5. Deleting a post doesn't delete it from the backend.
   Associated testcase : TestPostsCrudPositive.testDeletePost
6. Updating a post doesn't update it in the backend.
   Associated testcase : TestPostsCrudPositive.testUpdatePostUserId
7. Patching a post doesn't update it in the backend.
   Associated testcase : TestPostsCrudPositive.testPatchPost

Issues up for discussion with developer/PM:
1. Should a post call without a message body to create a post using /posts api succeed?

Next steps :
1) Test the /comments, /albums, /photos, /todos, /users api
2) Test http vs https
3) Mark tests as sanity vs regression
4) Add load testing scenarios
5) Add tests with different data types (localised, different data formats)
6) Add db tests
7) Add tests for documentation