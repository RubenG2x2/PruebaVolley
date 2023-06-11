package com.example.kebab4you.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.kebab4you.modelo.Pedido;
import com.example.kebab4you.modelo.Producto;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private Pedido pedido;
    private int mesaId;
    private List<Producto> productos;
    public ViewModel(@NonNull Application application) {
        super(application);
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getMesaId() {
        return mesaId;
    }

    public void setMesaId(int mesaId) {
        this.mesaId = mesaId;
    }

    public List<Pedido.LineaPedido> getLineasPedido(){
        return pedido.getOrder_line();
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
