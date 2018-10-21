package com.epam.onlinepharmacy.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * This entity class stores information about the user order.
 *
 * @author Nikita
 * @version 1.0
 * @since 26.09.2018
 */
public final class Order extends AbstractEntity {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    /**
     * List of the order items.
     */
    private List<OrderItem> orderItems;

    /**
     * Value of the object Client.
     */
    private User client;

    /**
     * Value of the order price.
     */
    private double price;

    /**
     * Value of the date.
     */
    private Date date;

    /**
     * Paid order value.
     */
    private boolean paid;

    static {
        LOGGER = LogManager.getLogger(Order.class);
    }

    /**
     * Public default constructor.
     */
    public Order() {

        super();
        this.orderItems = new ArrayList<>();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return getClass().getName() + "@entity:" + super.toString()
                + "@client:" + this.client + "@price:" + this.price
                + "@date:" + this.date + "@isPaid:" + this.paid
                + "@orderItems:" + this.orderItems;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return (int) (super.hashCode() + this.orderItems.size() + this.price
                + (this.client != null ? this.client.hashCode() : 0)
                + this.date.hashCode() + (this.paid ? 1 : 0));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {

        final boolean result;
        final Order order;

        if (object != null) {

            order = (Order) object;

            if (this == object) {
                result = true;
            } else if (getClass() != object.getClass()) {
                result = false;
            } else {
                result = (super.getId().equals(order.getId())
                        && (this.client != null
                        && this.client.equals(order.getClient()))
                        && this.price == order.getPrice()
                        && this.isPaid() == order.isPaid()
                        && (this.date != null && this.date.equals(
                        order.getDate())) && this.orderItems
                        .equals(order.getOrderItems()));
            }

        } else {
            result = false;
        }

        return result;

    }

    /**
     * This method return list of the order items.
     *
     * @return list of the order items
     */
    public List<OrderItem> getOrderItems() {

        return this.orderItems;

    }

    /**
     * This method set new value of the list of order items.
     *
     * @param newOrderItems new list of the order items
     */
    public void setOrderItems(final List<OrderItem> newOrderItems) {

        final String debugString = " Attribute is null in method "
                + "setOrderItems(List<OrderItem>).";

        if (newOrderItems != null) {
            this.orderItems = newOrderItems;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return value of the object User.
     *
     * @return value of the object User
     */
    public User getClient() {

        final User result;

        if (this.client != null) {
            result = this.client;
        } else {
            result = new User();
        }

        return result;

    }

    /**
     * This method set new value of the object User.
     *
     * @param newClient new value of the object User
     */
    public void setClient(final User newClient) {

        final String debugString
                = " Attribute is null in method setClient(User).";

        if (newClient != null) {
            this.client = newClient;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return value of the order price.
     *
     * @return value of the order price
     */
    public double getPrice() {

        return this.price;

    }

    /**
     * This method set new value of the order price.
     *
     * @param newPrice new value of the order price
     */
    public void setPrice(final double newPrice) {

        this.price = newPrice;

    }

    /**
     * This method return value of the order date.
     *
     * @return value of the order date
     */
    public Date getDate() {

        final Date result;

        if (this.date != null) {
            result = this.date;
        } else {
            result = new Date(0);
        }

        return result;

    }

    /**
     * This method set new value of the order date.
     *
     * @param newDate new value of the order date
     */
    public void setDate(final Date newDate) {

        final String debugString
                = " Attribute is null in method setDate(Date).";

        if (newDate != null) {
            this.date = newDate;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return order paid value.
     *
     * @return order paid value
     */
    public boolean isPaid() {

        return this.paid;

    }

    /**
     * This method set new order paid value.
     *
     * @param newPaid new order paid value
     */
    public void setPaid(final boolean newPaid) {

        this.paid = newPaid;

    }

}
