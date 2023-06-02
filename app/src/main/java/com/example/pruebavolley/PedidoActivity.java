package com.example.pruebavolley;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pruebavolley.databinding.ActivityPedidoBinding;
import com.example.pruebavolley.databinding.ContentPedidoBinding;
import com.example.pruebavolley.modelo.Conexion;
import com.example.pruebavolley.modelo.PedidoEnProceso;
import com.example.pruebavolley.modelo.Producto;
import com.example.pruebavolley.modelo.Respuesta;
import com.example.pruebavolley.vista.adaptadores.LineaProductoAdaptador;
import com.example.pruebavolley.vista.dialogos.DialogConfirmacion;
import com.example.pruebavolley.vista.interfaz.ConexionInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PedidoActivity extends AppCompatActivity implements DialogConfirmacion.DlgConfirmacionListener {

    private AppBarConfiguration appBarConfiguration;
    private static final String tagConfirmacion = "tagConfirmacion";
    private ActivityPedidoBinding binding;
    private ContentPedidoBinding bindingC;
    private List<Producto> productos;
    private LineaProductoAdaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPedidoBinding.inflate(getLayoutInflater());
        bindingC = binding.contentPedido;
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_peticion_bd);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        ConexionInterface obtenerProductos = Conexion.getConexion().getRetrofit().create(ConexionInterface.class);
        obtenerProductos.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                productos = response.body();
                adaptador = new LineaProductoAdaptador(PedidoEnProceso.getPedido(),productos);
                cargarRc();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                dialog.dismiss();
            }
        });

        bindingC.btEnviarrPedido.setOnClickListener(btEnviarPedidoOnCLickListener);
    }
    private void cargarRc(){
        bindingC.recyclerViewLineaPedido.setLayoutManager(new LinearLayoutManager(this));
        bindingC.recyclerViewLineaPedido.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        bindingC.recyclerViewLineaPedido.setHasFixedSize(true);
        bindingC.recyclerViewLineaPedido.setAdapter(adaptador);
        bindingC.tvTotal.setText(String.valueOf(PedidoEnProceso.getPedido().calcularTotal()));
    }

    private View.OnClickListener btEnviarPedidoOnCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (PedidoEnProceso.getPedido().getOrder_line().size()<1){
                Toast.makeText(getApplicationContext(), R.string.pedido_vacio, Toast.LENGTH_SHORT).show();
            } else {
                mostrarDialog();
            }
        }
    };
    private void mostrarDialog(){
        DialogConfirmacion dialog = new DialogConfirmacion();
        dialog.setTitulo(R.string.app_name);
        dialog.setMensaje(R.string.confirmar_pedido);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(),tagConfirmacion);
    }
    @Override
    public void onDlgConfirmacionPositiveClick(DialogFragment dialog) {
        bindingC.btEnviarrPedido.setActivated(false);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int mesaId = PedidoEnProceso.getPedido().getMesas_id();

        PedidoEnProceso.getPedido().setDate_order(dateFormat.format(date));

        ConexionInterface pedidoService = Conexion.getConexion().getRetrofit().create(ConexionInterface.class);
        Call<Respuesta> call = pedidoService.crearPedido(PedidoEnProceso.getPedido());
        call.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.isSuccessful()) {
                    Respuesta respuesta = response.body();
                    if (respuesta != null) {
                        String mensaje = respuesta.getMensaje();
                        int idPedido = respuesta.getId();
                        Toast.makeText(getApplicationContext(), mensaje + ", ID: " + idPedido, Toast.LENGTH_SHORT).show();
                        PedidoEnProceso.limpiarPedido(mesaId);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
            }
        });

    }

    @Override
    public void onDlgConfirmacionNegativeClick(DialogFragment dialog) {

    }

}