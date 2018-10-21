package com.epam.onlinepharmacy.database.dao;

import com.epam.onlinepharmacy.entity.OrderItem;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.OrderItemFactory;

import java.util.List;

/**
 * This abstract class stores methods for work with table 'order_item'.
 *
 * @author Nikita
 * @version 1.0
 * @since 03.09.2018
 */
public abstract class AbstractOrderItemDao extends AbstractDao<OrderItem> {

    /**
     * Protected default constructor.
     */
    protected AbstractOrderItemDao() {

        super(new OrderItemFactory());

    }

    /**
     * This method selects order items by order id.
     *
     * @param orderId value of the order id
     * @return list of order items
     * @throws ApplicationException throw SQLException
     */
    public abstract List<OrderItem> selectOrderItem(int orderId)
            throws ApplicationException;

}
