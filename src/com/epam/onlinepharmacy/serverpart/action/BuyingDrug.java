package com.epam.onlinepharmacy.serverpart.action;

import com.epam.onlinepharmacy.database.DrugDao;
import com.epam.onlinepharmacy.database.OrderDao;
import com.epam.onlinepharmacy.database.OrderItemDao;
import com.epam.onlinepharmacy.database.UserDao;
import com.epam.onlinepharmacy.database.dao.AbstractDao;
import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.entity.Order;
import com.epam.onlinepharmacy.entity.OrderItem;
import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.EntityFactory;
import com.epam.onlinepharmacy.factory.OrderItemFactory;
import com.epam.onlinepharmacy.main.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * This class implements interface "Action".
 *
 * @author Nikita
 * @version 1.0
 * @since 20.10.2018
 */
public final class BuyingDrug implements Action {

    /**
     * Logger for debug and error.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(BuyingDrug.class);
    }

    /**
     * Public default constructor.
     */
    public BuyingDrug() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response)
            throws IOException, ServletException {

        final User user = (User) request.getSession().getAttribute("user");
        final int count = Integer.parseInt(request.getParameter("count"));
        final Action action = new Moving();
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
                    action.execute(request, response, UserRole.CLIENT);

                }

            } else {

                request.setAttribute("errorMessage",
                        "That medicine doesn't exist.");
                action.execute(request, response, UserRole.CLIENT);

            }

        } catch (ApplicationException e) {

            LOGGER.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response,
                        final UserRole role) throws ApplicationException {

        final String errorString
                = " This method can't be call from this class.";

        throw new ApplicationException(errorString);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response,
                        final String url)
            throws ApplicationException {

        final String errorString
                = " This method can't be call from this class.";

        throw new ApplicationException(errorString);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response,
                        final String url, final String parameter)
            throws ApplicationException {

        final String errorString
                = " This method can't be call from this class.";

        throw new ApplicationException(errorString);

    }

}
