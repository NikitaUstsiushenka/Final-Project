package com.epam.onlinepharmacy.entity;

/**
 * This entity class stores information about the single order.
 *
 * @author Nikita
 * @version 1.0
 * @since 26.09.2018
 */
public final class OrderItem extends AbstractEntity {

    /**
     * Value of the drug id.
     */
    private int drugId;

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

        return getClass().getName() + "@drugId:" + this.drugId
                + "@orderId:" + this.orderId + "@count:" + this.count
                + "@recipeId:" + this.recipeId;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return super.hashCode() + this.orderId + this.drugId
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
                        && this.drugId == orderItem.getDrugId()
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
     * This method return value of the drug id.
     *
     * @return va;ue of the drug id
     */
    public int getDrugId() {

        return this.drugId;

    }

    /**
     * This method set new value of the drug id.
     *
     * @param newDrugId new value of the drug id
     */
    public void setDrugId(final int newDrugId) {

        this.drugId = newDrugId;

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
