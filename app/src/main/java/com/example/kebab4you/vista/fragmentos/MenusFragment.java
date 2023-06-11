package com.example.kebab4you.vista.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.kebab4You.R;
import com.example.kebab4You.databinding.FragmentTab2Binding;
import com.example.kebab4you.PedidoActivity;

import com.example.kebab4you.modelo.Producto;
import com.example.kebab4you.vista.adaptadores.ProductoAdaptador;

import java.util.ArrayList;
import java.util.List;

public class MenusFragment extends Fragment {

    private FragmentTab2Binding binding;
    private MenuFragInterface mCallback;
    private int mPos;
    private ProductoAdaptador adaptadorProductos;
    private List<Producto> listaProductos;

    public interface MenuFragInterface {
        List<Producto> cargarMenus();
    }
    @Override
    public void onResume() {
        super.onResume();
        adaptadorProductos.setReiniciarCantidades(1);
        adaptadorProductos.notifyDataSetChanged();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuFragInterface) {
            mCallback = (MenuFragInterface) context;
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

        for (Producto p : mCallback.cargarMenus()) {
            if (p.getCateg_id()[1].equals(getString(R.string.filtro_menu))) {
                listaProductos.add(p);
            }
        }
        adaptadorProductos.setListaProductos(listaProductos);
        adaptadorProductos.notifyDataSetChanged();


        // Listeners
        binding.btTerminarPedido.setOnClickListener(btTerminarPedidoOnClickListener);

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
