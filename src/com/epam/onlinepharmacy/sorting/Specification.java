package com.epam.onlinepharmacy.sorting;

import com.epam.onlinepharmacy.entity.AbstractEntity;

import java.util.List;

/**
 * This interface stores method execute().
 *
 * @author Nikita
 * @version 1.0
 * @since 26.09.2018
 */
public interface Specification {

    /**
     * This method execute query.
     *
     * @param entities list of entities
     */
    void execute(List<? extends AbstractEntity> entities);

}
