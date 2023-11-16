package com.example.examen;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DigimonApiService {
    @GET("/api/digimon")
    Call<List<Digimon>> getDigimons();
}
