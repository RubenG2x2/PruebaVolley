package com.example.kebab4you.vista.adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kebab4You.R;
import com.example.kebab4You.databinding.ContentProductoRcBinding;
import com.example.kebab4you.modelo.PedidoEnProceso;
import com.example.kebab4you.modelo.Producto;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ProductoAdaptador extends RecyclerView.Adapter<ProductoAdaptador.ProductoVh> {

    private List<Producto> listaProductos;
    private int posicion;
    private int reiniciarCantidades;

    public ProductoAdaptador(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
        this.posicion = -1;
        this.reiniciarCantidades = 0;
    }


    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ProductoVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_producto_rc, parent, false);
        return new ProductoVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVh holder, int position) {
        holder.setItem(listaProductos.get(position));
    }


    public void setReiniciarCantidades(int reiniciarCantidades) {
        this.reiniciarCantidades = reiniciarCantidades;
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ProductoVh extends RecyclerView.ViewHolder  {

        private final ContentProductoRcBinding binding;

        public ProductoVh(@NonNull View itemView) {
            super(itemView);
            binding = ContentProductoRcBinding.bind(itemView);
            binding.iconCarro.setOnClickListener(iconCarritoOnClickListener);
            binding.btAumentar.setOnClickListener(btAumentarOnCLickListener);
            binding.btDisminuir.setOnClickListener(btDisminuirOnCLickListener);
        }

        private void setItem(@NonNull Producto producto) {
            if (!producto.getImage_1920().equals("false")) {
                byte[] imagen = Base64.decode(producto.getImage_1920(), Base64.DEFAULT);
                Bitmap imagenBitmap = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);
                binding.imgProducto.setImageBitmap(imagenBitmap);
            }
            binding.tvId.setText(String.valueOf(producto.getId()));
            binding.tvNombrePro.setText(producto.getName());
            binding.tvPrecioPro.setText(String.valueOf(producto.getList_price() + "€"));
            if (reiniciarCantidades > 0) {
                binding.etCantidad.setText("1");
            }
        }


        private View.OnClickListener iconCarritoOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(binding.getRoot(), "Producto añadido", Snackbar.LENGTH_LONG).show();

                PedidoEnProceso.getPedido().añadirLinea(Integer.parseInt(binding.tvId.getText().toString()),
                        Integer.parseInt(binding.etCantidad.getText().toString()),
                        Float.parseFloat(binding.tvPrecioPro.getText().toString().substring(0, binding.tvPrecioPro.getText().toString().length() - 1)));

                binding.iconCarro.playAnimation();
            }
        };
        private View.OnClickListener btAumentarOnCLickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cantidadModificada = Integer.valueOf(binding.etCantidad.getText().toString()) + 1;
                binding.etCantidad.setText(String.valueOf(cantidadModificada));
            }
        };
        private View.OnClickListener btDisminuirOnCLickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cantidadModificada = Integer.valueOf(binding.etCantidad.getText().toString()) - 1;
                if (cantidadModificada < 1) {
                    Snackbar.make(binding.getRoot(), R.string.cantidad_minima, Snackbar.LENGTH_SHORT).show();
                } else {
                    binding.etCantidad.setText(String.valueOf(cantidadModificada));
                }
            }
        };

    }
}
