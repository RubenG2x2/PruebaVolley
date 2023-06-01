package com.example.pruebavolley.modelo;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final int partner_id;
    private Integer mesas_id;
    private String date_order;
    private List<LineaPedido> order_line;
    public Pedido() {
        this.partner_id = 1;
        order_line = new ArrayList<>();
    }

    public Pedido(Integer mesasId, List<LineaPedido> orderLine) {
        this.partner_id = 1;
        this.mesas_id = mesasId;
        this.order_line = orderLine;

    }

    public Integer getMesas_id() {
        return mesas_id;
    }

    public void setMesas_id(Integer mesas_id) {
        this.mesas_id = mesas_id;
    }

    public String getDate_order() {
        return date_order;
    }

    public List<LineaPedido> getOrder_line() {
        return order_line;
    }

    public void setDate_order(String date_order) {
        this.date_order = date_order;
    }

    public void añadirLinea(int product_id, int quantity, float price_unit){
        order_line.add(new LineaPedido(product_id,quantity,price_unit));
    }

    public float calcularTotal(){
        float precioTotal = 0;
        for (LineaPedido linea :
                this.order_line) {
            precioTotal += linea.getSubtotal();
        }
        return precioTotal;
    }
    public class LineaPedido {
        private int product_id;
        private int quantity;
        private float price_unit;

        public LineaPedido() {
        }

        public LineaPedido(int productId, int productUomQty, float price_unit) {
            this.product_id = productId;
            this.quantity = productUomQty;
            this.price_unit = price_unit;
        }

        public int getProduct_id() {
            return product_id;
        }

        public int getQuantity() {
            return quantity;
        }

        public float getPrice_unit() {
            return price_unit;
        }

        public float getSubtotal(){
            return quantity * price_unit;
        }
    }

}