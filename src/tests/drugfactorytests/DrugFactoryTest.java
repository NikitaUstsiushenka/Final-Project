package tests.drugfactorytests;

import com.epam.onlinepharmacy.database.pool.ConnectionPool;
import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.entity.Substance;
import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.factory.DrugFactory;
import com.epam.onlinepharmacy.factory.EntityFactory;
import com.epam.onlinepharmacy.main.AbstractSQLQuery;
import com.epam.onlinepharmacy.main.DrugType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Test class for class "DrugFactory".
 *
 * @author Nikita
 * @version 1.0
 * @since 21.10.2018
 */
public class DrugFactoryTest {

    /**
     * Value of the object EntityFactory.
     */
    private static EntityFactory factory;

    /**
     * Value of the first error message.
     */
    private static String[] errorString;

    /**
     * {@inheritDoc}
     */
    @BeforeClass
    public static void initialize() {

        factory = new DrugFactory();
        errorString = new String[]
                {"Error in method createEntity() from class DrugFactory.",
                        "Error in method createEntity(ResultSet) "
                                + "from class DrugFactory."};

    }

    /**
     * Test for method createEntity().
     * <p>
     * {@inheritDoc}
     */
    @Test
    public void testCreateEntity1() {

        Assert.assertEquals(new Drug(), ((DrugFactory) factory).createEntity(),
                errorString[0]);

    }

    /**
     * Test for method createEntity(HttpServletRequest).
     * <p>
     * {@inheritDoc}
     */
    @Test
    public void testCreateEntity2() throws ApplicationException, SQLException {

        final Connection connection;
        final PreparedStatement statement;
        final ResultSet resultSet;
        final Drug checkDrug = new Drug();

        connection = ConnectionPool.getInstance().getConnection();
        statement = connection.prepareStatement(
                AbstractSQLQuery.SELECT_DRUG_BY_ID);
        statement.setInt(1, 1);
        resultSet = statement.executeQuery();
        resultSet.next();

        checkDrug.setId(1);
        checkDrug.setSubstance(new Substance("Fenibut"));
        checkDrug.setDrugName("Fenibut");
        checkDrug.setCompany("Belmeddrugs");
        checkDrug.setCompanyCountry("Belarus");
        checkDrug.setPrice(13.0);
        checkDrug.setDrugsCount(3065);
        checkDrug.setDosage(250);
        checkDrug.setType(DrugType.PILL);
        checkDrug.setRecipe(false);

        Assert.assertEquals(checkDrug, factory.createEntity(resultSet),
                errorString[1]);

        connection.close();

    }

}