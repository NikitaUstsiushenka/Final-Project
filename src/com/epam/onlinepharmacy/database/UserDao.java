package com.epam.onlinepharmacy.database;

import com.epam.onlinepharmacy.database.dao.AbstractUserDao;
import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.UserFactory;
import com.epam.onlinepharmacy.main.AbstractProgramConstant;
import com.epam.onlinepharmacy.main.AbstractSQLQuery;
import com.epam.onlinepharmacy.main.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
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
        final String debugString = " All objects selected from table 'user'.";

        Connection connection = null;
        Statement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    AbstractSQLQuery.SELECT_ALL_USERS);

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
                    AbstractSQLQuery.INSERT_USER);

            statement.setString(AbstractProgramConstant.NUMBER_1,
                    user.getUserName());
            statement.setString(AbstractProgramConstant.NUMBER_2,
                    user.getUserLastName());
            statement.setString(AbstractProgramConstant.NUMBER_3,
                    user.getEmail());
            statement.setString(AbstractProgramConstant.NUMBER_4,
                    "client");
            statement.setString(AbstractProgramConstant.NUMBER_5,
                    user.getPassword());
            statement.setBigDecimal(AbstractProgramConstant.NUMBER_6,
                    new BigDecimal(user.getCash()));
            statement.setNull(AbstractProgramConstant.NUMBER_7, Types.NULL);

            if (statement.executeUpdate() != 0) {
                debugString = " Object "
                        + user + " inserted in table 'user'.";
            } else {
                debugString = " Object "
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
                    AbstractSQLQuery.USER_EXIST);
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
            debugString = " User with email " + email + " and password "
                    + password + " exists.";
        } else {
            debugString = " User with email " + email
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
                    AbstractSQLQuery.USER_EXIST_BY_EMAIL);
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
            debugString = " User with email " + email + " and password "
                    + " exists.";
        } else {
            debugString = " User with email " + email
                    + " and password " + " doesn't exist.";
        }

        LOGGER.log(Level.DEBUG, debugString);

        return result;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User selectUser(final String email)
            throws ApplicationException {

        final ResultSet resultSet;
        final User user;
        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.SELECT_USER_BY_EMAIL);
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

        debugString = " User selected by email.";
        LOGGER.log(Level.DEBUG, debugString);

        return user;

    }

    /**
     * {@inheritDoc}
     */
    public void updateUserCash(final String email, final double newCash)
            throws ApplicationException {

        final User user = this.selectUser(email);
        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.UPDATE_USER_CASH);
            statement.setBigDecimal(1, new BigDecimal(newCash));
            statement.setString(2, email);

            if (statement.executeUpdate() != 0) {
                debugString = " User " + user + " cash updated.";
            } else {
                debugString = " User " + user + " didn't update.";
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
    public List<User> selectUser(final UserRole role)
            throws ApplicationException {

        final ResultSet resultSet;
        final List<User> clients = new ArrayList<>();
        final String debugString1 = "User role is incorrect.";
        final String debugString2 = "Clients selected from table 'user'.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.SELECT_USERS_BY_ROLE);

            switch (role) {

                case CLIENT:
                    statement.setString(1, "client");
                    break;
                case ADMIN:
                    statement.setString(1, "admin");
                    break;
                case DOCTOR:
                    statement.setString(1, "doctor");
                    break;
                case PHARMACIST:
                    statement.setString(1, "pharm");
                    break;
                default:
                    LOGGER.log(Level.DEBUG, debugString1);
                    break;
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                clients.add(((UserFactory) super.getFactory())
                        .createEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        LOGGER.log(Level.DEBUG, debugString2);

        return clients;

    }

    /**
     * {@inheritDoc}
     */
    public User selectUser(final int userId)
            throws ApplicationException {

        final ResultSet resultSet;
        final User user;
        final String debugString = " User selected from table 'user'.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.SELECT_USER_BY_ID);

            statement.setInt(1, userId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = (User) super.getFactory().createEntity(resultSet);
            } else {
                user = new User();
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        LOGGER.log(Level.DEBUG, debugString);

        return user;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteClientById(final int clientId)
            throws ApplicationException {

        final String debugString;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(
                    AbstractSQLQuery.DELETE_CLIENT_BY_ID);

            statement.setInt(1, clientId);

            if (statement.executeUpdate() != 0) {
                debugString = " User with id " + clientId + " was deleted.";
            } else {
                debugString = " User with id " + clientId + " doesn't delete.";
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
