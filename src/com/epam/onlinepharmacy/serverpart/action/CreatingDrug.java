package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.database.DrugDao;
import com.epam.onlinepharmacy.database.SubstanceDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.DrugFactory;
import com.epam.onlinepharmacy.factory.EntityFactory;
import com.epam.onlinepharmacy.main.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public final class CreatingDrug implements Action {

    /**
     * Logger for error.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(CreatingDrug.class);
    }

    /**
     * Public default constructor.
     */
    public CreatingDrug() {
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

        final EntityFactory factory = new DrugFactory();
        final Drug drug = ((DrugFactory) factory).createEntity(request);

        AbstractDao dao;
        List<Drug> drugs = new ArrayList<>();

        dao = new SubstanceDao();

        try {

            if (!((SubstanceDao) dao).isExistSubstance(
                    drug.getSubstance().getName())) {
                ((SubstanceDao) dao).insert(drug.getSubstance());
            }

            dao = new DrugDao();
            ((DrugDao) dao).insert(drug);
            drugs = ((DrugDao) dao).selectAll();

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

        request.getSession().removeAttribute("drugs");
        request.getSession().setAttribute("drugs", drugs);
        response.sendRedirect("pharmacy");

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
