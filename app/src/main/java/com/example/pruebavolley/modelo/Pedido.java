package com.example.pruebavolley.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Pedido implements Parcelable  {
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


    public int getPartner_id() {
        return partner_id;
    }

    public void setOrder_line(List<LineaPedido> order_line) {
        this.order_line = order_line;
    }


    public static final Parcelable.Creator<Pedido> CREATOR
            = new Parcelable.Creator<Pedido>() {
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };

    private Pedido(Parcel in) {
        this.partner_id = in.readInt();
        this.mesas_id = in.readInt();
        this.date_order = in.readString();
        this.order_line = in.readArrayList(LineaPedido.class.getClassLoader());
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.partner_id);
        dest.writeInt(this.mesas_id);
        dest.writeString(this.date_order);
        dest.writeList(this.order_line);
    }

    public  class LineaPedido  {
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