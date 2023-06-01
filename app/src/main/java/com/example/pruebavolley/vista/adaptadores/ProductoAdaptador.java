package com.example.pruebavolley.vista.adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebavolley.R;
import com.example.pruebavolley.databinding.ContentProductoRcBinding;
import com.example.pruebavolley.modelo.Pedido;
import com.example.pruebavolley.modelo.Producto;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class ProductoAdaptador extends RecyclerView.Adapter<ProductoAdaptador.ProductoVh>{

    private List<Producto> listaProductos;
    private int posicion;

    private String categoriaFiltro;
    private Pedido pedido;
    public ProductoAdaptador(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
        this.categoriaFiltro = categoriaFiltro;
        this.posicion = -1;
        this.pedido = new Pedido();
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getCategoriaFiltro() {
        return categoriaFiltro;
    }

    public void setCategoriaFiltro(String categoriaFiltro) {
        this.categoriaFiltro = categoriaFiltro;
    }

    @NonNull
    @Override
    public ProductoVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_producto_rc,parent,false);
        return new ProductoVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVh holder, int position) {
        holder.setItem(listaProductos.get(position));
        holder.itemView.setBackgroundColor((posicion == position)
                ? ContextCompat.getColor(holder.itemView.getContext(), R.color.purple_200)
                : Color.TRANSPARENT);
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ProductoVh extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ContentProductoRcBinding binding;
        public ProductoVh(@NonNull View itemView) {
            super(itemView);
            binding = ContentProductoRcBinding.bind(itemView);
            binding.iconCarro.setOnClickListener(iconCarritoOnClickListener);
            binding.btAumentar.setOnClickListener(btAumentarOnCLickListener);
            binding.btDisminuir.setOnClickListener(btDisminuirOnCLickListener);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getLayoutPosition();
            notifyItemChanged(posicion);
            posicion = (posicion == pos) ? -1 : pos;
            notifyItemChanged(posicion);
        }

        private void setItem(@NonNull Producto producto){
            if (!producto.getImage_1920().equals("false")) {
                byte[] imagen = Base64.decode(producto.getImage_1920(), Base64.DEFAULT);
                Bitmap imagenBitmap = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);
                binding.imgProducto.setImageBitmap(imagenBitmap);
            }
            binding.tvId.setText(String.valueOf(producto.getId()));
            binding.tvNombrePro.setText(producto.getName());
            binding.tvPrecioPro.setText(String.valueOf(producto.getList_price() + "€"));

        }

        private View.OnClickListener iconCarritoOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(binding.getRoot(),binding.tvNombrePro.getText().toString(),Snackbar.LENGTH_LONG).show();

                pedido.añadirLinea(Integer.parseInt(binding.tvId.getText().toString()),
                        Integer.parseInt(binding.etCantidad.getText().toString()),
                        Float.parseFloat(binding.tvPrecioPro.getText().toString().substring(0,binding.tvPrecioPro.getText().toString().length()-1)));

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
                if (cantidadModificada < 1){
                    Snackbar.make(binding.getRoot(), R.string.cantidad_minima,Snackbar.LENGTH_LONG).show();
                } else {
                    binding.etCantidad.setText(String.valueOf(cantidadModificada));
                }
            }
        };

    }
}
