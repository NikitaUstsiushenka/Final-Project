package com.epam.onlinepharmacy.database;

import com.epam.onlinepharmacy.database.dao.AbstractOrderDao;
import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.entity.Order;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.OrderFactory;
import com.epam.onlinepharmacy.main.AbstractProgramConstant;
import com.epam.onlinepharmacy.main.AbstractSQLQuery;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        final String debugString = " All Order objects selected.";

        Connection connection = null;
        Statement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(
                    AbstractSQLQuery.SELECT_ALL_ORDERS);

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

        final String debugString = " Object " + order
                + " inserted in table `order`.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.INSERT_ORDER);

            statement.setInt(AbstractProgramConstant.NUMBER_1,
                    order.getClient().getId());
            statement.setBigDecimal(AbstractProgramConstant.NUMBER_2,
                    new BigDecimal(order.getPrice()));
            statement.setDate(AbstractProgramConstant.NUMBER_3,
                    order.getDate());

            if (order.isPaid()) {
                statement.setInt(AbstractProgramConstant.NUMBER_4, 1);
            } else {
                statement.setInt(AbstractProgramConstant.NUMBER_4, 0);
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
    public Order selectOrder(final int clientId, final boolean paid)
            throws ApplicationException {

        final Order order;
        final ResultSet resultSet;
        final String debugString1;
        final String debugString2 = " Order didn't select.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.SELECT_ORDER_ID_PAID);
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
                debugString1 = " Order " + order + " selected.";
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> selectOrder(final int clientId)
            throws ApplicationException {

        final List<Order> orders = new ArrayList<>();
        final ResultSet resultSet;
        final String debugString = " Orders selected by client id " + clientId;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.SELECT_ORDERS_BY_ID);

            statement.setInt(1, clientId);

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
    public void update(final int clientId,
                       final boolean paid, final double newPrice)
            throws ApplicationException {

        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.UPDATE_PRICE);

            statement.setBigDecimal(1, new BigDecimal(newPrice));
            statement.setInt(2, clientId);

            if (paid) {
                statement.setInt(AbstractProgramConstant.NUMBER_3, 1);
            } else {
                statement.setInt(AbstractProgramConstant.NUMBER_3, 0);
            }

            if (statement.executeUpdate() != 0) {

                debugString = "Order price with client id "
                        + clientId + " was updated.";

            } else {

                debugString = "Order price with client id "
                        + clientId + " didn't update.";
            }

            LOGGER.log(Level.DEBUG, debugString);

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final int orderId) throws ApplicationException {

        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.UPDATE_PAID);

            statement.setInt(1, 1);
            statement.setInt(2, orderId);

            if (statement.executeUpdate() != 0) {
                debugString = " Order with id " + orderId + " is paid.";
            } else {
                debugString = " Order with is " + orderId + " doesn't pay.";
            }

            LOGGER.log(Level.DEBUG, debugString);

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

    }

}
