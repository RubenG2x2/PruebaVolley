package com.example.pruebavolley.modelo.interfaz;

import com.example.pruebavolley.modelo.Mesa;
import com.example.pruebavolley.modelo.Pedido;
import com.example.pruebavolley.modelo.Producto;
import com.example.pruebavolley.modelo.Respuesta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ConexionInterface {
    @GET("/mesas")
    Call<List<Mesa>> getMesas();
    @POST("/create_order")
    Call<Respuesta> crearPedido(@Body Pedido order);

    @GET("/pedidos")
    Call<List<Pedido>> getPedidos();

    @GET("/productos")
    Call<List<Producto>> getProductos();
}
