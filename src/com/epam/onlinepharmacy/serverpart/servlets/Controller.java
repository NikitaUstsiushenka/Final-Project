package com.epam.onlinepharmacy.serverpart.servlets;

import com.epam.onlinepharmacy.database.OrderDao;
import com.epam.onlinepharmacy.database.UserDao;
import com.epam.onlinepharmacy.database.OrderItemDao;
import com.epam.onlinepharmacy.database.DrugDao;
import com.epam.onlinepharmacy.database.SubstanceDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.email.SendingEmail;
import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.entity.Order;
import com.epam.onlinepharmacy.entity.OrderItem;
import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.OrderItemFactory;
import com.epam.onlinepharmacy.factory.DrugFactory;
import com.epam.onlinepharmacy.factory.EntityFactory;
import com.epam.onlinepharmacy.factory.UserFactory;
import com.epam.onlinepharmacy.main.AbstractProgramConstant;
import com.epam.onlinepharmacy.main.UserRole;
import com.epam.onlinepharmacy.sorting.SortDrugsByName;
import com.epam.onlinepharmacy.sorting.SortDrugsByPrice;
import com.epam.onlinepharmacy.sorting.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        final String action = request.getParameter("action");

        if (action != null) {

            switch (action) {

                case "cart_products":
                    this.showCartProducts(request, response);
                    break;
                case "all_unknown":
                    this.allDrugs(request, response, "/index.jsp");
                    break;
                case "all_client":
                    this.allDrugs(request, response,
                            AbstractProgramConstant.URL_CLIENT_JSP);
                    break;
                case "all_pharm":
                    this.allDrugs(request, response,
                            AbstractProgramConstant.URL_PHARM_DRUGS_JSP);
                case "registration":
                    this.registration(request, response);
                    break;
                case "search_unknown":
                    this.search(request, response, "/index.jsp");
                    break;
                case "search_client":
                    this.search(request, response,
                            AbstractProgramConstant.URL_CLIENT_JSP);
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
                            AbstractProgramConstant.URL_CLIENT_JSP, "name");
                    break;
                case "sorted_by_price_client":
                    this.sortedDrugs(request, response,
                            AbstractProgramConstant.URL_CLIENT_JSP, "price");
                    break;
                case "sorted_by_name_admin":
                    this.sortedDrugs(request, response,
                            AbstractProgramConstant.URL_PHARM_DRUGS_JSP,
                            "name");
                    break;
                case "sorted_by_price_admin":
                    this.sortedDrugs(request, response,
                            AbstractProgramConstant.URL_PHARM_DRUGS_JSP,
                            "price");
                    break;
                case "create_drug":
                    this.addDrug(request, response);
                    break;
                case "pharms":
                    this.showUsers(request, response, UserRole.PHARMACIST);
                    break;
                case "clients":
                    this.showUsers(request, response, UserRole.CLIENT);
                    break;
                case "admins":
                    this.showUsers(request, response, UserRole.ADMIN);
                    break;
                case "show_drugs":
                    this.showDrugs(request, response);
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
                    this.addMedicines(request, response);
                    break;
                case "cart_back":
                    try {
                        this.moveToMenu(request, response, UserRole.CLIENT);
                    } catch (ApplicationException e) {
                        LOGGER.log(Level.DEBUG, e.getMessage());
                    }
                    break;
                case "cart_pay":
                    this.orderPay(request, response);
                    break;
                case "delete_client":
                    this.deleteClient(request, response);
                    break;
                case "show_orders":
                    this.showClientOrders(request, response);
                    break;
                case "code_back":
                    request.getSession().removeAttribute("code");
                    request.getSession().removeAttribute("user");
                    request.getRequestDispatcher("/index.jsp")
                            .forward(request, response);
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
     * @throws ApplicationException throw ServletException or IOException
     */
    private void moveToMenu(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final UserRole role) throws ApplicationException {

        final List<Drug> drugs;
        final List<OrderItem> orders;
        final Order order;
        final String debugString;

        AbstractDao dao;

        try {

            switch (role) {

                case CLIENT:
                    debugString = " Move to client page.";
                    dao = new DrugDao();
                    drugs = ((DrugDao) dao).selectAll();
                    dao = new OrderDao();
                    order = ((OrderDao) dao).selectOrder(
                            ((User) request.getSession().getAttribute("user"))
                                    .getId(), false);
                    dao = new OrderItemDao();
                    orders = ((OrderItemDao) dao).selectOrderItem(
                            order.getId());
                    order.setOrderItems(orders);
                    request.getSession().setAttribute("drugs", drugs);
                    request.getRequestDispatcher(
                            AbstractProgramConstant.URL_CLIENT_JSP)
                            .forward(request, response);
                    break;
                case ADMIN:
                    debugString = " Move to administrator page.";
                    request.getRequestDispatcher(
                            AbstractProgramConstant.URL_ADMIN_JSP)
                            .forward(request, response);
                    break;
                case DOCTOR:
                    debugString = " Move to doctor page.";
                    request.getRequestDispatcher(
                            AbstractProgramConstant.URL_DOCTOR_JSP)
                            .forward(request, response);
                    break;
                case PHARMACIST:
                    debugString = " Move to pharmacist page.";
                    request.getRequestDispatcher(
                            AbstractProgramConstant.URL_PHARM_JSP)
                            .forward(request, response);
                    break;
                default:
                    debugString = " Move to start page.";
                    request.getRequestDispatcher("/index.jsp")
                            .forward(request, response);
                    break;

            }

            LOGGER.log(Level.DEBUG, debugString);

        } catch (ServletException | IOException e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    /**
     * This method goes to the page with unpaid orders.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @throws ServletException throw ServletException
     * @throws IOException      throw IOException
     */
    private void showCartProducts(final HttpServletRequest request,
                                  final HttpServletResponse response)
            throws ServletException, IOException {

        final Order order = (Order) request.getSession().getAttribute("order");
        final String debugString;

        if (order != null && order.getOrderItems().size() != 0) {

            request.getRequestDispatcher(
                    AbstractProgramConstant.URL_CLIENT_CART)
                    .forward(request, response);
            debugString = " Go to the page with orders.";

        } else {

            debugString = " User doesn't have orders.";

            try {

                request.setAttribute("errorMessage", "You don't have orders.");
                this.moveToMenu(request, response, UserRole.CLIENT);

            } catch (ApplicationException e) {

                LOGGER.log(Level.ERROR, e.getMessage());
                e.printStackTrace();

            }

        }

        LOGGER.log(Level.DEBUG, debugString);

    }

    /**
     * This method pays for the order.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @throws ServletException throw ServletException
     * @throws IOException      throw IOException
     */
    private void orderPay(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException {

        final String debugString;
        final List<OrderItem> orders;

        AbstractDao dao;
        User user = (User) request.getSession().getAttribute("user");
        Order order = (Order) request.getSession().getAttribute("order");

        if (user.getCash() >= order.getPrice()) {

            debugString = " User " + user
                    + " has enough money to pay for the order.";

            try {

                dao = new OrderDao();
                ((OrderDao) dao).update(order.getId());

                dao = new UserDao();
                ((UserDao) dao).updateUserCash(user.getEmail(),
                        user.getCash() - order.getPrice());
                user = ((UserDao) dao).selectUser(user.getEmail());

                request.getSession().removeAttribute("order");
                request.getSession().removeAttribute("user");
                request.getSession().setAttribute("user", user);
                request.setAttribute("message", "Order successfully paid.");

                dao = new OrderDao();
                order = ((OrderDao) dao).selectOrder(
                        ((User) request.getSession().getAttribute("user"))
                                .getId(), false);
                dao = new OrderItemDao();
                orders = ((OrderItemDao) dao).selectOrderItem(
                        order.getId());
                order.setOrderItems(orders);
                request.getSession().setAttribute("order", order);

                response.sendRedirect("pharmacy");

            } catch (ApplicationException e) {

                LOGGER.log(Level.ERROR, e.getMessage());
                e.printStackTrace();

            }

        } else {

            request.setAttribute("errorMessage",
                    "You don't have enough money.");
            request.getRequestDispatcher(
                    AbstractProgramConstant.URL_CLIENT_CART)
                    .forward(request, response);

            debugString = " User " + user
                    + " hasn't enough money to pay for the order.";

        }

        LOGGER.log(Level.DEBUG, debugString);

    }

    /**
     * This method selects drugs from database and displays them on the page.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @param url      value of the new page url
     * @throws ServletException throw ServletException
     * @throws IOException      throw IOException
     */
    private void allDrugs(final HttpServletRequest request,
                          final HttpServletResponse response,
                          final String url)
            throws ServletException, IOException {

        final AbstractDao dao;
        final String debugString;

        try {

            dao = new DrugDao();
            request.getSession().removeAttribute("drugs");
            request.getSession().setAttribute("drugs",
                    ((DrugDao) dao).selectAll());
            request.getRequestDispatcher(url)
                    .forward(request, response);

            debugString = " All drugs selected from database.";
            LOGGER.log(Level.DEBUG, debugString);

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
     * @throws IOException      throw IOException
     * @throws ServletException throw ServletException
     */
    private void search(final HttpServletRequest request,
                        final HttpServletResponse response,
                        final String url)
            throws IOException, ServletException {

        final AbstractDao dao = new DrugDao();
        List<Drug> drugs = new ArrayList<>();
        final String debugString;

        try {

            if (request.getParameter("checkbox") != null) {

                drugs = ((DrugDao) dao).selectDrugWithAnalogs(
                        request.getParameter("drug_name"));
                debugString = " Search by name with analogs is completed.";

            } else {

                drugs = ((DrugDao) dao).selectDrug(
                        request.getParameter("drug_name"));
                debugString = " Search by name is completed.";

            }

            LOGGER.log(Level.DEBUG, debugString);

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
        final String debugString;

        try {

            if (((UserDao) dao).isExistUser(email, pass)) {

                user = ((UserDao) dao).selectUser(email);

                request.getSession().setAttribute("user", user);
                request.setAttribute("message",
                        "Hi, " + user.getUserName() + " "
                                + user.getUserLastName());

                this.moveToMenu(request, response, user.getRole());
                debugString = " User with email " + email
                        + " and password " + pass + " sign in.";

            } else {

                debugString = " User with email " + email
                        + " and password " + pass + " doesn't sign in.";
                request.setAttribute("errorMessage", "Incorrect data "
                        + "entered, check your login (password).");
                this.moveToMenu(request, response, UserRole.UNKNOWN);

            }

            LOGGER.log(Level.DEBUG, debugString);

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
     * @throws IOException      throw IOException
     * @throws ServletException throw ServletException
     */
    private void registration(final HttpServletRequest request,
                              final HttpServletResponse response)
            throws ServletException, IOException {

        final EntityFactory factory = new UserFactory();
        final Random random = new Random();
        final User user;
        final String debugString;
        final List<Drug> drugs;
        final List<OrderItem> orders;
        final Order order;

        AbstractDao dao = new UserDao();
        int code;

        try {

            if (request.getSession().getAttribute("code") != null
                    && request.getParameter("code") != null) {

                code = (int) request.getSession().getAttribute("code");
                user = (User) request.getSession().getAttribute("user");

                if (code == Integer.parseInt(request.getParameter("code"))) {

                    ((UserDao) dao).insert(user);
                    request.getSession().setAttribute("user", user);
                    request.setAttribute("message",
                            "Registration successful.");

                    debugString = " User " + user + " has registered.";

                    if (user.getRole() == UserRole.CLIENT) {

                        dao = new DrugDao();
                        drugs = ((DrugDao) dao).selectAll();
                        dao = new OrderDao();
                        order = ((OrderDao) dao).selectOrder(
                                ((User) request.getSession()
                                        .getAttribute("user"))
                                        .getId(), false);
                        dao = new OrderItemDao();
                        orders = ((OrderItemDao) dao).selectOrderItem(
                                order.getId());
                        order.setOrderItems(orders);

                        request.getSession().setAttribute("drugs", drugs);
                        response.sendRedirect("pharmacy");

                    } else {
                        this.moveToMenu(request, response, user.getRole());
                    }

                } else {

                    debugString = " Entered code is invalid.";
                    request.setAttribute("errorMessage", "Invalid code.");
                    request.getRequestDispatcher(
                            AbstractProgramConstant.URL_CLIENT_CODE)
                            .forward(request, response);

                }

            } else {

                user = ((UserFactory) factory).createEntity(request);

                if (((UserDao) dao).isExistUser(user.getEmail())) {

                    debugString = " User with that e-mail already exist.";
                    request.setAttribute("errorMessage", debugString);
                    this.moveToMenu(request, response, UserRole.UNKNOWN);

                } else if (!user.getPassword().equals(request.getParameter(
                        "repeat_pas"))) {

                    debugString = " Entered passwords do not match.";
                    request.setAttribute("errorMessage", debugString);
                    this.moveToMenu(request, response, UserRole.UNKNOWN);

                } else {

                    debugString = " Code generating and sending by e-mail.";

                    code = AbstractProgramConstant.NUMBER_1000
                            + random.nextInt(
                            AbstractProgramConstant.NUMBER_9000);
                    SendingEmail.send(user.getEmail(), "Online-pharmacy Code",
                            "Code: " + code);

                    request.getSession().setAttribute("code", code);
                    request.getSession().setAttribute("user", user);
                    request.setAttribute("errorMessage",
                            "To confirm the registration, "
                                    + "enter the code sent to your email");
                    request.getRequestDispatcher(
                            AbstractProgramConstant.URL_CLIENT_CODE)
                            .forward(request, response);

                }

            }

            LOGGER.log(Level.DEBUG, debugString);

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
     * @throws IOException throw IOException
     */
    private void addMoney(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws IOException {

        final AbstractDao dao = new UserDao();
        final String money = request.getParameter("money");
        final User user = (User) request.getSession().getAttribute("user");
        final String debugString;

        try {

            user.setCash(user.getCash() + Double.parseDouble(money));
            request.getSession().setAttribute("user", user);
            ((UserDao) dao).updateUserCash(user.getEmail(), user.getCash());
            request.setAttribute("message", "Cash replenished successfully.");
            response.sendRedirect("pharmacy");

            SendingEmail.send(user.getEmail(), "Cash",
                    user.getCash() + " Br credited to your account.");

            debugString = " Increase user " + user + " money by " + money;
            LOGGER.log(Level.DEBUG, debugString);

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
     * @throws ServletException throw ServletException
     * @throws IOException      throw IOException
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
     * @throws IOException throw IOException
     */
    private void addDrug(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws IOException {

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
        response.sendRedirect("pharmacy");

    }

    /**
     * This method creates client order item.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @throws IOException throw IOException
     */
    private void buyDrug(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws IOException {

        final User user = (User) request.getSession().getAttribute("user");
        final int count = Integer.parseInt(request.getParameter("count"));
        final Drug drug;
        final OrderItem orderItem;
        final List<OrderItem> orders;

        final String debugString1;
        final String debugString2;

        EntityFactory factory;
        AbstractDao dao;
        Order order;

        try {

            dao = new DrugDao();
            drug = ((DrugDao) dao).selectDrug(
                    request.getParameter("name"),
                    Integer.parseInt(request.getParameter("dosage")));

            if (drug.getId() != 0) {

                if (drug.getDrugsCount() >= count) {

                    ((DrugDao) dao).updateDrug(drug.getId(),
                            drug.getDrugsCount() - count);

                    dao = new OrderDao();
                    order = ((OrderDao) dao).selectOrder(
                            user.getId(), false);
                    order.setPrice(order.getPrice() + count * drug.getPrice());
                    ((OrderDao) dao).update(user.getId(),
                            false, order.getPrice());

                    if (order.getId() == 0) {

                        dao = new UserDao();

                        order.setPaid(false);
                        order.setClient(((UserDao) dao)
                                .selectUser(user.getId()));
                        order.setDate(new Date(new java.util.Date()
                                .getTime()));

                        dao = new OrderDao();
                        ((OrderDao) dao).insert(order);
                        order.setId(((OrderDao) dao).selectOrder(
                                user.getId(), false).getId());

                        debugString1 = " Object " + order + " created.";
                        LOGGER.log(Level.DEBUG, debugString1);

                    } else {

                        dao = new OrderItemDao();
                        order.setOrderItems(((OrderItemDao) dao)
                                .selectOrderItem(order.getId()));

                    }

                    dao = new OrderItemDao();
                    factory = new OrderItemFactory();
                    orderItem = ((OrderItemFactory) factory).createEntity();

                    orderItem.setOrderId(order.getId());
                    orderItem.setCount(count);
                    orderItem.setDrug(drug);
                    orderItem.setRecipeId(0);

                    debugString2 = " Object " + orderItem + " created.";
                    LOGGER.log(Level.DEBUG, debugString2);

                    order.getOrderItems().add(orderItem);
                    ((OrderItemDao) dao).insert(orderItem);

                    request.setAttribute("message",
                            "Order successfully added in cart.");

                    dao = new OrderDao();
                    order = ((OrderDao) dao).selectOrder(
                            ((User) request.getSession().getAttribute("user"))
                                    .getId(), false);
                    dao = new OrderItemDao();
                    orders = ((OrderItemDao) dao).selectOrderItem(
                            order.getId());
                    order.setOrderItems(orders);
                    request.getSession().setAttribute("order", order);

                    response.sendRedirect("pharmacy");

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
     * @throws IOException throw IOException
     */
    private void addMedicines(final HttpServletRequest request,
                              final HttpServletResponse response)
            throws IOException {

        final AbstractDao dao = new DrugDao();
        final String drugName = request.getParameter("name");
        final int dosage = Integer.parseInt(request.getParameter("dosage"));
        final int count = Integer.parseInt(request.getParameter("count"));
        final Drug drug;
        final String debugString;

        List<Drug> drugs = new ArrayList<>();

        try {

            drug = ((DrugDao) dao).selectDrug(drugName, dosage);
            drug.setDrugsCount(drug.getDrugsCount() + count);

            ((DrugDao) dao).updateDrug(drugName, dosage, drug.getDrugsCount());

            drugs = ((DrugDao) dao).selectAll();

            debugString = " Amount of the drug " + drug + " increased.";
            LOGGER.log(Level.DEBUG, debugString);

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

        request.getSession().removeAttribute("drugs");
        request.getSession().setAttribute("drugs", drugs);
        response.sendRedirect("pharmacy");

    }

    /**
     * This method selects all drugs from database and show their.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @throws ServletException throw ServletException
     * @throws IOException      throw IOException
     */
    private void showDrugs(final HttpServletRequest request,
                           final HttpServletResponse response)
            throws ServletException, IOException {

        final AbstractDao dao = new DrugDao();

        try {

            request.getSession().setAttribute("drugs",
                    ((DrugDao) dao).selectAll());
            request.getRequestDispatcher(
                    AbstractProgramConstant.URL_PHARM_DRUGS_JSP)
                    .forward(request, response);

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

    }

    /**
     * This method selects all users from database and show their.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @param role     value of the user role
     * @throws ServletException throw ServletException
     * @throws IOException      throw IOException
     */
    private void showUsers(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final UserRole role)
            throws ServletException, IOException {

        final AbstractDao dao = new UserDao();
        final List<User> users;
        final String message;

        try {

            switch (role) {

                case CLIENT:
                    users = ((UserDao) dao).selectUser(UserRole.CLIENT);
                    message = "Clients don't exist.";
                    break;
                case ADMIN:
                    users = ((UserDao) dao).selectUser(UserRole.ADMIN);
                    message = "Admins don't exist.";
                    break;
                case PHARMACIST:
                    users = ((UserDao) dao).selectUser(UserRole.PHARMACIST);
                    message = "Pharmacists don't exist.";
                    break;
                default:
                    users = new ArrayList<>();
                    message = "User type is incorrect.";
                    break;

            }

            request.getSession().setAttribute("users", users);

            if (users.size() != 0) {
                request.getRequestDispatcher(
                        AbstractProgramConstant.URL_ADMIN_USERS)
                        .forward(request, response);
            } else {

                request.setAttribute("errorMessage", message);
                request.getRequestDispatcher(
                        AbstractProgramConstant.URL_ADMIN_JSP)
                        .forward(request, response);

            }

        } catch (ApplicationException e) {

            LOGGER.log(Level.DEBUG, e.getMessage());
            e.printStackTrace();

        }

    }

    /**
     * This method deletes client.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @throws IOException throw IOException
     */
    private void deleteClient(final HttpServletRequest request,
                              final HttpServletResponse response)
            throws IOException {

        final int clientId = Integer.parseInt(
                request.getParameter("client_id"));
        final AbstractDao dao = new UserDao();
        final String debugString;

        List<User> clients = new ArrayList<>();

        try {

            ((UserDao) dao).deleteClientById(clientId);
            clients = ((UserDao) dao).selectUser(UserRole.CLIENT);

            debugString = " Client was removed.";
            LOGGER.log(Level.DEBUG, debugString);

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

        request.removeAttribute("users");
        request.setAttribute("users", clients);
        response.sendRedirect("pharmacy");

    }

    /**
     * This method show client orders by client id.
     *
     * @param request  value of the object HttpServletRequest
     * @param response value of the object HttpServletResponse
     * @throws ServletException throw ServletException
     * @throws IOException      throw IOException
     */
    private void showClientOrders(final HttpServletRequest request,
                                  final HttpServletResponse response)
            throws ServletException, IOException {

        final int clientId = Integer.parseInt(
                request.getParameter("client_id"));
        final String debugString = " Show client orders with id " + clientId;

        AbstractDao dao;
        List<Order> orders = new ArrayList<>();

        try {

            dao = new OrderDao();
            orders = ((OrderDao) dao).selectOrder(clientId);

            dao = new OrderItemDao();

            for (final Order order : orders) {
                order.setOrderItems(((OrderItemDao) dao).selectOrderItem(
                        order.getId()));
            }

            LOGGER.log(Level.DEBUG, debugString);

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher(
                AbstractProgramConstant.URL_ADMIN_ORDERS)
                .forward(request, response);

    }

}
