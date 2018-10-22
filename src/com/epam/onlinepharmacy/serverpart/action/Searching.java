package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.database.DrugDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
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
 * This class implements "Action".
 *
 * @author Nikita
 * @version 1.0
 * @since 20.10.2018
 */
public final class Searching implements Action {

    /**
     * Logger for debug and error.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(Searching.class);
    }

    /**
     * Public default constructor.
     */
    public Searching() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response,
                        final String url)
            throws ServletException, IOException {

        final AbstractDao dao = new DrugDao();
        List<Drug> drugs = new ArrayList<>();
        final String debugString;

        try {

            if (request.getParameter("checkbox") != null) {

                drugs = ((DrugDao) dao).selectDrugWithAnalogs(
                        request.getParameter("drug_name"));
                debugString = " Search by name with analogs is completed.";

            } else {

                drugs = ((DrugDao) dao).selectDrug(
                        request.getParameter("drug_name"));
                debugString = " Search by name is completed.";

            }

            LOGGER.log(Level.DEBUG, debugString);

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

        request.getSession().setAttribute("drugs", drugs);
        request.getRequestDispatcher(url).forward(request, response);

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
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
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
