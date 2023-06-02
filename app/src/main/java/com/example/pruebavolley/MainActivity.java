package com.example.pruebavolley;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.pruebavolley.databinding.ActivityMainBinding;
import com.example.pruebavolley.databinding.ContentMainBinding;
import com.example.pruebavolley.modelo.Conexion;
import com.example.pruebavolley.modelo.Pedido;
import com.example.pruebavolley.modelo.PedidoEnProceso;
import com.example.pruebavolley.modelo.Producto;
import com.example.pruebavolley.vista.adaptadores.AdaptadorSwipeTabs;
import com.example.pruebavolley.vista.fragmentos.BebidasFragment;
import com.example.pruebavolley.vista.fragmentos.MenusFragment;
import com.example.pruebavolley.vista.fragmentos.PlatosFragment;
import com.example.pruebavolley.vista.interfaz.ConexionInterface;
import com.example.pruebavolley.vistamodelo.ViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity implements BebidasFragment.tabBebidadInterface, MenusFragment.Tab2FragInterface, PlatosFragment.Tab3FragInterface {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ContentMainBinding bindingC;
    private int mesas_id;
    private AdaptadorSwipeTabs mAdaptadorST;
    private ViewModel vistaModeloPedido;

    @Override
    public void onAccionTab2Frag() {

    }

    @Override
    public void onAccionTab3Frag() {

    }

    @Override
    public List<Producto> addLineaBebida() {

        if (vistaModeloPedido != null){
            return vistaModeloPedido.getProductos();
        } else {
            return null;
        }
    }

    @Override
    public void consultarProducto() {
        ConexionInterface obtenerProductos = Conexion.getConexion().getRetrofit().create(ConexionInterface.class);
        obtenerProductos.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                try {
                    vistaModeloPedido.getProductos().addAll(response.body());

                } catch (Exception e) {
                }

            }
            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                // Toast.makeText(getContext(), getText(R.string.error_conexion), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindingC = binding.contentMain;
        vistaModeloPedido = new ViewModelProvider(this).get(ViewModel.class);
        //Asignamos el id de la mesa
        vistaModeloPedido.setMesaId(getIntent().getIntExtra("id_mesa",0));
        setSupportActionBar(binding.toolbar);

        PedidoEnProceso.getPedido().setMesas_id(getIntent().getIntExtra("id_mesa",0));
        //INIST
        mAdaptadorST = new AdaptadorSwipeTabs(this);
        bindingC.viewPager.setAdapter(mAdaptadorST);



        new TabLayoutMediator(bindingC.tabLayaout, bindingC.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            String[] tabNames = {
                    getString(R.string.tab_bebida),
                    getString(R.string.tab_menu),
                    getString(R.string.tab_platos)
            };

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNames[position]);
            }
        }).attach();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
        return true;
    }

    public int getMesas_id() {
        return mesas_id;
    }

    public void setMesas_id(int mesas_id) {
        this.mesas_id = mesas_id;
    }
}