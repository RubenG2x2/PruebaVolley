package com.example.pruebavolley.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.pruebavolley.modelo.Pedido;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private Pedido pedido;
    private int mesaId;

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
}
