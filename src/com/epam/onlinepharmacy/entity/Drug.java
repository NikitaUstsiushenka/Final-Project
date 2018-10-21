package com.epam.onlinepharmacy.entity;

import com.epam.onlinepharmacy.main.DrugType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class stores information about the drug.
 *
 * @author Nikita
 * @version 1.0
 * @since 15.09.2018
 */
public final class Drug extends AbstractEntity {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    /**
     * Value of the drug name.
     */
    private String drugName;

    /**
     * Value of the drug company.
     */
    private String company;

    /**
     * Value of the company country.
     */
    private String companyCountry;

    /**
     * Value of the substance.
     */
    private Substance substance;

    /**
     * Value of the drug price.
     */
    private double price;

    /**
     * Value of the drugs count.
     */
    private int drugsCount;

    /**
     * Value of the drug dosage.
     */
    private int dosage;

    /**
     * Value of the drug type.
     */
    private DrugType type;

    /**
     * Value of the required recipe.
     */
    private boolean recipe;

    static {
        LOGGER = LogManager.getLogger(Drug.class);
    }

    /**
     * Public default constructor.
     */
    public Drug() {

        super();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return getClass().getName() + "@entity:" + super.toString()
                + "@drugName:" + this.drugName + "@company:"
                + this.company + "@companyCountry:" + this.companyCountry
                + "@substance:" + this.substance + "@price:" + this.price
                + "@drugsCount:" + this.drugsCount + "@dosage:" + this.dosage
                + "@type:" + this.type + "@recipe:" + this.recipe;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return super.hashCode() + (this.drugName != null
                ? this.drugName.hashCode() : 0) + (this.company != null
                ? this.company.hashCode() : 0) + (this.companyCountry != null
                ? this.companyCountry.hashCode() : 0)
                + (this.substance != null ? this.substance.hashCode() : 0)
                + (int) this.price + this.drugsCount + this.dosage
                + (this.type != null ? this.type.hashCode() : 0);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {

        final boolean result;
        final Drug drug;

        if (object != null) {

            drug = (Drug) object;

            if (this == object) {
                result = true;
            } else if (getClass() != object.getClass()) {
                result = false;
            } else {
                result = (getId().equals(drug.getId())
                        && (this.drugName == null || this.drugName.equals(drug
                        .getDrugName())) && (this.company == null
                        || this.company.equals(drug.getCompany()))
                        && (this.companyCountry == null || this.companyCountry
                        .equals(drug.getCompanyCountry())) && (this.substance
                        == null || this.substance.equals(drug.getSubstance()))
                        && this.dosage == drug.getDosage() && this.price
                        == drug.getPrice() && this.drugsCount
                        == drug.getDrugsCount()
                        && this.type == drug.getType());
            }

        } else {
            result = false;
        }

        return result;

    }

    /**
     * This method return value of the drug name.
     *
     * @return value of the drug name
     */
    public String getDrugName() {

        final String result;

        if (this.drugName != null) {
            result = this.drugName;
        } else {
            result = "";
        }

        return result;

    }

    /**
     * This method set new value of the drug name.
     *
     * @param newDrugName new value of the drug name
     */
    public void setDrugName(final String newDrugName) {

        final String debugString
                = " Attribute is null in method setDrugName(String).";

        if (newDrugName != null) {
            this.drugName = newDrugName;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return value of the drug company.
     *
     * @return value of the drug company
     */
    public String getCompany() {

        final String result;

        if (this.company != null) {
            result = this.company;
        } else {
            result = "";
        }

        return result;

    }

    /**
     * This method set new value of the drug company.
     *
     * @param newCompany new value of the drug company
     */
    public void setCompany(final String newCompany) {

        final String debugString
                = " Attribute is null in method setCompany(String).";

        if (newCompany != null) {
            this.company = newCompany;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return value of the company country.
     *
     * @return value of the company country
     */
    public String getCompanyCountry() {

        final String result;

        if (this.companyCountry != null) {
            result = this.companyCountry;
        } else {
            result = "";
        }

        return result;

    }

    /**
     * This method set new value of the company country.
     *
     * @param newCompanyCountry new value of the company country
     */
    public void setCompanyCountry(final String newCompanyCountry) {

        final String debugString
                = " Attribute is null in method setCompanyCountry(String).";

        if (newCompanyCountry != null) {
            this.companyCountry = newCompanyCountry;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return value of the object Substance.
     *
     * @return value of the object Substance
     */
    public Substance getSubstance() {

        final Substance result;

        if (this.substance != null) {
            result = this.substance;
        } else {
            result = new Substance();
        }

        return result;

    }

    /**
     * This method set new value of the object Substance.
     *
     * @param newSubstance new value of the object Substance
     */
    public void setSubstance(final Substance newSubstance) {

        final String debugString
                = " Attribute is null in method setSubstance(Substance)";

        if (newSubstance != null) {
            this.substance = newSubstance;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return value of the drug price.
     *
     * @return value of the drug price
     */
    public double getPrice() {

        return this.price;

    }

    /**
     * This method set new value of the drug price.
     *
     * @param newPrice new value of the drug price
     */
    public void setPrice(final double newPrice) {

        this.price = newPrice;

    }

    /**
     * This method return value of the drugs count.
     *
     * @return value of the drugs count
     */
    public int getDrugsCount() {

        return this.drugsCount;

    }

    /**
     * This method set new value of the drugs count.
     *
     * @param newDrugsCount new value of the drugs count
     */
    public void setDrugsCount(final int newDrugsCount) {

        this.drugsCount = newDrugsCount;

    }

    /**
     * This method return value of the drug dosage.
     *
     * @return value of the drug dosage
     */
    public int getDosage() {

        return this.dosage;

    }

    /**
     * This method set new value of the drug dosage.
     *
     * @param newDosage new value of the drug dosage
     */
    public void setDosage(final int newDosage) {

        this.dosage = newDosage;

    }

    /**
     * This method return value of the drug type.
     *
     * @return value of the drug type
     */
    public DrugType getType() {

        return this.type;

    }

    /**
     * This method set new value of the drug type.
     *
     * @param newType new value of the drug type
     */
    public void setType(final DrugType newType) {

        final String debugString
                = " Attribute is null in method setType(DrugType).";

        if (newType != null) {
            this.type = newType;
        } else {
            LOGGER.log(Level.DEBUG, debugString);
        }

    }

    /**
     * This method return value of the required recipe.
     *
     * @return value of the required recipe
     */
    public boolean isRequiredRecipe() {

        return this.recipe;

    }

    /**
     * This method set new value of the required recipe.
     *
     * @param newRecipe new value of the required recipe
     */
    public void setRecipe(final boolean newRecipe) {

        this.recipe = newRecipe;

    }

}
