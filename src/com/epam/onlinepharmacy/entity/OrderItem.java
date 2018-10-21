package com.epam.onlinepharmacy.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This entity class stores information about the single order.
 *
 * @author Nikita
 * @version 1.0
 * @since 26.09.2018
 */
public final class OrderItem extends AbstractEntity {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    /**
     * Value of the object Drug.
     */
    private Drug drug;

    /**
     * Value of the order id.
     */
    private int orderId;

    /**
     * Value of the drug count.
     */
    private int count;

    /**
     * Value of the recipe id.
     */
    private int recipeId;

    static {
        LOGGER = LogManager.getLogger(OrderItem.class);
    }

    /**
     * Public default constructor.
     */
    public OrderItem() {

        super();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return getClass().getName() + "@drug:" + this.drug
                + "@orderId:" + this.orderId + "@count:" + this.count
                + "@recipeId:" + this.recipeId;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return super.hashCode() + this.orderId
                + (this.drug == null ? 0 : this.drug.hashCode())
                + this.count + this.recipeId;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {

        final boolean result;
        final OrderItem orderItem;

        if (object != null) {

            orderItem = (OrderItem) object;

            if (this == object) {
                result = true;
            } else if (getClass() != object.getClass()) {
                result = false;
            } else {
                result = (super.getId().equals(orderItem.getId())
                        && (this.drug != null && this.drug
                        .equals(orderItem.getDrug()))
                        && this.orderId == orderItem.getOrderId()
                        && this.count == orderItem.getCount()
                        && this.orderId == orderItem.getRecipeId());
            }

        } else {
            result = false;
        }

        return result;

    }

    /**
     * This method return value of the object Drug.
     *
     * @return value of the object Drug
     */
    public Drug getDrug() {

        final Drug result;

        if (this.drug != null) {
            result = this.drug;
        } else {
            result = new Drug();
        }

        return result;

    }

    /**
     * This method set new value of the drug id.
     *
     * @param newDrug new value of the object Drug
     */
    public void setDrug(final Drug newDrug) {

        final String debugString
                = " Attribute is null in method setDrug(Drug).";

        if (newDrug != null) {
            this.drug = newDrug;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return value of the order id.
     *
     * @return value of the order id
     */
    public int getOrderId() {

        return this.orderId;

    }

    /**
     * This method set new value of the order id.
     *
     * @param newOrderId new value of the order id
     */
    public void setOrderId(final int newOrderId) {

        this.orderId = newOrderId;

    }

    /**
     * This method return value of the drug count.
     *
     * @return value of the drug count
     */
    public int getCount() {

        return this.count;

    }

    /**
     * This method set new value of the drug count.
     *
     * @param newCount new value of the drug count
     */
    public void setCount(final int newCount) {

        this.count = newCount;

    }

    /**
     * This method return value of the recipe id.
     *
     * @return value of the recipe id
     */
    public int getRecipeId() {

        return this.recipeId;

    }

    /**
     * This method set new value of the recipe id.
     *
     * @param newRecipeId new value of the recipe id
     */
    public void setRecipeId(final int newRecipeId) {

        this.recipeId = newRecipeId;

    }

}
