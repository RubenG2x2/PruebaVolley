package com.example.pruebavolley;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.pruebavolley.databinding.ActivityMainBinding;
import com.example.pruebavolley.databinding.ContentMainBinding;
import com.example.pruebavolley.vista.adaptadores.AdaptadorSwipeTabs;
import com.example.pruebavolley.vista.fragmentos.BebidasFragment;
import com.example.pruebavolley.vista.fragmentos.MenusFragment;
import com.example.pruebavolley.vista.fragmentos.PlatosFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import android.view.Menu;
import android.view.MenuItem;
import retrofit2.Call;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity implements BebidasFragment.Tab1FragInterface, MenusFragment.Tab2FragInterface, PlatosFragment.Tab3FragInterface {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ContentMainBinding bindingC;
    private int mesas_id;
    private AdaptadorSwipeTabs mAdaptadorST;

    @Override
    public void onAccionTab1Frag() {

    }

    @Override
    public void onAccionTab2Frag() {

    }

    @Override
    public void onAccionTab3Frag() {

    }

    interface Request {
        @GET("/contactos")
        Call<Odoo> getUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mesas_id = getIntent().getIntExtra("id_mesa",0);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindingC = binding.contentMain;

        setSupportActionBar(binding.toolbar);


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