package com.example.kebab4you;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.kebab4You.R;
import com.example.kebab4You.databinding.ActivityMainBinding;
import com.example.kebab4You.databinding.ContentMainBinding;
import com.example.kebab4you.modelo.PedidoEnProceso;
import com.example.kebab4you.modelo.Producto;
import com.example.kebab4you.vista.adaptadores.AdaptadorSwipeTabs;
import com.example.kebab4you.vista.fragmentos.BebidasFragment;
import com.example.kebab4you.vista.fragmentos.MenusFragment;
import com.example.kebab4you.vista.fragmentos.PlatosFragment;
import com.example.kebab4you.vistamodelo.ViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BebidasFragment.tabBebidadInterface, MenusFragment.MenuFragInterface, PlatosFragment.PlatosFragInterface {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ContentMainBinding bindingC;
    private int mesas_id;
    private AdaptadorSwipeTabs mAdaptadorST;
    private ViewModel vistaModeloPedido;
    private List<Producto> productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindingC = binding.contentMain;
        vistaModeloPedido = new ViewModelProvider(this).get(ViewModel.class);
        //Asignamos el id de la mesa
        vistaModeloPedido.setMesaId(getIntent().getIntExtra("id_mesa",0));
        //setSupportActionBar(binding.toolbar);

        PedidoEnProceso.limpiarPedido(getIntent().getIntExtra("id_mesa",0));
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

    @Override
    public void cargarProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public List<Producto> cargarPlatos() {
        return this.productos;
    }

    @Override
    public List<Producto> cargarMenus() {
        return this.productos;
    }
}