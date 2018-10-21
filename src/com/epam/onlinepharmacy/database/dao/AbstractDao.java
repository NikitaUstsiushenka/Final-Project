package com.epam.onlinepharmacy.database.dao;

import com.epam.onlinepharmacy.entity.AbstractEntity;
import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.EntityFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * This abstract class stores basic methods for work with database.
 *
 * @param <T> value of the object that extends AbstractEntity class
 * @author Nikita
 * @version 1.0
 * @since 14.09.2018
 */
public abstract class AbstractDao<T extends AbstractEntity> {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    /**
     * Value of the object EntityFactory.
     */
    private EntityFactory factory;

    static {
        LOGGER = LogManager.getLogger(AbstractDao.class);
    }

    /**
     * Private-package default constructor.
     */
    AbstractDao() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * Private-package initialize constructor.
     *
     * @param newFactory new value of the object EntityFactory
     */
    AbstractDao(final EntityFactory newFactory) {

        this.factory = newFactory;

    }

    /**
     * This method return all elements from table.
     *
     * @return list of the objects that extend AbstractEntity class
     * @throws ApplicationException throw SQLException
     */
    public abstract List<T> selectAll() throws ApplicationException;

    /**
     * This method insert element in table.
     *
     * @param entity value of the object that extends AbstractEntity class
     * @throws ApplicationException throw SQLException
     */
    public abstract void insert(T entity) throws ApplicationException;

    /**
     * This method close statement.
     *
     * @param statement value of the object Statement
     */
    protected void close(final Statement statement) {

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * This method close connection with database.
     *
     * @param connection value of the object Connection
     */
    protected void close(final Connection connection) {

        if (connection != null) {
            try {
                ConnectionPool.getInstance().freeConnection(connection);
            } catch (ApplicationException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * This method return value of the object EntityFactory.
     *
     * @return value of the object EntityFactory
     */
    public EntityFactory getFactory() {

        return this.factory;

    }

    /**
     * This method new value of the factory.
     *
     * @param newFactory new value of the factory
     */
    public void setFactory(final EntityFactory newFactory) {

        final String debugString
                = " Attribute is null in method setFactory(EntityFactory).";

        if (newFactory != null) {
            this.factory = newFactory;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

}
