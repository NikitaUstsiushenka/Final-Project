package com.epam.onlinepharmacy.database.dao;

import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.DrugFactory;

import java.util.List;

/**
 * This abstract class stores methods for work with table 'drug'.
 *
 * @author Nikita
 * @version 1.0
 * @since 18.09.2018
 */
public abstract class AbstractDrugDao extends AbstractDao<Drug> {

    /**
     * Protected default constructor.
     */
    protected AbstractDrugDao() {

        super(new DrugFactory());

    }

    /**
     * This method selects drugs from table 'drug' by drug name.
     *
     * @param name value of the drug name
     * @return list of the objects Drug
     * @throws ApplicationException
     */
    public abstract List<Drug> selectByName(String name)
            throws ApplicationException;

    /**
     * This method selects drugs from table 'drug' by drug name with analogs.
     *
     * @param name value of the drug name
     * @return list of the objects Drug
     * @throws ApplicationException
     */
    public abstract List<Drug> selectByNameWithAnalogs(String name)
            throws ApplicationException;

    /**
     * This method selects drug by id.
     *
     * @param id value of the drug id
     * @return value of the object Drug
     * @throws ApplicationException
     */
    public abstract Drug selectById(int id)
            throws ApplicationException;

    /**
     * This method updates drug count.
     *
     * @param drugId value of the drug id
     * @param count  new value of the drug count
     * @throws ApplicationException
     */
    public abstract void updateDrugCount(final int drugId, final int count)
            throws ApplicationException;

    /**
     * This method selects drug by name and dosage.
     *
     * @param name   value of the drug name
     * @param dosage value of the drug dosage
     * @throws ApplicationException
     */
    public abstract Drug selectByNameDosage(final String name,
                                            final int dosage)
            throws ApplicationException;

}
