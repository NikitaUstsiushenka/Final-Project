package com.epam.onlinepharmacy.main;

/**
 * This abstract class stores final values for this application.
 *
 * @author Nikita
 * @version 1.0
 * @since 11.09.2018
 */
public abstract class AbstractProgramConstants {

    /**
     * Value of the error when attribute is null.
     */
    public static final String ATTRIBUTE_ERROR;

    /**
     * Value of the error when user role is incorrect.
     */
    public static final String ROLE_IS_INCORRECT;

    /**
     * Value of the error when drug type is incorrect.
     */
    public static final String TYPE_IS_INCORRECT;

    /**
     * Value of the debug string when user is created.
     */
    public static final String USER_CREATED;

    /**
     * Value of the debug string when drug is created.
     */
    public static final String DRUG_CREATED;

    /**
     * Value of the debug string when all drugs selected.
     */
    public static final String ALL_DRUGS_SELECTED;

    /**
     * Value of the debug string when all drugs selected by name.
     */
    public static final String DRUGS_SELECTED_BY_NAME;

    /**
     * Value of the debug string when selected drugs and analogs by name.
     */
    public static final String DRUGS_SELECTED_WITH_ANALOGS;

    /**
     * Value of the debug string when drugs sorted by name.
     */
    public static final String DRUGS_SORTED_BY_NAME;

    /**
     * Value of the debug string when drugs sorted by price.
     */
    public static final String DRUGS_SORTED_BY_PRICE;

    /**
     * Value of the debug string when order item is created.
     */
    public static final String ORDER_ITEM_CREATED;

    static {

        ATTRIBUTE_ERROR = "Attribute of method is null.";
        ROLE_IS_INCORRECT = "User role is incorrect.";
        TYPE_IS_INCORRECT = "Drug type is incorrect.";
        USER_CREATED = "User is created.";
        DRUG_CREATED = "Drug is created.";
        ALL_DRUGS_SELECTED = "All drugs selected.";
        DRUGS_SELECTED_BY_NAME = "All drugs selected by name.";
        DRUGS_SELECTED_WITH_ANALOGS
                = "All drugs and analogs selected by name.";
        DRUGS_SORTED_BY_NAME = "Drugs sorted by name.";
        DRUGS_SORTED_BY_PRICE = "Drugs sorted by price.";
        ORDER_ITEM_CREATED = "Order item is created.";

    }

    /**
     * Protected default constructor.
     */
    protected AbstractProgramConstants() {

    }

}
