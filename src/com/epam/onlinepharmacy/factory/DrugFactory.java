package com.epam.onlinepharmacy.factory;

import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.entity.Substance;
import com.epam.onlinepharmacy.main.DrugType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements EntityFactory.
 *
 * @author Nikita
 * @version 1.0
 * @since 17.09.2018
 */
public final class DrugFactory implements EntityFactory {

    /**
     * Logger for debug and errors.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(DrugFactory.class);
    }

    /**
     * Public default constructor.
     */
    public DrugFactory() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drug createEntity() {

        final Drug drug = new Drug();
        final String debugString = " Object " + drug + " created.";

        LOGGER.log(Level.DEBUG, debugString);

        return drug;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drug createEntity(final HttpServletRequest request) {

        final Drug drug = new Drug();
        final String debugString1 = " Attribute is null in method "
                + "createEntity(HttpServletRequest).";
        final String debugString2;

        if (request != null) {

            drug.setDrugName(request.getParameter("name"));
            drug.setDosage(Integer.parseInt(request.getParameter("dosage")));
            drug.setCompany(request.getParameter("company"));
            drug.setCompanyCountry(request.getParameter("country"));
            drug.setSubstance(new Substance(request
                    .getParameter("substance")));
            drug.setPrice(Double.parseDouble(request.getParameter("price")));
            drug.setDrugsCount(Integer.parseInt(
                    request.getParameter("count")));


            switch (request.getParameter("type")) {

                case "Pill":
                    drug.setType(DrugType.PILL);
                    break;
                case "Capsule":
                    drug.setType(DrugType.CAPSULE);
                    break;
                case "Solution":
                    drug.setType(DrugType.SOLUTION);
                    break;
                case "Ointment":
                    drug.setType(DrugType.OINTMENT);
                    break;
                case "Drops":
                    drug.setType(DrugType.DROPS);
                    break;
                case "Syrup":
                    drug.setType(DrugType.SYRUP);
                    break;
                default:
                    break;

            }

            switch (request.getParameter("recipe")) {

                case "0":
                    drug.setRecipe(false);
                    break;
                case "1":
                    drug.setRecipe(true);
                    break;
                default:
                    break;

            }

        } else {
            LOGGER.log(Level.DEBUG, debugString1);
        }

        debugString2 = " Object " + drug + " created.";
        LOGGER.log(Level.DEBUG, debugString2);

        return drug;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drug createEntity(final ResultSet resultSet) {

        final Drug drug = new Drug();
        final String debugString1
                = " Attribute is null in method createEntity(ResultSet).";
        final String debugString2 = " Drug type is incorrect.";
        final String debugString3;

        if (resultSet != null) {

            try {

                drug.setId(resultSet.getInt("id"));
                drug.setDrugName(resultSet.getString("name"));
                drug.setCompany(resultSet.getString("company"));
                drug.setCompanyCountry(resultSet.getString("country"));
                drug.setSubstance(new Substance(resultSet
                        .getString("substance_name")));
                drug.setPrice(resultSet
                        .getBigDecimal("price").doubleValue());
                drug.setDrugsCount(resultSet.getInt("count"));
                drug.setDosage(resultSet.getInt("dosage"));

                switch (resultSet.getInt("is_required_recipe")) {
                    case 0:
                        drug.setRecipe(false);
                        break;
                    case 1:
                        drug.setRecipe(true);
                        break;
                    default:
                        break;
                }

                switch (resultSet.getString("type")) {
                    case "pill":
                        drug.setType(DrugType.PILL);
                        break;
                    case "capsule":
                        drug.setType(DrugType.CAPSULE);
                        break;
                    case "solution":
                        drug.setType(DrugType.SOLUTION);
                        break;
                    case "ointment":
                        drug.setType(DrugType.OINTMENT);
                        break;
                    case "drops":
                        drug.setType(DrugType.DROPS);
                        break;
                    case "syrup":
                        drug.setType(DrugType.SYRUP);
                        break;
                    default:
                        LOGGER.log(Level.DEBUG, debugString2);
                        break;
                }

            } catch (SQLException e) {

                LOGGER.log(Level.ERROR, e.getMessage());
                e.printStackTrace();

            }
        } else {
            LOGGER.log(Level.DEBUG, debugString1);
        }

        debugString3 = " Object " + drug + " created.";
        LOGGER.log(Level.DEBUG, debugString3);

        return drug;

    }

}
