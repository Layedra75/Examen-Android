package com.example.examen;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity {


    public class DigimonActivity extends AppCompatActivity {

        private TextView digimonNameTextView;
        private TextView digimonLevelTextView;
        private ImageView digimonImageView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            digimonNameTextView = findViewById(R.id.digimon_name);
            digimonLevelTextView = findViewById(R.id.digimon_level);

            // Configuración de Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://digimon-api.vercel.app")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            DigimonApiService digimonApiService = retrofit.create(DigimonApiService.class);

            // Hacer la solicitud a la API
            Call<List<Digimon>> call = digimonApiService.getDigimons();
            call.enqueue(new Callback<List<Digimon>>() {
                @Override
                public void onResponse(Call<List<Digimon>> call, Response<List<Digimon>> response) {
                    if (response.isSuccessful()) {
                        List<Digimon> digimons = response.body();
                        if (digimons != null && !digimons.isEmpty()) {
                            Digimon firstDigimon = digimons.get(0);
                            updateUI(firstDigimon);
                        } else {
                            showToast("No se encontraron Digimons");
                        }
                    } else {
                        showToast("Error en la respuesta de la API");
                    }
                }


                @Override
                public void onFailure(Call<List<Digimon>> call, Throwable t) {
                    showToast("Error al realizar la solicitud");
                }
            });
        }

        private void updateUI(Digimon digimon) {
            digimonNameTextView.setText(digimon.getName());
            digimonLevelTextView.setText(digimon.getLevel());
            Picasso.get().load(digimon.getImageUrl()).into(digimonImageView);
        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
