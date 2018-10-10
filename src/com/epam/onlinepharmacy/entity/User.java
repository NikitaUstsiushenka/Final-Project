package com.epam.onlinepharmacy.entity;

import com.epam.onlinepharmacy.main.AbstractProgramConstants;
import com.epam.onlinepharmacy.main.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class stores information about the user(name, login, password etc.).
 *
 * @author Nikita
 * @version 1.0
 * @since 12.09.2018
 */
public final class User extends AbstractEntity {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    /**
     * Value of the user name.
     */
    private String userName;

    /**
     * Value of the user last name.
     */
    private String userLastName;

    /**
     * Value of the user email.
     */
    private String email;

    /**
     * Value of the user role.
     */
    private UserRole role;

    /**
     * Value of the hash code of password.
     */
    private String password;

    /**
     * Value of the doctor speciality.
     */
    private String speciality;

    /**
     * Value of the client cash.
     */
    private double cash;

    static {
        LOGGER = LogManager.getLogger(User.class);
    }

    /**
     * Public default constructor.
     */
    public User() {

        super();
        this.role = UserRole.CLIENT;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return getClass().getName() + "@entity:" + super.toString()
                + "@userName:" + this.userName + "@userLastName:"
                + this.userLastName + "@email:" + this.email + "@role:"
                + this.role + "@password:"
                + this.password + "@speciality:" + this.speciality
                + "@cash:" + this.cash;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return super.hashCode() + (this.userName != null
                ? this.userName.hashCode() : 0) + (this.userLastName != null
                ? this.userLastName.hashCode() : 0) + (this.email != null
                ? this.email.hashCode() : 0) + (this.role != null
                ? this.role.hashCode() : 0) + (this.password != null
                ? this.password.hashCode() : 0) + (int) this.cash
                + (this.speciality != null ? this.speciality.hashCode() : 0);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {

        boolean result;
        final User user;

        if (object != null) {

            user = (User) object;

            if (this == object) {
                result = true;
            } else if (this.getClass() != object.getClass()) {
                result = false;
            } else {

                result = (getId().equals(user.getId())
                        && (this.userName != null && this.userName.equals(
                        user.getUserName())) && (this.userLastName
                        != null && this.userLastName.equals(
                        user.getUserLastName())) && (this.email
                        != null && this.email.equals(user.getEmail()))
                        && this.role == user.getRole()
                        && (this.password != null && this.password.equals(
                        user.getPassword())));

                if ((this.role == UserRole.CLIENT && this.cash
                        == user.getCash()) || (this.role == UserRole.DOCTOR
                        && (this.speciality != null && this.speciality
                        .equals(user.getSpeciality())))) {
                    result = true;
                }

            }

        } else {
            result = false;
        }

        return result;

    }

    /**
     * This method return value of the user name.
     *
     * @return value of the user name
     */
    public String getUserName() {

        final String resultString;

        if (this.userName != null) {
            resultString = this.userName;
        } else {
            resultString = "";
        }

        return resultString;

    }

    /**
     * This method set new value of the user name.
     *
     * @param newUserName new value of the user name
     */
    public void setUserName(final String newUserName) {

        final String debugString = getClass().getName()
                + ": Attribute is null in method setUserName(String).";

        if (newUserName != null) {
            this.userName = newUserName;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return value of the user last name.
     *
     * @return value of the user last name
     */
    public String getUserLastName() {

        final String result;

        if (this.userLastName != null) {
            result = this.userLastName;
        } else {
            result = "";
        }

        return result;

    }

    /**
     * This method set new value of the user last name.
     *
     * @param newUserLastName new value of the user
     */
    public void setUserLastName(final String newUserLastName) {

        final String debugString = getClass().getName()
                + ": Attribute is null in method setUserLastName(String).";

        if (newUserLastName != null) {
            this.userLastName = newUserLastName;
        } else {
            LOGGER.log(Level.DEBUG, AbstractProgramConstants.ATTRIBUTE_ERROR);
        }

    }

    /**
     * This method return value of the user email.
     *
     * @return value of the user email
     */
    public String getEmail() {

        final String result;

        if (this.email != null) {
            result = this.email;
        } else {
            result = "";
        }

        return result;

    }

    /**
     * This method set new value of the email.
     *
     * @param newEmail new value of the user email
     */
    public void setEmail(final String newEmail) {

        final String debugString = getClass().getName()
                + ": Attribute is null in method setEmail(String).";

        if (newEmail != null) {
            this.email = newEmail;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return value of the user role.
     *
     * @return value of the user role
     */
    public UserRole getRole() {

        return this.role;

    }

    /**
     * This method set new value of the user role.
     *
     * @param newRole new value of the user role
     */
    public void setRole(final UserRole newRole) {

        final String debugString = getClass().getName()
                + ": Attribute is null in method setRole(UserRole).";

        if (newRole != null) {
            this.role = newRole;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return value of the hash code of password.
     *
     * @return value of the hash code of password
     */
    public String getPassword() {

        final String result;

        if (this.password != null) {
            result = this.password;
        } else {
            result = "";
        }

        return result;

    }

    /**
     * This method set new value of the hash code of password.
     *
     * @param newPassword new value of the hash code of password
     */
    public void setPassword(final String newPassword) {

        this.password = newPassword;

    }

    /**
     * This method return value of the doctor speciality.
     *
     * @return value of the doctor speciality
     */
    public String getSpeciality() {

        final String result;

        if (this.speciality != null) {
            result = this.speciality;
        } else {
            result = "";
        }

        return result;

    }

    /**
     * This method set new value of the doctor speciality.
     *
     * @param newSpeciality new value of the doctor speciality
     */
    public void setSpeciality(final String newSpeciality) {

        final String debugString = getClass().getName()
                + ": Attribute is null in method setSpeciality(String).";

        if (newSpeciality != null) {
            this.speciality = newSpeciality;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return value of the client cash.
     *
     * @return value of the client cash
     */
    public double getCash() {

        return this.cash;

    }

    /**
     * This method set new value of the client cash.
     *
     * @param newCash new value of the client cash
     */
    public void setCash(final double newCash) {

        this.cash = newCash;

    }

}
