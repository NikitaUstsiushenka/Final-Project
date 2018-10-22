package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.database.UserDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.email.SendingEmail;
import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.main.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public final class AddingMoney implements Action {

    /**
     * Logger for debug and error.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(AddingMoney.class);
    }

    /**
     * Public default constructor.
     */
    public AddingMoney() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response)
            throws IOException {

        final AbstractDao dao = new UserDao();
        final String money = request.getParameter("money");
        final User user = (User) request.getSession().getAttribute("user");
        final String debugString;

        try {

            user.setCash(user.getCash() + Double.parseDouble(money));
            request.getSession().setAttribute("user", user);
            ((UserDao) dao).updateUserCash(user.getEmail(), user.getCash());
            request.setAttribute("message", "Cash replenished successfully.");
            response.sendRedirect("pharmacy");

            SendingEmail.send(user.getEmail(), "Cash",
                    money + " Br credited to your account.");

            debugString = " Increase user " + user + " money by " + money;
            LOGGER.log(Level.DEBUG, debugString);

        } catch (ApplicationException e) {

            e.printStackTrace();
            LOGGER.log(Level.ERROR, e.getMessage());

        }

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
