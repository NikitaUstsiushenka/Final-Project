package com.epam.onlinepharmacy.factory;

import com.epam.onlinepharmacy.entity.OrderItem;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.main.AbstractProgramConstants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements EntityFactory.
 *
 * @author Nikita
 * @version 1.0
 * @since 29.09.2018
 */
public final class OrderItemFactory implements EntityFactory {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(OrderItemFactory.class);
    }

    /**
     * Public default constructor.
     */
    public OrderItemFactory() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderItem createEntity() {

        LOGGER.log(Level.DEBUG, AbstractProgramConstants.ORDER_ITEM_CREATED);

        return new OrderItem();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderItem createEntity(final HttpServletRequest request) {

        final OrderItem orderItem = new OrderItem();

        LOGGER.log(Level.DEBUG, AbstractProgramConstants.ORDER_ITEM_CREATED);

        return orderItem;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderItem createEntity(final ResultSet resultSet)
            throws ApplicationException {

        final OrderItem orderItem = new OrderItem();

        if (resultSet != null) {

            try {

                orderItem.setId(resultSet.getInt("id"));
                orderItem.setDrugId(resultSet.getInt("drug_id"));
                orderItem.setOrderId(resultSet.getInt("order_id"));
                orderItem.setRecipeId(resultSet.getInt("recipe_id"));
                orderItem.setCount(resultSet.getInt("count"));

            } catch (SQLException e) {
                throw new ApplicationException(e.getMessage());
            }

        } else {
            LOGGER.log(Level.DEBUG, AbstractProgramConstants.ATTRIBUTE_ERROR);
        }

        LOGGER.log(Level.DEBUG, AbstractProgramConstants.ORDER_ITEM_CREATED);

        return orderItem;

    }

}
