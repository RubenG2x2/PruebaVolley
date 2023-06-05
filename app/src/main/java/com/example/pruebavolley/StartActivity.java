package com.example.pruebavolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.pruebavolley.databinding.ActivityStartBinding;
import com.example.pruebavolley.modelo.Conexion;
import com.example.pruebavolley.modelo.Mesa;
import com.example.pruebavolley.vista.interfaz.ConexionInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {
    private ActivityStartBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<String> mesas_list = new ArrayList<>();
        binding.buttonNext.setVisibility(View.INVISIBLE);
        binding.spinner.setVisibility(View.INVISIBLE);

        binding.buttonNext.setOnClickListener(button_next_onClickListener);

        ConexionInterface obtenerMesas = Conexion.getConexion().getRetrofit().create(ConexionInterface.class);
        obtenerMesas.getMesas().enqueue(new Callback<List<Mesa>>() {
            @Override
            public void onResponse(Call<List<Mesa>> call, Response<List<Mesa>> response) {

                for (Mesa mesa : response.body()) {
                    mesas_list.add(mesa.getId() + "-" +mesa.getName());
                }
                ArrayAdapter<String> adp = new ArrayAdapter<> (StartActivity.this,android.R.layout.simple_spinner_dropdown_item,mesas_list);
                binding.spinner.setAdapter(adp);
                binding.buttonNext.setVisibility(View.VISIBLE);
                binding.spinner.setVisibility(View.VISIBLE);
                binding.animationView.cancelAnimation();
                binding.buttonNext.cancelAnimation();
            }

            @Override
            public void onFailure(Call<List<Mesa>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getText(R.string.error_conexion), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private final View.OnClickListener button_next_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (binding.spinner.getSelectedItem() != null) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("id_mesa", Integer.parseInt(binding.spinner.getSelectedItem().toString().substring(0, binding.spinner.getSelectedItem().toString().indexOf('-'))));
                startActivity(intent);
            }


        }
    };
}