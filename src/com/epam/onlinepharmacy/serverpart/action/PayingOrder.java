package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.database.OrderDao;
import com.epam.onlinepharmacy.database.OrderItemDao;
import com.epam.onlinepharmacy.database.UserDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.entity.Order;
import com.epam.onlinepharmacy.entity.OrderItem;
import com.epam.onlinepharmacy.entity.User;
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
import java.util.List;

/**
 * This class implements interface "Action".
 *
 * @author Nikita
 * @version 1.0
 * @since 20.10.2018
 */
public final class PayingOrder implements Action {

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
    public PayingOrder() {
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

        final String debugString;
        final List<OrderItem> orders;

        AbstractDao dao;
        User user = (User) request.getSession().getAttribute("user");
        Order order = (Order) request.getSession().getAttribute("order");

        if (user.getCash() >= order.getPrice()) {

            debugString = " User " + user
                    + " has enough money to pay for the order.";

            try {

                dao = new OrderDao();
                ((OrderDao) dao).update(order.getId());

                dao = new UserDao();
                ((UserDao) dao).updateUserCash(user.getEmail(),
                        user.getCash() - order.getPrice());
                user = ((UserDao) dao).selectUser(user.getEmail());

                request.getSession().removeAttribute("order");
                request.getSession().removeAttribute("user");
                request.getSession().setAttribute("user", user);
                request.setAttribute("message", "Order successfully paid.");

                dao = new OrderDao();
                order = ((OrderDao) dao).selectOrder(
                        ((User) request.getSession().getAttribute("user"))
                                .getId(), false);
                dao = new OrderItemDao();
                orders = ((OrderItemDao) dao).selectOrderItem(
                        order.getId());
                order.setOrderItems(orders);
                request.getSession().setAttribute("order", order);

                response.sendRedirect("pharmacy");

            } catch (ApplicationException e) {

                LOGGER.log(Level.ERROR, e.getMessage());
                e.printStackTrace();

            }

        } else {

            request.setAttribute("errorMessage",
                    "You don't have enough money.");
            request.getRequestDispatcher(
                    AbstractProgramConstant.URL_CLIENT_CART)
                    .forward(request, response);

            debugString = " User " + user
                    + " hasn't enough money to pay for the order.";

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
