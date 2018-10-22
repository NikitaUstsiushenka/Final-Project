package tests.userfactorytests;

import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.EntityFactory;
import com.epam.onlinepharmacy.factory.OrderFactory;
import com.epam.onlinepharmacy.factory.UserFactory;
import com.epam.onlinepharmacy.main.AbstractSQLQuery;
import com.epam.onlinepharmacy.main.UserRole;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Test class for class "OrderFactory".
 *
 * @author Nikita
 * @version 1.0
 * @since 21.10.2018
 */
public class UserFactoryTest {

    /**
     * Value of the object EntityFactory.
     */
    private static EntityFactory factory;

    /**
     * Value of the first error message.
     */
    private static String[] errorString;

    /**
     * {@inheritDoc}
     */
    @BeforeClass
    public static void initialize() {

        factory = new UserFactory();
        errorString = new String[]
                {"Error in method createEntity() from class UserFactory.",
                        "Error in method createEntity(ResultSet) "
                                + "from class UserFactory."};

    }

    /**
     * Test for method createEntity().
     * <p>
     * {@inheritDoc}
     */
    @Test
    public void testCreateEntity1() {

        Assert.assertEquals(new User(),
                ((UserFactory) factory).createEntity(), errorString[0]);

    }

    /**
     * Test for method createEntity(HttpServletRequest).
     * <p>
     * {@inheritDoc}
     */
    @Test
    public void testCreateEntity2() throws ApplicationException, SQLException {

        final Connection connection;
        final PreparedStatement statement;
        final ResultSet resultSet;
        final User checkUser = new User();

        connection = ConnectionPool.getInstance().getConnection();
        statement = connection.prepareStatement(
                AbstractSQLQuery.SELECT_USER_BY_ID);
        statement.setInt(1, 2);
        resultSet = statement.executeQuery();
        resultSet.next();

        checkUser.setId(2);
        checkUser.setUserName("Egor");
        checkUser.setUserLastName("Karduban");
        checkUser.setEmail("egorkarduban@gmail.com");
        checkUser.setRole(UserRole.CLIENT);
        checkUser.setPassword("30650271454048f2062d84fb85ddf00"
                + "acb105ce28fa53f1f74386fef34ac43e2");
        checkUser.setCash(500.0);

        Assert.assertEquals(checkUser, factory.createEntity(resultSet),
                errorString[1]);

        connection.close();

    }

}