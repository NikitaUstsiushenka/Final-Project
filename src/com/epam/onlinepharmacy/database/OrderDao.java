package com.epam.onlinepharmacy.database;

import com.epam.onlinepharmacy.database.dao.AbstractOrderDao;
import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.entity.Order;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.OrderFactory;
import com.epam.onlinepharmacy.main.AbstractSQLQueries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements methods for work with table 'order'.
 *
 * @author Nikita
 * @version 1.0
 * @since 04.09.2018
 */
public final class OrderDao extends AbstractOrderDao {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(OrderDao.class);
    }

    /**
     * Public default constructor.
     */
    public OrderDao() {

        super();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> selectAll() throws ApplicationException {

        final ResultSet resultSet;
        final List<Order> orders = new ArrayList<>();
        final String debugString = getClass().getName()
                + ": All Order objects selected.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.SELECT_ALL_ORDERS);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                orders.add(((OrderFactory) super.getFactory())
                        .createEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        LOGGER.log(Level.DEBUG, debugString);

        return orders;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final Order order) throws ApplicationException {

        final String debugString = getClass().getName() + ": Object " + order
                + " inserted in table `order`.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.INSERT_ORDER);

            statement.setInt(1, order.getClientId());
            statement.setBigDecimal(2, new BigDecimal(order.getPrice()));
            statement.setDate(3, order.getDate());

            if (order.isPaid()) {
                statement.setInt(4, 1);
            } else {
                statement.setInt(4, 0);
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        LOGGER.log(Level.DEBUG, debugString);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order selectOrderByIdPaid(final int clientId, final boolean paid)
            throws ApplicationException {

        final Order order;
        final ResultSet resultSet;
        final String debugString1 = getClass().getName()
                + ": Order selected.";
        final String debugString2 = getClass().getName()
                + ": Order didn't select.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.SELECT_ORDER_ID_PAID);
            statement.setInt(1, clientId);

            if (paid) {
                statement.setInt(2, 1);
            } else {
                statement.setInt(2, 0);
            }

            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                order = ((OrderFactory) super.getFactory())
                        .createEntity(resultSet);
                LOGGER.log(Level.DEBUG, debugString1);

            } else {

                order = new Order();
                LOGGER.log(Level.DEBUG, debugString2);

            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        return order;

    }

}
