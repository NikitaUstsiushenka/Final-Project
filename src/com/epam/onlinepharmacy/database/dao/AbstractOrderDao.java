package com.epam.onlinepharmacy.database.dao;

import com.epam.onlinepharmacy.entity.Order;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.OrderFactory;

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
     * @throws ApplicationException
     */
    public abstract Order selectOrderByIdPaid(final int clientId,
                                              final boolean paid)
            throws ApplicationException;

}
