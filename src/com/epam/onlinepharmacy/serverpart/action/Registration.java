package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.database.DrugDao;
import com.epam.onlinepharmacy.database.OrderDao;
import com.epam.onlinepharmacy.database.OrderItemDao;
import com.epam.onlinepharmacy.database.UserDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.email.SendingEmail;
import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.entity.Order;
import com.epam.onlinepharmacy.entity.OrderItem;
import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.EntityFactory;
import com.epam.onlinepharmacy.factory.UserFactory;
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
import java.util.Random;

/**
 * This class implements interface "Action".
 *
 * @author Nikita
 * @version 1.0
 * @since 20.10.2018
 */
public final class Registration implements Action {

    /**
     * Logger for debug and error.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(Registration.class);
    }

    /**
     * Public default constructor.
     */
    public Registration() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
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
                        final HttpServletResponse response)
            throws IOException, ServletException {

        final EntityFactory factory = new UserFactory();
        final Random random = new Random();
        final Action action = new Moving();
        final String debugString;
        final List<Drug> drugs;
        final List<OrderItem> orders;
        final Order order;

        AbstractDao dao = new UserDao();
        User user;
        int code;

        try {

            if (request.getSession().getAttribute("code") != null
                    && request.getParameter("code") != null) {

                code = (int) request.getSession().getAttribute("code");
                user = (User) request.getSession().getAttribute("user");

                if (code == Integer.parseInt(request.getParameter("code"))) {

                    ((UserDao) dao).insert(user);
                    user = ((UserDao) dao).selectUser(user.getEmail());
                    request.getSession().setAttribute("user", user);
                    request.setAttribute("message",
                            "Registration successful.");

                    debugString = " User " + user + " has registered.";

                    if (user.getRole() == UserRole.CLIENT) {

                        dao = new DrugDao();
                        drugs = ((DrugDao) dao).selectAll();
                        dao = new OrderDao();
                        order = ((OrderDao) dao).selectOrder(
                                ((User) request.getSession()
                                        .getAttribute("user"))
                                        .getId(), false);
                        dao = new OrderItemDao();
                        orders = ((OrderItemDao) dao).selectOrderItem(
                                order.getId());
                        order.setOrderItems(orders);

                        request.getSession().setAttribute("drugs", drugs);
                        response.sendRedirect("pharmacy");

                    } else {
                        action.execute(request, response, user.getRole());
                    }

                } else {

                    debugString = " Entered code is invalid.";
                    request.setAttribute("errorMessage", "Invalid code.");
                    request.getRequestDispatcher(
                            AbstractProgramConstant.URL_CLIENT_CODE)
                            .forward(request, response);

                }

            } else {

                user = ((UserFactory) factory).createEntity(request);

                if (((UserDao) dao).isExistUser(user.getEmail())) {

                    debugString = " User with that e-mail already exist.";
                    request.setAttribute("errorMessage", debugString);
                    action.execute(request, response, UserRole.UNKNOWN);

                } else if (!user.getPassword().equals(request.getParameter(
                        "repeat_pas"))) {

                    debugString = " Entered passwords do not match.";
                    request.setAttribute("errorMessage", debugString);
                    action.execute(request, response, UserRole.UNKNOWN);

                } else {

                    debugString = " Code generating and sending by e-mail.";

                    code = AbstractProgramConstant.NUMBER_1000
                            + random.nextInt(
                            AbstractProgramConstant.NUMBER_9000);
                    SendingEmail.send(user.getEmail(), "Online-pharmacy Code",
                            "Code: " + code);

                    request.getSession().setAttribute("code", code);
                    request.getSession().setAttribute("user", user);
                    request.setAttribute("errorMessage",
                            "To confirm the registration, "
                                    + "enter the code sent to your email");
                    request.getRequestDispatcher(
                            AbstractProgramConstant.URL_CLIENT_CODE)
                            .forward(request, response);

                }

            }

            LOGGER.log(Level.DEBUG, debugString);

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

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
