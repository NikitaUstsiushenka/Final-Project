package com.epam.onlinepharmacy.main;

/**
 * This abstract class stores SQL queries.
 *
 * @author Nikita
 * @version 1.0
 * @since 14.09.2018
 */
public abstract class AbstractSQLQueries {

    /**
     * Value of the sql query that selects all users from table 'user'.
     */
    public static final String SELECT_ALL_USERS;

    /**
     * Value of the query that checks user.
     */
    public static final String USER_EXIST;

    /**
     * Value of the query that checks user by email.
     */
    public static final String USER_EXIST_BY_EMAIL;

    /**
     * Value of the sql query that selects all drugs from table 'drug'.
     */
    public static final String SELECT_ALL_DRUGS;

    /**
     * Value of the sql query that inserts user in table 'user'.
     */
    public static final String INSERT_USER;

    /**
     * Value of the sql query that selects drugs from table 'drug' by name.
     */
    public static final String SELECT_DRUGS_BY_NAME;

    /**
     * Value of the sql query that selects drugs and analogs by name.
     */
    public static final String SELECT_DRUGS_WITH_ANALOGS;

    /**
     * Value of the sql query that selects user by email.
     */
    public static final String SELECT_USER_BY_EMAIL;

    /**
     * Value of the sql query that update user cash.
     */
    public static final String UPDATE_USER_CASH;

    /**
     * Value of the sql query that insert drug in table 'drug'.
     */
    public static final String INSERT_DRUG;

    /**
     * Value of the sql query that selects all substances.
     */
    public static final String SELECT_ALL_SUBSTANCES;

    /**
     * Value of the sql query that inserts substance in table 'substance'.
     */
    public static final String INSERT_SUBSTANCE;

    /**
     * Value of the sql query that selects substance by name.
     */
    public static final String SELECT_SUBSTANCE_BY_NAME;

    /**
     * Value of the sql query that selects drug by id.
     */
    public static final String SELECT_DRUG_BY_ID;

    /**
     * Value of the sql query that selects all order items.
     */
    public static final String SELECT_ALL_ORDER_ITEMS;

    /**
     * Value of the sql query that inserts order item.
     */
    public static final String INSERT_ORDER_ITEM;

    /**
     * Value of the sql query that selects all orders.
     */
    public static final String SELECT_ALL_ORDERS;

    /**
     * Value of the sql query that inserts order.
     */
    public static final String INSERT_ORDER;

    /**
     * Value of the sql query that updates drug count.
     */
    public static final String UPDATE_DRUG_COUNT;

    /**
     * Value of the sql query that selects drug by name and dosage.
     */
    public static final String SELECT_DRUG_NAME_DOSAGE;

    /**
     * Value of the sql query that selects order by client id and paid.
     */
    public static final String SELECT_ORDER_ID_PAID;

    static {

        SELECT_ALL_USERS = "SELECT * FROM `user`";
        SELECT_USER_BY_EMAIL = "SELECT * FROM `user` WHERE email = ?";
        USER_EXIST = "SELECT * FROM `user` WHERE `email` = ? " +
                "AND `password` = sha2(?, 256)";
        USER_EXIST_BY_EMAIL = "SELECT * FROM `user` WHERE `email` = ?";
        SELECT_ALL_DRUGS = "SELECT d.`id`, d.`name`, d.`company`, " +
                "d.`country`, s.`name` `substance_name`, d.`price`, " +
                "d.`count`, d.`dosage`, d.`type`, " +
                "d.`is_required_recipe` FROM `drug` d " +
                "INNER JOIN `substance` s ON s.`id` = d.`substance_id`";
        INSERT_USER = "INSERT INTO `user` (`name`, `lastname`, `email`, " +
                "`role`, `password`, `cash`, `speciality`) " +
                "VALUES(?, ?, ?, ?, sha2(?, 256), ?, ?)";
        SELECT_DRUGS_BY_NAME = "SELECT d.`id`, d.`name`, d.`company`," +
                " d.`country`, s.`name` `substance_name`, d.`price`, " +
                "d.`count`, d.`dosage`, d.`type`, d.`is_required_recipe` " +
                "FROM `drug` d INNER JOIN `substance` s " +
                "ON s.`id` = d.`substance_id` AND d.`name` = ?";
        SELECT_DRUGS_WITH_ANALOGS = "SELECT d.`id`, d.`name`, d.`company`," +
                " d.`country`, s.`name` `substance_name`, d.`price`, " +
                "d.`count`, d.`dosage`, d.`type`, d.`is_required_recipe` " +
                "FROM `drug` d INNER JOIN `substance` s " +
                "ON s.`id` = (SELECT DISTINCT `substance_id` FROM `drug` " +
                "WHERE `name` = ?) AND d.`substance_id` = (" +
                "SELECT DISTINCT `substance_id` FROM `drug` " +
                "WHERE `name` = ?) ORDER BY d.`name`";
        UPDATE_USER_CASH = "UPDATE `user` u SET u.`cash` " +
                "= ? WHERE u.`email` = ?";
        INSERT_DRUG = "INSERT INTO `drug` (`substance_id`, `name`, " +
                "`company`, `country`, `price`, `count`, dosage, `type`, " +
                "`is_required_recipe`) VALUES((SELECT `id` FROM `substance` " +
                "WHERE `name` = ?), ?, ?, ?, ?, ?, ?, ?, ?)";
        SELECT_ALL_SUBSTANCES = "SELECT * FROM `substance`";
        INSERT_SUBSTANCE = "INSERT INTO `substance` (`name`) VALUES(?)";
        SELECT_SUBSTANCE_BY_NAME = "SELECT * FROM `substance` " +
                "WHERE `name` = ?";
        SELECT_DRUG_BY_ID = "SELECT d.`id`, d.`name`, d.`company`," +
                " d.`country`, s.`name` `substance_name`, d.`price`, " +
                "d.`count`, d.`dosage`, d.`type`, d.`is_required_recipe` " +
                "FROM `drug` d INNER JOIN `substance` s " +
                "ON s.`id` = d.`substance_id` AND d.`id` = ?";
        SELECT_ALL_ORDER_ITEMS = "SELECT * FROM `order_item`";
        INSERT_ORDER_ITEM = "INSERT INTO `order_item` (`order_id`, " +
                "`drug_id`, `recipe_id`, `count`) VALUES(?, ?, ?, ?)";
        SELECT_ALL_ORDERS = "SELECT * FROM ``order`";
        INSERT_ORDER = "INSERT INTO `order` (`client_id`, `price`, " +
                "`date`, `is_paid`) VALUES(?, ?, ?, ?)";
        UPDATE_DRUG_COUNT = "UPDATE `drug` d SET d.`count` = ? " +
                "WHERE d.`id` = ?";
        SELECT_DRUG_NAME_DOSAGE = "SELECT d.`id`, d.`name`, d.`company`," +
                " d.`country`, s.`name` `substance_name`, d.`price`, " +
                "d.`count`, d.`dosage`, d.`type`, d.`is_required_recipe` " +
                "FROM `drug` d INNER JOIN `substance` s " +
                "ON s.`id` = d.`substance_id` AND d.`name` = ? " +
                "AND d.`dosage` = ?";
        SELECT_ORDER_ID_PAID = "SELECT * FROM `order` o WHERE " +
                "o.`client_id` = ? AND o.`is_paid` = ?";

    }

    /**
     * Protected default constructor.
     */
    protected AbstractSQLQueries() {

    }

}
