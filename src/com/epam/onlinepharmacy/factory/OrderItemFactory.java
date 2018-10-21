package com.epam.onlinepharmacy.factory;

import com.epam.onlinepharmacy.database.DrugDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.entity.OrderItem;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
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

        final OrderItem orderItem = new OrderItem();
        final String debugString = " Object " + orderItem + " created.";

        LOGGER.log(Level.DEBUG, debugString);

        return orderItem;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderItem createEntity(final HttpServletRequest request)
            throws ApplicationException {

        final String debugString
                = "Object OrderItem can't be created from request.";

        LOGGER.log(Level.DEBUG, debugString);

        throw new ApplicationException();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderItem createEntity(final ResultSet resultSet)
            throws ApplicationException {

        final OrderItem orderItem = new OrderItem();
        final String debugString1
                = " Attribute is null in method createEntity(ResultSet).";
        final String debugString2;
        final AbstractDao dao = new DrugDao();

        if (resultSet != null) {

            try {

                orderItem.setId(resultSet.getInt("id"));
                orderItem.setDrug(((DrugDao) dao).selectDrug(resultSet
                        .getInt("drug_id")));
                orderItem.setOrderId(resultSet.getInt("order_id"));
                orderItem.setRecipeId(resultSet.getInt("recipe_id"));
                orderItem.setCount(resultSet.getInt("count"));

            } catch (SQLException e) {
                throw new ApplicationException(e.getMessage());
            }

        } else {
            LOGGER.log(Level.DEBUG, debugString1);
        }

        debugString2 = " Object " + orderItem + " created.";
        LOGGER.log(Level.DEBUG, debugString2);

        return orderItem;

    }

}
