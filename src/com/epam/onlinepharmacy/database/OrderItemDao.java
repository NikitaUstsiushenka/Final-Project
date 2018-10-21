package com.epam.onlinepharmacy.database;

import com.epam.onlinepharmacy.database.dao.AbstractOrderItemDao;
import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.entity.OrderItem;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.OrderItemFactory;
import com.epam.onlinepharmacy.main.AbstractProgramConstant;
import com.epam.onlinepharmacy.main.AbstractSQLQuery;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements methods for work with table 'order_item'.
 *
 * @author Nikita
 * @version 1.0
 * @since 03.09.2018
 */
public final class OrderItemDao extends AbstractOrderItemDao {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(OrderItemDao.class);
    }

    /**
     * Public default constructor.
     */
    public OrderItemDao() {

        super();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderItem> selectAll() throws ApplicationException {

        final String debugString
                = " All objects selected from table 'order_item'.";
        final ResultSet resultSet;
        final List<OrderItem> orderItems = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(
                    AbstractSQLQuery.SELECT_ALL_ORDER_ITEMS);

            while (resultSet.next()) {
                orderItems.add(((OrderItemFactory) super.getFactory())
                        .createEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        LOGGER.log(Level.DEBUG, debugString);

        return orderItems;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final OrderItem orderItem) throws ApplicationException {

        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.INSERT_ORDER_ITEM);

            statement.setInt(AbstractProgramConstant.NUMBER_1,
                    orderItem.getOrderId());
            statement.setInt(AbstractProgramConstant.NUMBER_2,
                    orderItem.getDrug().getId());
            statement.setInt(AbstractProgramConstant.NUMBER_3,
                    orderItem.getRecipeId());
            statement.setInt(AbstractProgramConstant.NUMBER_4,
                    orderItem.getCount());

            if (statement.executeUpdate() != 0) {

                debugString = " Object " + orderItem
                        + " inserted in table 'order_item'.";
                LOGGER.log(Level.DEBUG, debugString);

            } else {

                debugString = " Object " + orderItem
                        + " didn't insert in table 'order_item'.";
                LOGGER.log(Level.DEBUG, debugString);

            }

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
    public List<OrderItem> selectOrderItem(final int orderId)
            throws ApplicationException {

        final ResultSet resultSet;
        final List<OrderItem> orderItems = new ArrayList<>();
        final String debugString
                = " All OrderItem objects selected from table `order_item`.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.SELECT_ITEMS_BY_ORDER_ID);

            statement.setInt(1, orderId);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                orderItems.add(((OrderItemFactory) super.getFactory())
                        .createEntity(resultSet));
            }

            System.out.println(orderItems.size());

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        LOGGER.log(Level.DEBUG, debugString);

        return orderItems;

    }

}
