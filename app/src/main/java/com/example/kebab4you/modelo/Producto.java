package com.example.kebab4you.modelo;

public class Producto {
    private int id;
    private String image_1920;
    private String name;
    private float list_price;
    private String[] categ_id;


    public Producto(int id, String image_1920, String name, float list_price) {
        this.id = id;
        this.image_1920 = image_1920;
        this.name = name;
        this.list_price = list_price;
    }

    public Producto() {
    }

    public String getImage_1920() {
        return image_1920;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getList_price() {
        return list_price;
    }

    public String[] getCateg_id() {
        return categ_id;
    }

    public void setCateg_id(String[] categ_id) {
        this.categ_id = categ_id;
    }

    public void setImage_1920(String image_1920) {
        this.image_1920 = image_1920;
    }

    public void setList_price(float list_price) {
        this.list_price = list_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
