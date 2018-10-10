package com.epam.onlinepharmacy.entity;

import com.epam.onlinepharmacy.main.AbstractProgramConstants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This abstract class stores information about entity id.
 *
 * @author Nikita
 * @version 1.0
 * @since 12.09.2018
 */
public abstract class AbstractEntity {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    /**
     * Value of the entity id.
     */
    private Integer id;

    static {
        LOGGER = LogManager.getLogger(AbstractEntity.class);
    }

    /**
     * Protected default constructor.
     * */
    protected AbstractEntity() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return getClass().getName() + "@id:" + this.id;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return (this.id != null ? this.id.hashCode() : 0);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {

        final boolean result;
        final AbstractEntity abstractEntity = (AbstractEntity) object;

        if (object != null) {

            if (this == object) {
                result = true;
            } else if (this.getClass() != object.getClass()) {
                result = false;
            } else {
                result = (this.id != null
                        && this.id.equals(abstractEntity.getId()));
            }

        } else {
            result = false;
        }

        return result;

    }

    /**
     * This method return value of the entity id.
     *
     * @return value of the entity id
     */
    public Integer getId() {

        Integer resultId;

        if (this.id != null) {
            resultId = this.id;
        } else {
            resultId = 0;
        }

        return resultId;

    }

    /**
     * This method set new value of the entity id.
     *
     * @param newId new value of the entity id
     */
    public void setId(final Integer newId) {

        final String debugString = getClass().getName()
                + ": Attribute is null in method setId(Integer).";

        if (newId != null) {
            this.id = newId;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }


    }

}
