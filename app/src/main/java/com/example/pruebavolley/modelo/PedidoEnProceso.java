package com.example.pruebavolley.modelo;

public class PedidoEnProceso {

    private static Pedido pedido;

    public static Pedido getPedido() {
        if (pedido==null){
            pedido = new Pedido();
        }
        return pedido;
    }
    public static void limpiarPedido(int mesas_id){
        pedido = new Pedido();
        pedido.setMesas_id(mesas_id);
    }
}
