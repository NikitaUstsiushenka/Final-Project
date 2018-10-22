package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.database.DrugDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.main.UserRole;
import com.epam.onlinepharmacy.sorting.SortDrugsByName;
import com.epam.onlinepharmacy.sorting.SortDrugsByPrice;
import com.epam.onlinepharmacy.sorting.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class implements method for sorting drugs.
 *
 * @author Nikita
 * @version 1.0
 * @since 20.10.2018
 */
public final class SortingDrugs implements Action {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(SortingDrugs.class);
    }

    /**
     * Public default constructor.
     */
    public SortingDrugs() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response,
                        final String url, final String parameter)
            throws ServletException, IOException {

        final AbstractDao dao = new DrugDao();
        final Specification specification;

        @SuppressWarnings("unchecked")
        List<Drug> drugs = (List<Drug>) request.getSession()
                .getAttribute("drugs");

        if (drugs == null) {

            try {
                drugs = ((DrugDao) dao).selectAll();
            } catch (ApplicationException e) {

                LOGGER.log(Level.DEBUG, e.getMessage());
                e.printStackTrace();

            }

        }

        switch (parameter) {

            case "name":
                specification = new SortDrugsByName();
                specification.execute(drugs);
                break;
            case "price":
                specification = new SortDrugsByPrice();
                specification.execute(drugs);
                break;
            default:
                break;

        }

        request.getSession().removeAttribute("drugs");
        request.getSession().setAttribute("drugs", drugs);
        request.getRequestDispatcher(url).forward(request, response);

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
            throws ApplicationException {

        final String errorString
                = " This method can't be call from this class.";

        throw new ApplicationException(errorString);

    }

}
