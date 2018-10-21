package com.epam.onlinepharmacy.main;

/**
 * This class stores database constants.
 *
 * @author Nikita
 * @version 1.0
 * @since 14.09.2018
 */
public abstract class AbstractDBConstant {

    /**
     * Value of the driver class.
     */
    public static final String DRIVER_CLASS;

    /**
     * Value of the database url.
     */
    public static final String DB_URL;

    /**
     * Value of the database user.
     */
    public static final String DB_USER;

    /**
     * Value of the user password.
     */
    public static final String DB_PASSWORD;

    static {

        DRIVER_CLASS = "com.mysql.jdbc.Driver";
        DB_URL = "jdbc:mysql://localhost:3306/online-pharmacy";
        DB_USER = "root";
        DB_PASSWORD = "Nikita09061999";

    }

    /**
     * Protected default constructor.
     */
    protected AbstractDBConstant() {

    }

}
