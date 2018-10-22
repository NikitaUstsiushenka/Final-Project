package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.database.DrugDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
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
public final class ShowingDrug implements Action {

    /**
     * Logger for error.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(ShowingDrug.class);
    }

    /**
     * Public default constructor.
     */
    public ShowingDrug() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response)
            throws ServletException, IOException {

        final AbstractDao dao = new DrugDao();

        try {

            request.getSession().setAttribute("drugs",
                    ((DrugDao) dao).selectAll());
            request.getRequestDispatcher(
                    AbstractProgramConstant.URL_PHARM_DRUGS_JSP)
                    .forward(request, response);

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
