package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.database.OrderDao;
import com.epam.onlinepharmacy.database.OrderItemDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.entity.Order;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.main.AbstractProgramConstant;
import com.epam.onlinepharmacy.main.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class implements interface "Action".
 *
 * @author Nikita
 * @version 1.0
 * @since 20.10.2018
 */
public final class ShowingCartProduct implements Action {

    /**
     * Logger for debug and error.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(ShowingClientOrder.class);
    }

    /**
     * Public default constructor.
     */
    public ShowingCartProduct() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response)
            throws IOException, ServletException {

        final Order order = (Order) request.getSession().getAttribute("order");
        final Action action = new Moving();
        final String debugString;

        if (order != null && order.getOrderItems().size() != 0) {

            request.getRequestDispatcher(
                    AbstractProgramConstant.URL_CLIENT_CART)
                    .forward(request, response);
            debugString = " Go to the page with orders.";

        } else {

            debugString = " User doesn't have orders.";

            try {

                request.setAttribute("errorMessage", "You don't have orders.");
                action.execute(request, response, UserRole.CLIENT);

            } catch (ApplicationException e) {

                LOGGER.log(Level.ERROR, e.getMessage());
                e.printStackTrace();

            }

        }

        LOGGER.log(Level.DEBUG, debugString);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response,
                        final UserRole role) throws ApplicationException {

        final String errorString
                = " This method can't be call from this class.";

        throw new ApplicationException(errorString);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response,
                        final String url)
            throws ApplicationException {

        final String errorString
                = " This method can't be call from this class.";

        throw new ApplicationException(errorString);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response,
                        final String url, final String parameter)
            throws ApplicationException {

        final String errorString
                = " This method can't be call from this class.";

        throw new ApplicationException(errorString);

    }

}
