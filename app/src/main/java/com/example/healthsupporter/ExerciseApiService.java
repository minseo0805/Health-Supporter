package com.example.healthsupporter;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExerciseApiService {
    @GET("exercise/{bodyPart}")
    Call<List<Exercise>> getExerciseData(@Path("bodyPart") String bodyPart);
}