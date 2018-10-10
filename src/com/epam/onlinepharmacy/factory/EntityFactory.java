package com.epam.onlinepharmacy.factory;

import com.epam.onlinepharmacy.entity.AbstractEntity;
import com.epam.onlinepharmacy.exceptions.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

/**
 * Interface for stores method that create entity.
 *
 * @author Nikita
 * @version 1.0
 * @since 16.09.2018
 */
public interface EntityFactory {

    /**
     * This method creates objects that extend AbstractEntity.
     *
     * @return value of the object AbstractEntity
     */
    AbstractEntity createEntity();

    /**
     * This method creates objects that extend AbstractEntity class.
     *
     * @param request value of the object HttpServletRequest
     * @return value of the object AbstractEntity
     */
    AbstractEntity createEntity(HttpServletRequest request);

    /**
     * This method creates objects that extend AbstractEntity class.
     *
     * @param resultSet value of the object ResultSet
     * @return value of the object AbstractEntity
     * @throws ApplicationException
     */
    AbstractEntity createEntity(ResultSet resultSet)
            throws ApplicationException;

}
