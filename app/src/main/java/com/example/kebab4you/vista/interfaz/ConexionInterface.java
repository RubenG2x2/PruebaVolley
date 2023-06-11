package com.example.kebab4you.vista.interfaz;

import com.example.kebab4you.modelo.Mesa;
import com.example.kebab4you.modelo.Pedido;
import com.example.kebab4you.modelo.Producto;
import com.example.kebab4you.modelo.Respuesta;

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
