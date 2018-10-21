package com.epam.onlinepharmacy.database.dao;

import com.epam.onlinepharmacy.entity.Substance;
import com.epam.onlinepharmacy.exceptions.ApplicationException;

/**
 * This abstract class stores methods for work with table 'substance'.
 *
 * @author Nikita
 * @version 1.0
 * @since 30.09.2018
 */
public abstract class AbstractSubstanceDao extends AbstractDao<Substance> {

    /**
     * Protected default constructor.
     */
    protected AbstractSubstanceDao() {

        super();

    }

    /**
     * This method tests the existence of a substance.
     *
     * @param name value of the substance name
     * @return boolean value
     * @throws ApplicationException throw SQLException
     */
    public abstract boolean isExistSubstance(String name)
            throws ApplicationException;

}
