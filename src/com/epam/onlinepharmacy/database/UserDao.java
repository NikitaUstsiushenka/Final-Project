package com.epam.onlinepharmacy.database;

import com.epam.onlinepharmacy.database.dao.AbstractUserDao;
import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.UserFactory;
import com.epam.onlinepharmacy.main.AbstractSQLQueries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements methods for work with table 'user'.
 *
 * @author Nikita
 * @version 1.0
 * @since 15.09.2018
 */
public final class UserDao extends AbstractUserDao {

    /**
     * Logger for errors and debug.
     */
    private static final Logger LOGGER;

    /**
     * Public default constructor.
     */
    public UserDao() {

        super();

    }

    static {
        LOGGER = LogManager.getLogger(UserDao.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> selectAll() throws ApplicationException {

        final List<User> users = new ArrayList<>();
        final ResultSet resultSet;
        final String debugString = getClass().getName()
                + ": All objects selected from table 'user'.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.SELECT_ALL_USERS);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(((UserFactory) super.getFactory())
                        .createEntity(resultSet));
            }

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        LOGGER.log(Level.DEBUG, debugString);

        return users;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final User user) throws ApplicationException {

        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.INSERT_USER);

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getUserLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, "client");
            statement.setString(5, user.getPassword());
            statement.setBigDecimal(6, new BigDecimal(user.getCash()));
            statement.setNull(7, Types.NULL);

            if (statement.executeUpdate() != 0) {
                debugString = getClass().getName() + ": Object "
                        + user + " inserted in table 'user'.";
            } else {
                debugString = getClass().getName() + ": Object "
                        + user + " didn't insert in table 'user'";
            }

            LOGGER.log(Level.DEBUG, debugString);

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

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
    public boolean isExistUser(final String email,
                               final String password)
            throws ApplicationException {

        final ResultSet resultSet;
        final String debugString;

        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.USER_EXIST);
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                result = true;
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        if (result) {
            debugString = "User with email " + email + " and password "
                    + password + " exists.";
        } else {
            debugString = "User with email " + email
                    + " and password " + password + " doesn't exist.";
        }

        LOGGER.log(Level.DEBUG, debugString);

        return result;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExistUser(final String email)
            throws ApplicationException {

        final ResultSet resultSet;
        final String debugString;

        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.USER_EXIST_BY_EMAIL);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                result = true;
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        if (result) {
            debugString = "User with email " + email + " and password "
                    + " exists.";
        } else {
            debugString = "User with email " + email
                    + " and password " + " doesn't exist.";
        }

        LOGGER.log(Level.DEBUG, debugString);

        return result;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User selectUserByEmail(final String email)
            throws ApplicationException {

        final ResultSet resultSet;
        final User user;
        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.SELECT_USER_BY_EMAIL);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = ((UserFactory) super.getFactory())
                        .createEntity(resultSet);
            } else {
                user = new User();
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        debugString = "User selected by email.";
        LOGGER.log(Level.DEBUG, debugString);

        return user;

    }

    /**
     * {@inheritDoc}
     */
    public void updateUserCash(final String email, final String money)
            throws ApplicationException {

        final User user = this.selectUserByEmail(email);
        final double oldCash = user.getCash();
        final double newCash = Double.parseDouble(money) + oldCash;
        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQueries.UPDATE_USER_CASH);
            statement.setBigDecimal(1, new BigDecimal(newCash));
            statement.setString(2, email);

            if (statement.executeUpdate() != 0) {
                debugString = "User " + user + " cash updated.";
            } else {
                debugString = "User " + user + " didn't update.";
            }

            LOGGER.log(Level.DEBUG, debugString);

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

    }

}
