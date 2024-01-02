package com.example.proj.model;

public class Outsourced extends Part {

    private String companyName;

    /**
     * Outsourced constructor
     * @param id the id to set
     * @param name the name to set
     * @param price the price to set
     * @param stock the stock to set
     * @param min the min to set
     * @param max the max to set
     * @param companyName the companyName to set
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Outsourced Company name setter
     * @param companyName company name to set
     */
    public void setCompanyName (String companyName) {

        this.companyName = companyName;
    }

    /**
     * Outsourced Company name getter
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }
}