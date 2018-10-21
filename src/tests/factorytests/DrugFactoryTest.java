package tests.factorytests;

import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.factory.DrugFactory;
import com.epam.onlinepharmacy.factory.EntityFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

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
    private static String errorString1;

    /**
     * {@inheritDoc}
     */
    @BeforeClass
    public static void initialize() {

        factory = new DrugFactory();
        errorString1
                = "Error in method createEntity() from class DrugFactory.";

    }

    /**
     * Test for method createEntity().
     * <p>
     * {@inheritDoc}
     */
    @Test
    public void testCreateEntity1() {

        Assert.assertEquals(new Drug(), factory.createEntity(), errorString1);

    }

    /**
     * Test for method createEntity(HttpServletRequest).
     * <p>
     * {@inheritDoc}
     */
    @Test
    public void testCreateEntity2() {


    }

}