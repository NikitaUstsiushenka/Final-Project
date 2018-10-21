package com.epam.onlinepharmacy.database;

import com.epam.onlinepharmacy.database.dao.AbstractDrugDao;
import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.DrugFactory;
import com.epam.onlinepharmacy.main.AbstractProgramConstant;
import com.epam.onlinepharmacy.main.AbstractSQLQuery;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements methods for work with table 'drug'.
 *
 * @author Nikita
 * @version 1.0
 * @since 15.09.2018
 */
public final class DrugDao extends AbstractDrugDao {

    /**
     * Logger for debug and errors.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(DrugDao.class);
    }

    /**
     * Public default constructor.
     */
    public DrugDao() {

        super();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Drug> selectAll() throws ApplicationException {

        final List<Drug> drugs = new ArrayList<>();
        final ResultSet resultSet;
        final String debugString = " All drugs selected from table `drug`.";

        Connection connection = null;
        Statement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    AbstractSQLQuery.SELECT_ALL_DRUGS);

            while (resultSet.next()) {
                drugs.add(((DrugFactory) super.getFactory())
                        .createEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        LOGGER.log(Level.DEBUG, debugString);

        return drugs;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final Drug drug) throws ApplicationException {

        final String errorString = " Drug type is incorrect.";
        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.INSERT_DRUG);

            statement.setString(AbstractProgramConstant.NUMBER_1,
                    drug.getSubstance().getName());
            statement.setString(AbstractProgramConstant.NUMBER_2,
                    drug.getDrugName());
            statement.setString(AbstractProgramConstant.NUMBER_3,
                    drug.getCompany());
            statement.setString(AbstractProgramConstant.NUMBER_4,
                    drug.getCompanyCountry());
            statement.setBigDecimal(AbstractProgramConstant.NUMBER_5,
                    new BigDecimal(drug.getPrice()));
            statement.setInt(AbstractProgramConstant.NUMBER_6,
                    drug.getDrugsCount());
            statement.setBigDecimal(AbstractProgramConstant.NUMBER_7,
                    new BigDecimal(drug.getDosage()));

            switch (drug.getType()) {

                case PILL:
                    statement.setString(AbstractProgramConstant.NUMBER_8,
                            "pill");
                    break;
                case CAPSULE:
                    statement.setString(AbstractProgramConstant.NUMBER_8,
                            "capsule");
                    break;
                case SOLUTION:
                    statement.setString(AbstractProgramConstant.NUMBER_8,
                            "solution");
                    break;
                case OINTMENT:
                    statement.setString(AbstractProgramConstant.NUMBER_8,
                            "ointment");
                    break;
                case DROPS:
                    statement.setString(AbstractProgramConstant.NUMBER_8,
                            "drops");
                    break;
                case SYRUP:
                    statement.setString(AbstractProgramConstant.NUMBER_8,
                            "syrup");
                    break;
                default:
                    throw new ApplicationException(errorString);

            }

            if (drug.isRequiredRecipe()) {
                statement.setInt(AbstractProgramConstant.NUMBER_9, 1);
            } else {
                statement.setInt(AbstractProgramConstant.NUMBER_9, 0);
            }

            if (statement.executeUpdate() != 0) {

                debugString = " Object " + drug + " inserted in table 'drug'.";
                LOGGER.log(Level.DEBUG, debugString);

            } else {

                debugString = " Object " + drug
                        + "didn't insert in table `drug`.";
                LOGGER.log(Level.DEBUG, debugString);

            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Drug> selectDrug(final String name)
            throws ApplicationException {

        final List<Drug> drugs = new ArrayList<>();
        final ResultSet resultSet;
        final String debugString = " All drugs selected by name.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.SELECT_DRUGS_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                drugs.add(((DrugFactory) super.getFactory())
                        .createEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        LOGGER.log(Level.DEBUG, debugString);

        return drugs;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Drug> selectDrugWithAnalogs(final String name)
            throws ApplicationException {

        final List<Drug> drugs = new ArrayList<>();
        final ResultSet resultSet;
        final String debugString = " All drugs and analogs selected by name.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.SELECT_DRUGS_WITH_ANALOGS);
            statement.setString(1, name);
            statement.setString(2, name);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                drugs.add(((DrugFactory) super.getFactory())
                        .createEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        LOGGER.log(Level.DEBUG, debugString);

        return drugs;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drug selectDrug(final int id) throws ApplicationException {

        final ResultSet resultSet;
        final Drug drug;
        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.SELECT_DRUG_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                drug = (Drug) super.getFactory().createEntity(resultSet);
                debugString = " Drug " + drug + " selected by id.";
                LOGGER.log(Level.DEBUG, debugString);

            } else {

                drug = new Drug();
                debugString = " Drug with id " + id + " doesn't exist.";
                LOGGER.log(Level.DEBUG, debugString);

            }


        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        return drug;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDrug(final int drugId, final int count)
            throws ApplicationException {

        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.UPDATE_DRUG_COUNT);

            statement.setInt(1, count);
            statement.setInt(2, drugId);

            if (statement.executeUpdate() != 0) {
                debugString = " Drug with id " + drugId + " was updated.";
            } else {
                debugString = " Drug with id " + drugId + "didn't update.";
            }

            LOGGER.log(Level.DEBUG, debugString);

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDrug(final String name, final int dosage,
                           final int count)
            throws ApplicationException {

        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.UPDATE_DRUG_COUNT_BY_DOSAGE);

            statement.setInt(1, count);
            statement.setString(2, name);
            statement.setInt(3, dosage);

            if (statement.executeUpdate() != 0) {
                debugString = " Drug with name " + name
                        + " and dosage " + dosage + " was updated.";
            } else {
                debugString = " Drug with name " + name
                        + " and dosage " + dosage + "didn't update.";
            }

            LOGGER.log(Level.DEBUG, debugString);

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drug selectDrug(final String name, final int dosage)
            throws ApplicationException {

        final ResultSet resultSet;
        final Drug drug;
        final String debugString = " All drugs selected by name and dosage.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.SELECT_DRUG_NAME_DOSAGE);
            statement.setString(1, name);
            statement.setInt(2, dosage);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                drug = ((DrugFactory) super.getFactory())
                        .createEntity(resultSet);
            } else {
                drug = new Drug();
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        LOGGER.log(Level.DEBUG, debugString);

        return drug;

    }

}
