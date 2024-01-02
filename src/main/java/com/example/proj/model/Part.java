package com.example.proj.model;

public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Part constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    protected Part() {
    }

    /**
     * ID getter
     * @return part id
     */
    public int getId() {
        return id;
    }

    /**
     * id Setter
     * @param id int id to be set
     *
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * part name getter
     * @return String part name
     */
    public String getName() {
        return name;
    }

    /**
     * part name setter
     * @param name String name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * part name getter
     * @return double part price
     */
    public double getPrice() {
        return price;
    }

    /**
     * part price setter
     * @param price double price to be set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * stock getter
     * @return int part stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * part stock setter
     * @param stock int stock to be set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * part min getter
     * @return int min
     */
    public int getMin() {
        return min;
    }

    /**
     * part min setter
     * @param min int min to be set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * part max getter
     * @return int max
     */
    public int getMax() {
        return max;
    }

    /**
     * part max setter
     * @param max  int max to be set
     */
    public void setMax(int max) {
        this.max = max;
    }

}