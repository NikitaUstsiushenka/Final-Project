package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.database.UserDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
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
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements interface "Action".
 *
 * @author Nikita
 * @version 1.0
 * @since 20.10.2018
 */
public final class ShowingUser implements Action {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(ShowingUser.class);
    }

    /**
     * Public default constructor.
     */
    public ShowingUser() {
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
            throws ServletException, IOException {

        final AbstractDao dao = new UserDao();
        final List<User> users;
        final String message;

        try {

            switch (role) {

                case CLIENT:
                    users = ((UserDao) dao).selectUser(UserRole.CLIENT);
                    message = "Clients don't exist.";
                    break;
                case ADMIN:
                    users = ((UserDao) dao).selectUser(UserRole.ADMIN);
                    message = "Admins don't exist.";
                    break;
                case PHARMACIST:
                    users = ((UserDao) dao).selectUser(UserRole.PHARMACIST);
                    message = "Pharmacists don't exist.";
                    break;
                default:
                    users = new ArrayList<>();
                    message = "User type is incorrect.";
                    break;

            }

            request.getSession().setAttribute("users", users);

            if (users.size() != 0) {
                request.getRequestDispatcher(
                        AbstractProgramConstant.URL_ADMIN_USERS)
                        .forward(request, response);
            } else {

                request.setAttribute("errorMessage", message);
                request.getRequestDispatcher(
                        AbstractProgramConstant.URL_ADMIN_JSP)
                        .forward(request, response);

            }

        } catch (ApplicationException e) {

            LOGGER.log(Level.DEBUG, e.getMessage());
            e.printStackTrace();

        }

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
