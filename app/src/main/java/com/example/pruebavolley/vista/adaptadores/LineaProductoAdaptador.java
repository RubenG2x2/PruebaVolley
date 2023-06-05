package com.example.pruebavolley.vista.adaptadores;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebavolley.PedidoActivity;
import com.example.pruebavolley.R;
import com.example.pruebavolley.databinding.ContentLineaProductoRcBinding;
import com.example.pruebavolley.modelo.Conexion;
import com.example.pruebavolley.modelo.Pedido;
import com.example.pruebavolley.modelo.PedidoEnProceso;
import com.example.pruebavolley.modelo.Producto;
import com.example.pruebavolley.vista.interfaz.ConexionInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LineaProductoAdaptador extends RecyclerView.Adapter<LineaProductoAdaptador.LineaVh> {

    private int posicion;

    private List<Producto> productos;

    public LineaProductoAdaptador(Pedido pedido,List<Producto> productos) {

        posicion = -1;
        this.productos = productos;

    }

    @NonNull
    @Override
    public LineaVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_linea_producto_rc,parent,false);
        return new LineaProductoAdaptador.LineaVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LineaVh holder, int position) {
        holder.setItem(PedidoEnProceso.getPedido().getOrder_line().get(position));
        holder.itemView.setBackgroundColor((posicion == position)
                ? ContextCompat.getColor(holder.itemView.getContext(), R.color.purple_200)
                : Color.TRANSPARENT);
    }


    @Override
    public int getItemCount() {
        return PedidoEnProceso.getPedido().getOrder_line().size();
    }

    public class LineaVh extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int posicion;
        private final ContentLineaProductoRcBinding binding;

        public LineaVh(@NonNull View itemView) {
            super(itemView);
            binding = ContentLineaProductoRcBinding.bind(itemView);
            binding.btEliminarLineaProducto.setOnClickListener(btEliminarLineaProductoOnClickListener);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getLayoutPosition();
            notifyItemChanged(posicion);
            posicion = (posicion == pos) ? -1 : pos;
            notifyItemChanged(posicion);
        }
        private void setItem(@NonNull Pedido.LineaPedido lineaPedido){
            binding.tvSubTotal.setText(String.valueOf(lineaPedido.getSubtotal()));
            binding.tvPrecioPro.setText(String.valueOf(lineaPedido.getPrice_unit()));
            binding.tvCantidadPro.setText(String.valueOf(lineaPedido.getQuantity()));
            for (Producto p : productos) {
                if (p.getId() == lineaPedido.getProduct_id()){
                    binding.tvNombrePro.setText(p.getName());
                }
            }
            binding.tvIdLinea.setText(String.valueOf(lineaPedido.getProduct_id()));
        }
        private View.OnClickListener btEliminarLineaProductoOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Pedido.LineaPedido l: PedidoEnProceso.getPedido().getOrder_line()) {
                    if (l.getProduct_id() == Integer.parseInt(binding.tvIdLinea.getText().toString()) && l.getQuantity() == Integer.parseInt(binding.tvCantidadPro.getText().toString())){
                        PedidoEnProceso.getPedido().getOrder_line().remove(l);
                        notifyDataSetChanged();
                        ((PedidoActivity) itemView.getContext()).actualizarTotalPedido();
                        return;
                    }
                }

            }
        };
    }
}
