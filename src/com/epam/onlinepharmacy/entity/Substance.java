package com.epam.onlinepharmacy.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class stores information about drug substance.
 *
 * @author Nikita
 * @version 1.0
 * @since 30.09.2018
 */
public final class Substance extends AbstractEntity {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    /**
     * Value of the substance name.
     */
    private String name;

    static {
        LOGGER = LogManager.getLogger(Substance.class);
    }

    /**
     * Public default constructor.
     */
    public Substance() {

        super();

    }

    /**
     * Public initialize constructor.
     *
     * @param newName value of the new name
     */
    public Substance(final String newName) {

        super();
        this.name = newName;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return getClass().getName() + "@entity:" + super.toString()
                + "@name:" + this.name;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return super.hashCode() + (this.name != null
                ? this.name.hashCode() : 0);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {

        final boolean result;
        final Substance substance;

        if (object != null) {

            substance = (Substance) object;

            if (this == object) {
                result = true;
            } else if (getClass() != object.getClass()) {
                result = false;
            } else {
                result = (super.getId().equals(substance.getId())
                        && this.name.equals(substance.getName()));
            }

        } else {
            result = false;
        }

        return result;

    }

    /**
     * This method return value of the substance name.
     *
     * @return value of the substance name
     */
    public String getName() {

        final String result;

        if (this.name != null) {
            result = this.name;
        } else {
            result = "";
        }

        return result;

    }

    /**
     * This method set new value of the substance name.
     *
     * @param newName new value of the substance name
     */
    public void setName(final String newName) {

        final String debugString
                = " Attribute is null in method setName(String).";

        if (newName != null) {
            this.name = newName;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

}
