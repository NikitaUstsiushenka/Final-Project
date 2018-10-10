package com.epam.onlinepharmacy.sorting;

import com.epam.onlinepharmacy.entity.AbstractEntity;
import com.epam.onlinepharmacy.entity.Drug;
import com.epam.onlinepharmacy.main.AbstractProgramConstants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * This class implements interface "Specification".
 *
 * @author Nikita
 * @version 1.0
 * @since 26.09.2018
 */
public class SortDrugsByPrice implements Specification {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(SortDrugsByPrice.class);
    }

    /**
     * Public default constructor.
     */
    public SortDrugsByPrice() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(List<? extends AbstractEntity> entities) {

        entities.sort((final AbstractEntity entity1,
                       final AbstractEntity entity2) -> {

            final Drug drug1 = (Drug) entity1;
            final Drug drug2 = (Drug) entity2;

            return Double.compare(drug1.getPrice(), drug2.getPrice());

        });

        LOGGER.log(Level.DEBUG,
                AbstractProgramConstants.DRUGS_SORTED_BY_PRICE);

    }
}
