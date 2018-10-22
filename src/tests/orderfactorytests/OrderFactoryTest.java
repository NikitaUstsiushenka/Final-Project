package tests.orderfactorytests;

import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.entity.Order;
import com.epam.onlinepharmacy.entity.Substance;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.DrugFactory;
import com.epam.onlinepharmacy.factory.EntityFactory;
import com.epam.onlinepharmacy.factory.OrderFactory;
import com.epam.onlinepharmacy.main.AbstractSQLQuery;
import com.epam.onlinepharmacy.main.DrugType;
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
public class OrderFactoryTest {

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

        factory = new OrderFactory();
        errorString = new String[]
                {"Error in method createEntity() from class OrderFactory.",
                        "Error in method createEntity(ResultSet) "
                                + "from class OrderFactory."};

    }

    /**
     * Test for method createEntity().
     * <p>
     * {@inheritDoc}
     */
    @Test
    public void testCreateEntity1() {

        Assert.assertEquals(new Order(),
                ((OrderFactory) factory).createEntity(), errorString[0]);

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
        final Order checkOrder = new Order();

        connection = ConnectionPool.getInstance().getConnection();
        statement = connection.prepareStatement(
                AbstractSQLQuery.SELECT_ORDER_ID_PAID);
        statement.setInt(1, 3);
        statement.setInt(2, 0);
        resultSet = statement.executeQuery();
        resultSet.next();

        Assert.assertEquals(checkOrder, factory.createEntity(resultSet),
                errorString[1]);

        connection.close();

    }

}