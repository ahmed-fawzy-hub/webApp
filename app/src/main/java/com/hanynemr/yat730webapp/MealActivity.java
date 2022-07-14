package com.hanynemr.yat730webapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MealActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener {

    ListView lvMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        lvMeals = findViewById(R.id.lvMeals);

        String url = "http://560057.youcanlearnit.net/services/json/itemsfeed.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(url, this, this);
        queue.add(request);

    }

    @Override
    public void onResponse(JSONArray response) {

//        ArrayList<Meal> meals=new ArrayList<>();
//        for (int i = 0; i < response.length(); i++) {
//            try {
//                Meal meal=new Meal();
//                meal.setItemName(response.getJSONObject(i).getString("itemName"));
//                meal.setCategory(response.getJSONObject(i).getString("category"));
//                meal.setDescription(response.getJSONObject(i).getString("description"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        Gson gson = new Gson();
        Meal[] meals = gson.fromJson(response.toString(), Meal[].class);

        List<String> names = Stream.of(meals)
                .map(Meal::getItemName)
                .collect(Collectors.toList());

        //adapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        lvMeals.setAdapter(adapter);

        lvMeals.setOnItemClickListener((parent, view, position, id) -> {
            Intent in = new Intent(MealActivity.this, MealDetailActivity.class);
            in.putExtra("meal", meals[position]);
            startActivity(in);

        });


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

    }
}