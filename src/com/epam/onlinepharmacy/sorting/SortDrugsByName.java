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
public class SortDrugsByName implements Specification {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(SortDrugsByName.class);
    }

    /**
     * Public default constructor.
     */
    public SortDrugsByName() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final List<? extends AbstractEntity> entities) {

        entities.sort((final AbstractEntity entity1,
                       final AbstractEntity entity2) -> {

            final Drug drug1 = (Drug) entity1;
            final Drug drug2 = (Drug) entity2;

            return drug1.getDrugName().compareTo(drug2.getDrugName());

        });

        LOGGER.log(Level.DEBUG, AbstractProgramConstants.DRUGS_SORTED_BY_NAME);

    }
}
