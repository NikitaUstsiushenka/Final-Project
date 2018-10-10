package com.epam.onlinepharmacy.database;

import com.epam.onlinepharmacy.database.dao.AbstractSubstanceDao;
import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.entity.Substance;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.main.AbstractSQLQueries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements methods for work with table 'substance'.
 *
 * @author Nikita
 * @version 1.0
 * @since 30.09.2018
 */
public final class SubstanceDao extends AbstractSubstanceDao {

    /**
     * Logger for debug and errors.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(SubstanceDao.class);
    }

    /**
     * Public default constructor.
     */
    public SubstanceDao() {

        super();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Substance> selectAll() throws ApplicationException {

        final List<Substance> substances = new ArrayList<>();
        final ResultSet resultSet;
        final String debugString = getClass().getName()
                + ": All substances selected from table 'substance'.";

        Connection connection = null;
        PreparedStatement statement = null;
        Substance substance;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.SELECT_ALL_SUBSTANCES);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                substance = new Substance();
                substance.setId(resultSet.getInt("id"));
                substance.setName(resultSet.getString("name"));

                substances.add(substance);

            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        LOGGER.log(Level.DEBUG, debugString);

        return substances;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final Substance substance) throws ApplicationException {

        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.INSERT_SUBSTANCE);
            statement.setString(1, substance.getName());

            if (statement.executeUpdate() != 0) {

                debugString = getClass().getName() + ": Object " + substance
                        + " inserted in table 'substance'.";
                LOGGER.log(Level.DEBUG, debugString);

            } else {

                debugString = getClass().getName() + ": Object " + substance
                        + " didn't insert in table 'substance'.";
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
    public boolean isExistSubstance(final String name) throws ApplicationException {

        final boolean result;
        final ResultSet resultSet;
        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.SELECT_SUBSTANCE_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            result = resultSet.next();

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        if (result) {
            debugString = getClass().getName()
                    + ": Substance with name " + name + " already exist.";
        } else {
            debugString = getClass().getName() + ": Substance with name "
                    + name + " else doesn't exist.";
        }

        LOGGER.log(Level.DEBUG, debugString);

        return result;

    }

}
