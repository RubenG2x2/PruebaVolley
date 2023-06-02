package com.example.pruebavolley.vista.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.pruebavolley.PedidoActivity;
import com.example.pruebavolley.R;
import com.example.pruebavolley.databinding.FragmentTab2Binding;
import com.example.pruebavolley.modelo.Conexion;
import com.example.pruebavolley.modelo.Producto;
import com.example.pruebavolley.modelo.Respuesta;
import com.example.pruebavolley.vista.interfaz.ConexionInterface;
import com.example.pruebavolley.vista.adaptadores.ProductoAdaptador;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenusFragment extends Fragment {

    private FragmentTab2Binding binding;
    private Tab2FragInterface mCallback;
    private int mPos;
    private ProductoAdaptador adaptadorProductos;
    private List<Producto> listaProductos;

    public interface Tab2FragInterface {
        void onAccionTab2Frag();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Tab2FragInterface) {
            mCallback = (Tab2FragInterface) context;
        } else {
            throw new RuntimeException(context + " must implement Tab2FragInterface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPos = getArguments().getInt("pos");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTab2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inits
        listaProductos = new ArrayList<>();
        adaptadorProductos = new ProductoAdaptador(listaProductos);
        binding.rcMenus.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcMenus.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.rcMenus.setHasFixedSize(true);
        binding.rcMenus.setAdapter(adaptadorProductos);
        binding.btTerminarPedido.setOnClickListener(btTerminarPedidoOnClickListener);

        //Dialog de carga
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(R.layout.dialog_peticion_bd);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        ConexionInterface obtenerProductos = Conexion.getConexion().getRetrofit().create(ConexionInterface.class);
        obtenerProductos.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                try {
                    for (Producto p : response.body()) {
                        if (p.getCateg_id()[1].equals(getString(R.string.filtro_menu))) {
                            listaProductos.add(p);
                        }
                    }
                    adaptadorProductos.setListaProductos(listaProductos);
                    adaptadorProductos.notifyDataSetChanged();
                } catch (Exception e) {
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(getContext(), getText(R.string.error_conexion), Toast.LENGTH_SHORT).show();
            }
        });

        // Listeners

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    private View.OnClickListener btTerminarPedidoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), PedidoActivity.class);
            startActivity(intent);
        }
    };
}
