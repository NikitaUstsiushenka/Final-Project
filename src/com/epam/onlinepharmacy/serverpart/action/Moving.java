package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.database.DrugDao;
import com.epam.onlinepharmacy.database.OrderDao;
import com.epam.onlinepharmacy.database.OrderItemDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.entity.Drug;
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
public final class Moving implements Action {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(Moving.class);
    }

    /**
     * Public default constructor.
     */
    public Moving() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response)
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
                        final UserRole role) throws ApplicationException {

        final List<Drug> drugs;
        final List<OrderItem> orders;
        final Order order;
        final String debugString;

        AbstractDao dao;

        try {

            switch (role) {

                case CLIENT:
                    debugString = " Move to client page.";
                    dao = new DrugDao();
                    drugs = ((DrugDao) dao).selectAll();
                    dao = new OrderDao();
                    order = ((OrderDao) dao).selectOrder(
                            ((User) request.getSession().getAttribute("user"))
                                    .getId(), false);
                    dao = new OrderItemDao();
                    orders = ((OrderItemDao) dao).selectOrderItem(
                            order.getId());
                    order.setOrderItems(orders);
                    request.getSession().setAttribute("drugs", drugs);
                    request.getRequestDispatcher(
                            AbstractProgramConstant.URL_CLIENT_JSP)
                            .forward(request, response);
                    break;
                case ADMIN:
                    debugString = " Move to administrator page.";
                    request.getRequestDispatcher(
                            AbstractProgramConstant.URL_ADMIN_JSP)
                            .forward(request, response);
                    break;
                case DOCTOR:
                    debugString = " Move to doctor page.";
                    request.getRequestDispatcher(
                            AbstractProgramConstant.URL_DOCTOR_JSP)
                            .forward(request, response);
                    break;
                case PHARMACIST:
                    debugString = " Move to pharmacist page.";
                    request.getRequestDispatcher(
                            AbstractProgramConstant.URL_PHARM_JSP)
                            .forward(request, response);
                    break;
                default:
                    debugString = " Move to start page.";
                    request.getRequestDispatcher("/index.jsp")
                            .forward(request, response);
                    break;

            }

            LOGGER.log(Level.DEBUG, debugString);

        } catch (ServletException | IOException e) {
            throw new ApplicationException(e.getMessage());
        }

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
