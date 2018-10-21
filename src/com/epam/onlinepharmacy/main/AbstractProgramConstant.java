package com.epam.onlinepharmacy.main;

/**
 * This abstract class stores final values for this application.
 *
 * @author Nikita
 * @version 1.0
 * @since 11.09.2018
 */
public abstract class AbstractProgramConstant {

    /**
     * Value of the number 1.
     */
    public static final int NUMBER_1 = 1;

    /**
     * Value of the number 2.
     */
    public static final int NUMBER_2 = 2;

    /**
     * Value of the number 3.
     */
    public static final int NUMBER_3 = 3;

    /**
     * Value of the number 4.
     */
    public static final int NUMBER_4 = 4;

    /**
     * Value of the number 5.
     */
    public static final int NUMBER_5 = 5;

    /**
     * Value of the number 6.
     */
    public static final int NUMBER_6 = 6;

    /**
     * Value of the number 7.
     */
    public static final int NUMBER_7 = 7;

    /**
     * Value of the number 8.
     */
    public static final int NUMBER_8 = 8;

    /**
     * Value of the number 9.
     */
    public static final int NUMBER_9 = 9;

    /**
     * Value of the number 1000.
     */
    public static final int NUMBER_1000 = 1_000;

    /**
     * Value of the number 9000.
     */
    public static final int NUMBER_9000 = 9_000;

    /**
     * Value of the url to the "client.jsp".
     */
    public static final String URL_CLIENT_JSP;

    /**
     * Value of the url to the "admin.jsp".
     */
    public static final String URL_ADMIN_JSP;

    /**
     * Value of the url to the "doctor.jsp".
     */
    public static final String URL_DOCTOR_JSP;

    /**
     * Value of the url to the pharm "drugs.jsp".
     */
    public static final String URL_PHARM_DRUGS_JSP;

    /**
     * Value of the url to the client "cart_products.jsp".
     */
    public static final String URL_CLIENT_CART;

    /**
     * Value of the url to the admin "users.jsp".
     */
    public static final String URL_ADMIN_USERS;

    /**
     * Value of the url to the admin "orders.jsp".
     */
    public static final String URL_ADMIN_ORDERS;

    /**
     * Value of the url to the client "code.jsp".
     */
    public static final String URL_CLIENT_CODE;

    /**
     * Value of the url to the "pharm.jsp".
     */
    public static final String URL_PHARM_JSP;

    /**
     * Value of the e-mail address from which the message will be sent.
     */
    public static final String EMAIL;

    /**
     * Value of the password of e-mail.
     */
    public static final String EMAIL_PASSWORD;

    static {

        URL_CLIENT_JSP = "/WEB-INF/jsp/client/client.jsp";
        URL_ADMIN_JSP = "/WEB-INF/jsp/admin/admin.jsp";
        URL_DOCTOR_JSP = "/WEB-INF/jsp/doctor/doctor.jsp";
        URL_PHARM_DRUGS_JSP = "/WEB-INF/jsp/pharm/drugs.jsp";
        URL_CLIENT_CART = "/WEB-INF/jsp/client/cart_products.jsp";
        URL_ADMIN_USERS = "/WEB-INF/jsp/admin/users.jsp";
        URL_ADMIN_ORDERS = "/WEB-INF/jsp/admin/orders.jsp";
        URL_CLIENT_CODE = "/WEB-INF/jsp/client/code.jsp";
        URL_PHARM_JSP = "/WEB-INF/jsp/pharm/pharm.jsp";
        EMAIL = "nikitaustyushenko@mail.ru";
        EMAIL_PASSWORD = "NikOsborn09061999";

    }

    /**
     * Protected default constructor.
     */
    protected AbstractProgramConstant() {

    }

}
