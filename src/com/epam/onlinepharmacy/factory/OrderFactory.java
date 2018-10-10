package com.epam.onlinepharmacy.factory;

import com.epam.onlinepharmacy.entity.Order;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

/**
 * This class implements EntityFactory.
 *
 * @author Nikita
 * @version 1.0
 * @since 04.09.2018
 */
public final class OrderFactory implements EntityFactory {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(OrderFactory.class);
    }

    /**
     * Public default constructor.
     */
    public OrderFactory() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order createEntity() {

        final Order order = new Order();
        final String debugString = getClass().getName() + ": Object "
                + order + " created.";

        LOGGER.log(Level.DEBUG, debugString);

        return order;

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public Order createEntity(final HttpServletRequest request) {

        final Order order = new Order();
        final String debugString1 = getClass().getName()
                + ": Attribute is null in method " +
                "createEntity(HttpServletRequest).";
        final String debugString2;

        if (request != null) {



        } else {
            LOGGER.log(Level.DEBUG, debugString1);
        }

        debugString2 = getClass().getName() + ": Object "
                + order + " created.";
        LOGGER.log(Level.DEBUG, debugString2);

        return order;

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public Order createEntity(final ResultSet resultSet) {

        final Order order = new Order();
        final String debugString1 = getClass().getName()
                + ": Attribute is null in method createEntity(ResultSet).";
        final String debugString2;

        if (resultSet != null) {



        } else {
            LOGGER.log(Level.DEBUG, debugString1);
        }

        debugString2 = getClass().getName() + ": Object "
                + order + " created.";
        LOGGER.log(Level.DEBUG, debugString2);

        return order;

    }

}
