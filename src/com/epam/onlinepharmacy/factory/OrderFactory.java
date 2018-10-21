package com.epam.onlinepharmacy.factory;

import com.epam.onlinepharmacy.database.UserDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.entity.Order;
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
        final String debugString = " Object " + order + " created.";

        LOGGER.log(Level.DEBUG, debugString);

        return order;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order createEntity(final HttpServletRequest request)
            throws ApplicationException {

        final String errorString
                = "Object Order can't be created from request.";

        LOGGER.log(Level.DEBUG, errorString);

        throw new ApplicationException(errorString);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order createEntity(final ResultSet resultSet) {

        final Order order = new Order();
        final AbstractDao dao = new UserDao();
        final String debugString1
                = " Attribute is null in method createEntity(ResultSet).";
        final String debugString2;

        if (resultSet != null) {

            try {

                order.setId(resultSet.getInt("id"));
                order.setClient(((UserDao) dao)
                        .selectUser(resultSet.getInt("client_id")));
                order.setPrice(resultSet.getDouble("price"));
                order.setDate(resultSet.getDate("date"));

                switch (resultSet.getInt("is_paid")) {

                    case 0:
                        order.setPaid(false);
                        break;
                    case 1:
                        order.setPaid(true);
                        break;
                    default:
                        break;

                }

            } catch (SQLException | ApplicationException e) {

                LOGGER.log(Level.ERROR, e.getMessage());
                e.printStackTrace();

            }

        } else {
            LOGGER.log(Level.DEBUG, debugString1);
        }

        debugString2 = " Object " + order + " created.";
        LOGGER.log(Level.DEBUG, debugString2);

        return order;

    }

}
