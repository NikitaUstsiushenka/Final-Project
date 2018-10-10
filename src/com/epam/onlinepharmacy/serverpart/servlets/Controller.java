package com.epam.onlinepharmacy.serverpart.servlets;

import com.epam.onlinepharmacy.database.*;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.entity.Order;
import com.epam.onlinepharmacy.entity.OrderItem;
import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.*;
import com.epam.onlinepharmacy.main.UserRole;
import com.epam.onlinepharmacy.sorting.SortDrugsByName;
import com.epam.onlinepharmacy.sorting.SortDrugsByPrice;
import com.epam.onlinepharmacy.sorting.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

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
     * @throws ServletException
     * @throws IOException
     */
    private void process(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {

        final String action = request.getParameter("action");

        AbstractDao dao;

        if (action != null) {

            switch (action) {

                case "all_unknown":
                    this.allDrugs(request, response, "/index.jsp");
                    break;
                case "all_client":
                    this.allDrugs(request, response,
                            "/WEB-INF/jsp/client/client.jsp");
                    break;
                case "all_admin":
                    this.allDrugs(request, response,
                            "/WEB-INF/jsp/admin/drugs.jsp");
                case "registration":
                    this.registration(request, response);
                    break;
                case "search_unknown":
                    this.search(request, response, "/index.jsp");
                    break;
                case "search_client":
                    this.search(request, response,
                            "/WEB-INF/jsp/client/client.jsp");
                    break;
                case "signin":
                    this.userSignIn(request, response);
                    break;
                case "balance":
                    this.addMoney(request, response);
                    break;
                case "buy_drug":
                    this.buyDrug(request, response);
                    break;
                case "logout":
                    request.getSession().removeAttribute("user");
                    request.getRequestDispatcher("/index.jsp")
                            .forward(request, response);
                    break;
                case "sorted_by_name":
                    this.sortedDrugs(request, response, "/index.jsp", "name");
                    break;
                case "sorted_by_price":
                    this.sortedDrugs(request, response, "/index.jsp", "price");
                    break;
                case "sorted_by_name_client":
                    this.sortedDrugs(request, response,
                            "/WEB-INF/jsp/client/client.jsp", "name");
                    break;
                case "sorted_by_price_client":
                    this.sortedDrugs(request, response,
                            "/WEB-INF/jsp/client/client.jsp", "price");
                    break;
                case "sorted_by_name_admin":
                    this.sortedDrugs(request, response,
                            "/WEB-INF/jsp/admin/drugs.jsp", "name");
                    break;
                case "sorted_by_price_admin":
                    this.sortedDrugs(request, response,
                            "/WEB-INF/jsp/admin/drugs.jsp", "price");
                    break;
                case "order":
                    break;
                case "add_drug":
                    this.addDrug(request, response);
                    break;
                case "users":
                    break;
                case "drugs":
                    dao = new DrugDao();
                    try {
                        request.getSession().removeAttribute("drugs");
                        request.getSession().setAttribute("drugs",
                                ((DrugDao) dao).selectAll());
                        request.getRequestDispatcher(
                                "/WEB-INF/jsp/admin/drugs.jsp")
                                .forward(request, response);
                    } catch (ApplicationException e) {
                        LOGGER.log(Level.ERROR, e.getMessage());
                    }
                    break;
                case "back":
                    request.getRequestDispatcher(
                            "/WEB-INF/jsp/admin/admin.jsp")
                            .forward(request, response);
                    break;
                case "add_drugs":
                    this.addMedicines(request, response);
                    break;
                default:
                    break;

            }

        } else {

            UserRole role = UserRole.UNKNOWN;

            if (request.getSession().getAttribute("user") != null) {
                role = ((User) request.getSession()
                        .getAttribute("user")).getRole();
            }

            try {
                this.moveToMenu(request, response, role);
            } catch (ApplicationException e) {

                LOGGER.log(Level.ERROR, e.getMessage());
                e.printStackTrace();

            }

        }

    }

    /**
     * This method redirects the user to a new page depending on the user role.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @param role     value of the user role
     * @throws ApplicationException
     */
    private void moveToMenu(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final UserRole role) throws ApplicationException {

        final List<Drug> drugs;
        final AbstractDao dao;

        try {

            switch (role) {

                case CLIENT:
                    dao = new DrugDao();
                    drugs = ((DrugDao) dao).selectAll();
                    request.getSession().setAttribute("drugs", drugs);
                    request.getRequestDispatcher(
                            "/WEB-INF/jsp/client/client.jsp")
                            .forward(request, response);
                    break;
                case ADMIN:
                    request.getRequestDispatcher(
                            "/WEB-INF/jsp/admin/admin.jsp")
                            .forward(request, response);
                    break;
                case DOCTOR:
                    request.getRequestDispatcher(
                            "/WEB-INF/jsp/doctor/doctor.jsp")
                            .forward(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/index.jsp")
                            .forward(request, response);
                    break;

            }

        } catch (ServletException | IOException e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    /**
     * This method selects drugs from database and displays them on the page.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @param url      value of the new page url
     * @throws ServletException
     * @throws IOException
     */
    private void allDrugs(final HttpServletRequest request,
                          final HttpServletResponse response,
                          final String url)
            throws ServletException, IOException {

        final AbstractDao dao;

        try {

            dao = new DrugDao();
            request.getSession().removeAttribute("drugs");
            request.getSession().setAttribute("drugs",
                    ((DrugDao) dao).selectAll());
            request.getRequestDispatcher(url)
                    .forward(request, response);

        } catch (ApplicationException e) {

            LOGGER.log(Level.DEBUG, e.getMessage());
            e.printStackTrace();

        }

    }

    /**
     * This method searches for drugs.
     *
     * @param request  value of the object HttpServletRequest
     * @param response va;ue of the object HttpServletResponse
     * @param url      value of the url address
     * @throws IOException
     * @throws ServletException
     */
    private void search(final HttpServletRequest request,
                        final HttpServletResponse response,
                        final String url) throws IOException, ServletException {

        final AbstractDao dao = new DrugDao();
        List<Drug> drugs = new ArrayList<>();

        try {

            if (request.getParameter("checkbox") != null) {
                drugs = ((DrugDao) dao).selectByNameWithAnalogs(
                        request.getParameter("drug_name"));
            } else {
                drugs = ((DrugDao) dao).selectByName(
                        request.getParameter("drug_name"));
            }

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

        request.getSession().setAttribute("drugs", drugs);
        request.getRequestDispatcher(url).forward(request, response);

    }

    /**
     * This method performs user authentication.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     */
    private void userSignIn(final HttpServletRequest request,
                            final HttpServletResponse response) {

        final AbstractDao dao = new UserDao();
        final String email = request.getParameter("email");
        final String pass = request.getParameter("password");
        final User user;

        try {

            if (((UserDao) dao).isExistUser(email, pass)) {

                user = ((UserDao) dao).selectUserByEmail(email);
                request.getSession().setAttribute("user", user);
                this.moveToMenu(request, response, user.getRole());

            } else {

                request.setAttribute("errorMessage", "Incorrect data " +
                        "entered, check your login (password).");
                this.moveToMenu(request, response, UserRole.UNKNOWN);

            }

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

    }

    /**
     * This method adds new user in database.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     */
    private void registration(final HttpServletRequest request,
                              final HttpServletResponse response) {

        final AbstractDao dao = new UserDao();
        final EntityFactory factory = new UserFactory();

        try {

            if (((UserDao) dao).isExistUser(
                    request.getParameter("email"))) {

                request.setAttribute("errorMessage",
                        "User with that e-mail already exist.");
                this.moveToMenu(request, response, UserRole.UNKNOWN);

            } else if (!request.getParameter("password")
                    .equals(request.getParameter("repeat_pas"))) {

                request.setAttribute("errorMessage",
                        "Entered passwords do not match.");
                this.moveToMenu(request, response, UserRole.UNKNOWN);

            } else {

                final User user;

                user = ((UserFactory) factory).createEntity(request);
                ((UserDao) dao).insert(user);
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("email",
                        user.getEmail());
                request.getSession().setAttribute("password",
                        user.getPassword());
                request.getSession().setAttribute("role",
                        user.getRole());

                this.moveToMenu(request, response, user.getRole());


            }

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

    }

    /**
     * This method add user money.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void addMoney(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException {

        final AbstractDao dao = new UserDao();
        final String money = request.getParameter("money");
        final User user = (User) request.getSession().getAttribute("user");

        try {

            user.setCash(user.getCash() + Double.parseDouble(money));
            request.getSession().setAttribute("user", user);
            ((UserDao) dao).updateUserCash(user.getEmail(), money);
            request.getRequestDispatcher("/WEB-INF/jsp/client/client.jsp")
                    .forward(request, response);

        } catch (ApplicationException e) {

            e.printStackTrace();
            LOGGER.log(Level.ERROR, e.getMessage());

        }

    }

    /**
     * This method sorted drugs by some parameters.
     *
     * @param request   value of the object HttpServletRequest
     * @param response  value of the object HttpServletResponse
     * @param url       value of url for new page
     * @param parameter value of the sorting parameter
     * @throws ServletException
     * @throws IOException
     */
    private void sortedDrugs(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final String url, final String parameter)
            throws IOException, ServletException {

        final AbstractDao dao = new DrugDao();
        final Specification specification;

        @SuppressWarnings("unchecked")
        List<Drug> drugs = (List<Drug>) request.getSession()
                .getAttribute("drugs");

        if (drugs == null) {

            try {
                drugs = ((DrugDao) dao).selectAll();
            } catch (ApplicationException e) {

                LOGGER.log(Level.DEBUG, e.getMessage());
                e.printStackTrace();

            }

        }

        switch (parameter) {

            case "name":
                specification = new SortDrugsByName();
                specification.execute(drugs);
                break;
            case "price":
                specification = new SortDrugsByPrice();
                specification.execute(drugs);
                break;
            default:
                break;

        }

        request.getSession().removeAttribute("drugs");
        request.getSession().setAttribute("drugs", drugs);
        request.getRequestDispatcher(url).forward(request, response);

    }

    /**
     * This method creating and adding new drug in database.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void addDrug(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {

        final EntityFactory factory = new DrugFactory();
        final Drug drug = ((DrugFactory) factory).createEntity(request);

        AbstractDao dao;
        List<Drug> drugs = new ArrayList<>();

        dao = new SubstanceDao();

        try {

            if (!((SubstanceDao) dao).isExistSubstance(
                    drug.getSubstance().getName())) {
                ((SubstanceDao) dao).insert(drug.getSubstance());
            }

            dao = new DrugDao();
            ((DrugDao) dao).insert(drug);
            drugs = ((DrugDao) dao).selectAll();

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

        request.getSession().removeAttribute("drugs");
        request.getSession().setAttribute("drugs", drugs);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/drugs.jsp")
                .forward(request, response);

    }

    /**
     * This method creates client order item.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     */
    private void buyDrug(final HttpServletRequest request,
                         final HttpServletResponse response) {

        final User user = (User) request.getSession().getAttribute("user");
        final int count = Integer.parseInt(request.getParameter("count"));
        final Order order;
        final Drug drug;
        final OrderItem orderItem;

        EntityFactory factory;
        AbstractDao dao;

        try {

            dao = new DrugDao();
            drug = ((DrugDao) dao).selectByNameDosage(
                    request.getParameter("name"),
                    Integer.parseInt(request.getParameter("dosage")));

            if (drug.getId() != 0) {

                if (drug.getDrugsCount() >= count) {

                    dao = new OrderDao();
                    order = ((OrderDao) dao).selectOrderByIdPaid(
                            user.getId(), false);

                    if (order.getId() == 0) {

                        order.setPaid(false);
                        order.setClientId(user.getId());
                        order.setDate(new Date(new java.util.Date()
                                .getTime()));

                        ((OrderDao) dao).insert(order);

                    }

                    factory = new OrderItemFactory();
                    orderItem = ((OrderItemFactory) factory).createEntity();

                    orderItem.setOrderId(order.getId());
                    orderItem.setCount(count);

                    order.getOrderItems().add(orderItem);
                    dao.insert(orderItem);

                } else {

                    request.setAttribute("errorMessage",
                            "Medicine count is incorrect.");
                    this.moveToMenu(request, response, UserRole.CLIENT);

                }

            } else {

                request.setAttribute("errorMessage",
                        "That medicine doesn't exist.");
                this.moveToMenu(request, response, UserRole.CLIENT);

            }

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }


    }

    /**
     * This method increases the amount of the drug.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void addMedicines(final HttpServletRequest request,
                              final HttpServletResponse response)
            throws ServletException, IOException {

        final AbstractDao dao = new DrugDao();
        final int drugId = Integer.parseInt(request.getParameter("drug_id"));
        final int count = Integer.parseInt(request.getParameter("count"));
        final Drug drug;

        List<Drug> drugs = new ArrayList<>();

        try {

            drug = ((DrugDao) dao).selectById(drugId);
            drug.setDrugsCount(drug.getDrugsCount() + count);

            ((DrugDao) dao).updateDrugCount(drugId, drug.getDrugsCount());

            drugs = ((DrugDao) dao).selectAll();

        } catch (ApplicationException e) {

            LOGGER.log(Level.DEBUG, e.getMessage());
            e.printStackTrace();

        }

        request.getSession().removeAttribute("drugs");
        request.getSession().setAttribute("drugs", drugs);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/drugs.jsp")
                .forward(request, response);

    }

}
