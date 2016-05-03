import models.Post;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by sissoko on 03/05/2016.
 */
public class PostUnitTest extends UnitTest {

    private static String TITLE = "Title of the problem";
    private static String PROBLEM = "The body of the problem";
    private static String SOLUTION = "This is a fake solution";

    @Test
    public void shouldCreatePostWhenTitleAndProblemAreGiven() {
        Post post = createPost(TITLE, PROBLEM);
        post.save();
        assertEquals(1, Post.find.findRowCount());
    }

    @Test
    public void shouldReturnPostWhenItsIdIsGiven() {
        Post post = createPost(TITLE, PROBLEM);
        post.save();
        Post fromDatabase = Post.find.byId(post.id);
        assertNotNull(fromDatabase);
    }

    @Test
    public void shouldUpdatePostWhenItsChanged() {
        Post post = createPost(TITLE, PROBLEM);
        post.save();
        Post fromDatabase = Post.find.byId(post.id);
        fromDatabase.setSolution(SOLUTION);
        fromDatabase.update();
        assertNotEquals(post.solution, fromDatabase.solution);
        assertEquals(SOLUTION, fromDatabase.solution);
    }

    private Post createPost(String title, String problem) {
        Post post = new Post();
        post.title = title;
        post.problem = problem;
        return post;
    }
}
