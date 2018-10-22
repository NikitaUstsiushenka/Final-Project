package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.main.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This interface stores method execute().
 *
 * @author Nikita
 * @version 1.0
 * @since 18.10.2018
 */
public interface Action {

    /**
     * This method executes some action with http request.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @throws IOException          throw IOException
     * @throws ServletException     throw ServletException
     * @throws ApplicationException throw ApplicationException
     */
    void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, ApplicationException;

    /**
     * This method executes some action with http request.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @param role     value of the user role
     * @throws IOException          throw IOException
     * @throws ServletException     throw ServletException
     * @throws ApplicationException throw ApplicationException
     */
    void execute(HttpServletRequest request, HttpServletResponse response,
                 UserRole role)
            throws ServletException, IOException, ApplicationException;

    /**
     * This method executes some action with http request.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @param url      value of the url
     * @throws IOException          throw IOException
     * @throws ServletException     throw ServletException
     * @throws ApplicationException throw ApplicationException
     */
    void execute(HttpServletRequest request, HttpServletResponse response,
                 String url)
            throws ServletException, IOException, ApplicationException;

    /**
     * This method executes some action with http request.
     *
     * @param request   value of the object HttpServletRequest
     * @param response  value of the object HttpServletResponse
     * @param url       value of the url
     * @param parameter value of the parameter
     * @throws IOException          throw IOException
     * @throws ServletException     throw ServletException
     * @throws ApplicationException throw ApplicationException
     */
    void execute(HttpServletRequest request, HttpServletResponse response,
                 String url, String parameter)
            throws ServletException, IOException, ApplicationException;

}
