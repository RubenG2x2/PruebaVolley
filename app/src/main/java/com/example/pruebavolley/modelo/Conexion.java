package com.example.pruebavolley.modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Conexion  {

    private Gson gson;
    private Retrofit retrofit;
    private static Conexion conexion;

    public static Conexion getConexion(){
        if (conexion == null){
            conexion = new Conexion();
        }
        return conexion;
    }

    private Conexion() {
        this.gson = new GsonBuilder()
                .setLenient()
                .create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000") // el web service que consumira
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
