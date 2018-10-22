package com.epam.onlinepharmacy.serverpart.servlets;

import com.epam.onlinepharmacy.database.DrugDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.main.AbstractProgramConstant;
import com.epam.onlinepharmacy.main.UserRole;
import com.epam.onlinepharmacy.serverpart.action.*;
import com.epam.onlinepharmacy.sorting.SortDrugsByName;
import com.epam.onlinepharmacy.sorting.SortDrugsByPrice;
import com.epam.onlinepharmacy.sorting.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author Nikita
 * @version 1.0
 * @since 15.09.2018
 */
public class Controller extends HttpServlet {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(Controller.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doGet(final HttpServletRequest request,
                      final HttpServletResponse response)
            throws ServletException, IOException {

        this.process(request, response);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPost(final HttpServletRequest request,
                       final HttpServletResponse response)
            throws ServletException, IOException {

        this.process(request, response);

    }

    /**
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @throws ServletException throw ServletException
     * @throws IOException      throw IOException
     */
    private void process(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {

        final String httpAction = request.getParameter("action");
        final String debugString = " Action type is incorrect.";
        final Action action;

        try {

            if (httpAction != null) {

                switch (httpAction) {

                    case "cart_products":
                        action = new ShowingCartProduct();
                        action.execute(request, response);
                        break;
                    case "all_unknown":
                        action = new SelectingDrug();
                        action.execute(request, response, "/index.jsp");
                        break;
                    case "all_client":
                        action = new SelectingDrug();
                        action.execute(request, response,
                                AbstractProgramConstant.URL_CLIENT_JSP);
                        break;
                    case "all_pharm":
                        action = new SelectingDrug();
                        action.execute(request, response,
                                AbstractProgramConstant.URL_PHARM_DRUGS_JSP);
                        break;
                    case "registration":
                        action = new Registration();
                        action.execute(request, response);
                        break;
                    case "search_unknown":
                        action = new Searching();
                        action.execute(request, response, "/index.jsp");
                        break;
                    case "search_client":
                        action = new Searching();
                        action.execute(request, response,
                                AbstractProgramConstant.URL_CLIENT_JSP);
                        break;
                    case "signin":
                        action = new Authorization();
                        action.execute(request, response);
                        break;
                    case "balance":
                        action = new AddingMoney();
                        action.execute(request, response);
                        break;
                    case "buy_drug":
                        action = new BuyingDrug();
                        action.execute(request, response);
                        break;
                    case "logout":
                        request.getSession().removeAttribute("user");
                        request.getRequestDispatcher("/index.jsp")
                                .forward(request, response);
                        break;
                    case "sorted_by_name":
                        action = new SortingDrugs();
                        action.execute(request, response, "/index.jsp",
                                "name");
                        break;
                    case "sorted_by_price":
                        action = new SortingDrugs();
                        action.execute(request, response, "/index.jsp",
                                "price");
                        break;
                    case "sorted_by_name_client":
                        action = new SortingDrugs();
                        action.execute(request, response,
                                AbstractProgramConstant.URL_CLIENT_JSP,
                                "name");
                        break;
                    case "sorted_by_price_client":
                        action = new SortingDrugs();
                        action.execute(request, response,
                                AbstractProgramConstant.URL_CLIENT_JSP,
                                "price");
                        break;
                    case "sorted_by_name_admin":
                        action = new SortingDrugs();
                        action.execute(request, response,
                                AbstractProgramConstant.URL_PHARM_DRUGS_JSP,
                                "name");
                        break;
                    case "sorted_by_price_admin":
                        action = new SortingDrugs();
                        action.execute(request, response,
                                AbstractProgramConstant.URL_PHARM_DRUGS_JSP,
                                "price");
                        break;
                    case "create_drug":
                        action = new CreatingDrug();
                        action.execute(request, response);
                        break;
                    case "pharms":
                        action = new ShowingUser();
                        action.execute(request, response, UserRole.PHARMACIST);
                        break;
                    case "clients":
                        action = new ShowingUser();
                        action.execute(request, response, UserRole.CLIENT);
                        break;
                    case "admins":
                        action = new ShowingUser();
                        action.execute(request, response, UserRole.ADMIN);
                        break;
                    case "show_drugs":
                        action = new ShowingDrug();
                        action.execute(request, response);
                        break;
                    case "back":
                        request.getRequestDispatcher(
                                AbstractProgramConstant.URL_ADMIN_JSP)
                                .forward(request, response);
                        break;
                    case "back_pharm":
                        request.getRequestDispatcher(
                                AbstractProgramConstant.URL_PHARM_JSP)
                                .forward(request, response);
                        break;
                    case "increase_amount":
                        action = new AddingDrug();
                        action.execute(request, response);
                        break;
                    case "cart_back":
                        action = new Moving();
                        action.execute(request, response, UserRole.CLIENT);
                        break;
                    case "cart_pay":
                        action = new PayingOrder();
                        action.execute(request, response);
                        break;
                    case "delete_client":
                        action = new DeletingClient();
                        action.execute(request, response);
                        break;
                    case "show_orders":
                        action = new ShowingClientOrder();
                        action.execute(request, response);
                        break;
                    case "code_back":
                        request.getSession().removeAttribute("code");
                        request.getSession().removeAttribute("user");
                        request.getRequestDispatcher("/index.jsp")
                                .forward(request, response);
                        break;
                    default:
                        LOGGER.log(Level.DEBUG, debugString);
                        break;

                }

            } else {

                UserRole role = UserRole.UNKNOWN;
                action = new Moving();

                if (request.getSession().getAttribute("user") != null) {
                    role = ((User) request.getSession()
                            .getAttribute("user")).getRole();
                }

                action.execute(request, response, role);

            }

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

    }

}
