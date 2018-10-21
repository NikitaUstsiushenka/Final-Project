package com.epam.onlinepharmacy.entity;

/**
 * This class stores information about drug recipe.
 *
 * @author Nikita
 * @version 1.0
 * @since 28.08.2018
 */
public final class Recipe extends AbstractEntity {

    /**
     * Value of the client id.
     */
    private int clientId;

    /**
     * Value of the doctor id.
     */
    private int doctorId;

    /**
     * Value of the drug id.
     */
    private int drugId;

    /**
     * Value of the drug count.
     */
    private int count;

    /**
     * Public default constructor.
     */
    public Recipe() {

        super();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return getClass().getName() + "@clientId:" + this.clientId
                + "@doctorId:" + this.doctorId + "@drugId:" + this.drugId
                + "@count:" + this.count;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return super.hashCode() + (this.count != 0 ? this.count : 1)
                + (this.drugId != 0 ? this.drugId : 1)
                + (this.doctorId != 0 ? this.doctorId : 1)
                + (this.clientId != 0 ? this.clientId : 1);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {

        final boolean result;
        final Recipe recipe;

        if (object != null) {

            recipe = (Recipe) object;

            if (this == object) {
                result = true;
            } else if (getClass() != object.getClass()) {
                result = false;
            } else {
                result = (super.getId().equals(recipe.getId())
                        && this.count == recipe.getCount()
                        && this.clientId == recipe.getClientId()
                        && this.doctorId == recipe.getDoctorId()
                        && this.drugId == recipe.getDrugId());
            }

        } else {
            result = false;
        }

        return result;

    }

    /**
     * This method return value of the client id.
     *
     * @return value of the client id
     */
    public int getClientId() {

        return this.clientId;

    }

    /**
     * This method set new value of the client id.
     *
     * @param newClientId new value of the client id
     */
    public void setClientId(final int newClientId) {

        this.clientId = newClientId;

    }

    /**
     * This method return value of the doctor id.
     *
     * @return value of the doctor id
     */
    public int getDoctorId() {

        return this.doctorId;

    }

    /**
     * This method set new value of the doctor id.
     *
     * @param newDoctorId new value of the docotor id
     */
    public void setDoctorId(final int newDoctorId) {

        this.doctorId = newDoctorId;

    }

    /**
     * This method return value of the drug id.
     *
     * @return value of the drug id
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


}
