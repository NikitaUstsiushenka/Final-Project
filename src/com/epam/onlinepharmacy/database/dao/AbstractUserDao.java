package com.epam.onlinepharmacy.database.dao;

import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.UserFactory;
import com.epam.onlinepharmacy.main.UserRole;

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
     * @throws ApplicationException
     */
    public abstract boolean isExistUser(String email, String password)
            throws ApplicationException;

    /**
     * This method tests the existence of the user by email.
     *
     * @param email value of the user email
     * @return boolean value
     * @throws ApplicationException
     */
    public abstract boolean isExistUser(String email)
            throws ApplicationException;

    /**
     * This method selects user by email.
     *
     * @param email value of the user email
     * @return value of the object User
     */
    public abstract User selectUserByEmail(String email)
            throws ApplicationException;

    /**
     * This method update user cash in table 'user'.
     *
     * @param email value of the user email
     * @param money value of the money
     * @throws ApplicationException
     */
    public abstract void updateUserCash(final String email, final String money)
            throws ApplicationException;

}
