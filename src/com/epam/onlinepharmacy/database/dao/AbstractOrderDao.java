package com.epam.onlinepharmacy.database.dao;

import com.epam.onlinepharmacy.entity.Order;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.OrderFactory;

import java.util.List;

/**
 * This abstract class stores methods for work with table `order`.
 *
 * @author Nikita
 * @version 1.0
 * @since 04.09.2018
 */
public abstract class AbstractOrderDao extends AbstractDao<Order> {

    /**
     * Protected default constructor.
     */
    protected AbstractOrderDao() {

        super(new OrderFactory());

    }

    /**
     * This method selects order by client id and paid.
     *
     * @param clientId value of the client id
     * @param paid     value of the paid
     * @return value of the object Order
     * @throws ApplicationException throw SQLException
     */
    public abstract Order selectOrder(int clientId, boolean paid)
            throws ApplicationException;

    /**
     * This method selects orders by client id.
     *
     * @param clientId value of the client id
     * @return list of orders
     * @throws ApplicationException throw SQLException
     */
    public abstract List<Order> selectOrder(int clientId)
            throws ApplicationException;

    /**
     * This method updates order price in table 'order'.
     *
     * @param clientId value of the client id
     * @param paid     value of the paid
     * @param newPrice value of the new price
     * @throws ApplicationException throw SQLException
     */
    public abstract void update(int clientId, boolean paid, double newPrice)
            throws ApplicationException;

    /**
     * This method establishes that the order is paid.
     *
     * @param orderId integer value of the order id
     * @throws ApplicationException throw SQLException
     */
    public abstract void update(int orderId)
            throws ApplicationException;

}
