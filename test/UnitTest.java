import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import models.Post;
import models.User;
import play.Application;
import org.junit.After;
import org.junit.Before;
import play.ApplicationLoader;
import play.Environment;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceApplicationLoader;
import play.test.Helpers;
import tools.Hash;
import tools.HashAdapter;

/**
 * Created by sissoko on 03/05/2016.
 */
public class UnitTest {

    @Inject
    Application application;

    @Before
    public void setUp() {
        com.google.inject.Module testModule = new AbstractModule() {
            @Override
            public void configure() {
                bind(Hash.class).to(HashAdapter.class);
            }
        };

        GuiceApplicationBuilder builder = new GuiceApplicationLoader()
                .builder(new ApplicationLoader.Context(Environment.simple()))
                .overrides(testModule);
        Guice.createInjector(builder.applicationModule()).injectMembers(this);
        Helpers.start(application);
    }

    @After
    public void tearDown() {
        User.find.db().execute(User.find.db().createSqlUpdate("delete from t_users"));
        Post.find.db().execute(Post.find.db().createSqlUpdate("delete from t_posts"));
        Helpers.stop(application);
    }
}
