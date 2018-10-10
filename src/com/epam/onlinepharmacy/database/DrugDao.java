package com.epam.onlinepharmacy.database;

import com.epam.onlinepharmacy.database.dao.AbstractDrugDao;
import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.DrugFactory;
import com.epam.onlinepharmacy.main.AbstractProgramConstants;
import com.epam.onlinepharmacy.main.AbstractSQLQueries;
import javafx.application.Application;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
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
        final String debugString = getClass().getName()
                + ": All drugs selected from table `drug`.";

        Connection connection = null;
        Statement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    AbstractSQLQueries.SELECT_ALL_DRUGS);

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

        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.INSERT_DRUG);
            statement.setString(1, drug.getSubstance().getName());
            statement.setString(2, drug.getDrugName());
            statement.setString(3, drug.getCompany());
            statement.setString(4, drug.getCompanyCountry());
            statement.setBigDecimal(5, new BigDecimal(drug.getPrice()));
            statement.setInt(6, drug.getDrugsCount());
            statement.setBigDecimal(7, new BigDecimal(drug.getDosage()));

            switch (drug.getType()) {

                case PILL:
                    statement.setString(8, "pill");
                    break;
                case CAPSULE:
                    statement.setString(8, "capsule");
                    break;
                case SOLUTION:
                    statement.setString(8, "solution");
                    break;
                case OINTMENT:
                    statement.setString(8, "ointment");
                    break;
                case DROPS:
                    statement.setString(8, "drops");
                    break;
                case SYRUP:
                    statement.setString(8, "syrup");
                    break;
                default:
                    break;

            }

            if (drug.isRequiredRecipe()) {
                statement.setInt(9, 1);
            } else {
                statement.setInt(9, 0);
            }

            if (statement.executeUpdate() != 0) {

                debugString = getClass().getName()
                        + ": Object " + drug + " inserted in table 'drug'.";
                LOGGER.log(Level.DEBUG, debugString);

            } else {

                debugString = getClass().getName() + ": Object " + drug
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
    public List<Drug> selectByName(final String name)
            throws ApplicationException {

        final List<Drug> drugs = new ArrayList<>();
        final ResultSet resultSet;
        final String debugString = getClass().getName()
                + ": All drugs selected by name.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.SELECT_DRUGS_BY_NAME);
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
    public List<Drug> selectByNameWithAnalogs(final String name)
            throws ApplicationException {

        final List<Drug> drugs = new ArrayList<>();
        final ResultSet resultSet;
        final String debugString = getClass().getName()
                + ": All drugs and analogs selected by name.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.SELECT_DRUGS_WITH_ANALOGS);
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
    public Drug selectById(final int id) throws ApplicationException {

        final ResultSet resultSet;
        final Drug drug;
        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.SELECT_DRUG_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                drug = (Drug) super.getFactory().createEntity(resultSet);
                debugString = getClass().getName()
                        + ": Drug " + drug + " selected by id.";
                LOGGER.log(Level.DEBUG, debugString);

            } else {

                drug = new Drug();
                debugString = getClass().getName()
                        + ": Drug with id " + id + " doesn't exist.";
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
    public void updateDrugCount(final int drugId, final int count)
            throws ApplicationException {

        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.UPDATE_DRUG_COUNT);
            statement.setInt(1, count);
            statement.setInt(2, drugId);

            if (statement.executeUpdate() != 0) {

                debugString = getClass().getName()
                        + ": Drug with id " + drugId + " was updated.";
                LOGGER.log(Level.DEBUG, debugString);

            } else {

                debugString = getClass().getName()
                        + ": Drug with id " + drugId + "didn't update.";
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
    public Drug selectByNameDosage(final String name, final int dosage)
            throws ApplicationException {

        final ResultSet resultSet;
        final Drug drug;
        final String debugString = getClass().getName()
                + ": All drugs selected by name and dosage.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.SELECT_DRUG_NAME_DOSAGE);
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
