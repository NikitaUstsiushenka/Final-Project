package com.epam.onlinepharmacy.database.dao;

import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.UserFactory;
import com.epam.onlinepharmacy.main.UserRole;

import java.util.List;

/**
 * This abstract class stores classes for work with table 'user'.
 *
 * @author Nikita
 * @version 1.0
 * @since 18.09.2018
 */
public abstract class AbstractUserDao extends AbstractDao<User> {

    /**
     * Protected default constructor.
     */
    protected AbstractUserDao() {

        super(new UserFactory());

    }

    /**
     * This method tests the existence of the user by email and password.
     *
     * @param email    value of the user email
     * @param password value of the user password
     * @return boolean value
     * @throws ApplicationException throw SQLException
     */
    public abstract boolean isExistUser(String email, String password)
            throws ApplicationException;

    /**
     * This method tests the existence of the user by email.
     *
     * @param email value of the user email
     * @return boolean value
     * @throws ApplicationException throw SQLException
     */
    public abstract boolean isExistUser(String email)
            throws ApplicationException;

    /**
     * This method selects user by email.
     *
     * @param email value of the user email
     * @return value of the object User
     * @throws ApplicationException throw SQLException
     */
    public abstract User selectUser(String email)
            throws ApplicationException;

    /**
     * This method selects user by user id.
     *
     * @param userId value of the user id
     * @throws ApplicationException throw SQLException
     */
    public abstract User selectUser(int userId)
            throws ApplicationException;

    /**
     * This method updates user cash in table 'user'.
     *
     * @param email   value of the user email
     * @param newCash value of the new cash
     * @throws ApplicationException throw SQLException
     */
    public abstract void updateUserCash(String email, double newCash)
            throws ApplicationException;

    /**
     * This method selects all users by role.
     *
     * @param role value of the user role
     * @return list of users
     * @throws ApplicationException throw SQLException
     */
    public abstract List<User> selectUser(UserRole role)
            throws ApplicationException;

    /**
     * This method deletes user by id.
     *
     * @param clientId value of the client id
     * @throws ApplicationException throw SQLException
     */
    public abstract void deleteClientById(int clientId)
            throws ApplicationException;

}
