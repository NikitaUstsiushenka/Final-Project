package com.epam.onlinepharmacy.database;

import com.epam.onlinepharmacy.database.dao.AbstractOrderItemDao;
import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.entity.OrderItem;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.OrderItemFactory;
import com.epam.onlinepharmacy.main.AbstractSQLQueries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        final String debugString = getClass().getName()
                + ": All objects selected from table 'order_item'.";
        final ResultSet resultSet;
        final List<OrderItem> orderItems = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.SELECT_ALL_ORDER_ITEMS);

            resultSet = statement.executeQuery();

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
                    AbstractSQLQueries.INSERT_ORDER_ITEM);

            statement.setInt(1, orderItem.getOrderId());
            statement.setInt(2, orderItem.getDrugId());
            statement.setInt(3, orderItem.getRecipeId());
            statement.setInt(4, orderItem.getCount());

            if (statement.executeUpdate() != 0) {

                debugString = getClass().getName() + ": Object " + orderItem
                        + " inserted in table 'order_item'.";
                LOGGER.log(Level.DEBUG, debugString);

            } else {

                debugString = getClass().getName() + ": Object " + orderItem
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

}
