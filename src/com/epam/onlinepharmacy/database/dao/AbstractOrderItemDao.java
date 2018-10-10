package com.epam.onlinepharmacy.database.dao;

import com.epam.onlinepharmacy.entity.OrderItem;
import com.epam.onlinepharmacy.factory.OrderItemFactory;

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

}
