package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.database.UserDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
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
public final class Authorization implements Action {

    /**
     * Logger for debug and error.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(Authorization.class);
    }

    /**
     * Public default constructor.
     */
    public Authorization() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response,
                        final UserRole role)
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
                        final HttpServletResponse response)
            throws ServletException, IOException {

        final AbstractDao dao = new UserDao();
        final String email = request.getParameter("email");
        final String pass = request.getParameter("password");
        final Action action = new Moving();
        final User user;
        final String debugString;

        try {

            if (((UserDao) dao).isExistUser(email, pass)) {

                user = ((UserDao) dao).selectUser(email);

                request.getSession().setAttribute("user", user);
                request.setAttribute("message",
                        "Hi, " + user.getUserName() + " "
                                + user.getUserLastName());

                action.execute(request, response, user.getRole());
                debugString = " User with email " + email
                        + " and password " + pass + " sign in.";

            } else {

                debugString = " User with email " + email
                        + " and password " + pass + " doesn't sign in.";
                request.setAttribute("errorMessage", "Incorrect data "
                        + "entered, check your login (password).");
                action.execute(request, response, UserRole.UNKNOWN);

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
