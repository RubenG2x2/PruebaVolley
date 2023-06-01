package com.example.pruebavolley.vista.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pruebavolley.R;

public class PedidoDetalleFragment extends Fragment {


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PedidoDetalleFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PedidoDetalleFragment newInstance(String param1, String param2) {
        PedidoDetalleFragment fragment = new PedidoDetalleFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedido_detalle, container, false);
    }
}