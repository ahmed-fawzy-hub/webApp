package com.hanynemr.yat730webapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class MealDetailActivity extends AppCompatActivity {

    TextView tvName, tvDesc;
    ImageView mealImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);
        tvName = findViewById(R.id.tvName);
        tvDesc = findViewById(R.id.tvDesc);
        mealImage = findViewById(R.id.mealImage);

        Meal meal = (Meal) getIntent().getSerializableExtra("meal");
        tvName.setText(meal.getItemName());
        tvDesc.setText(meal.getDescription());

        Picasso.get().load("http://560057.youcanlearnit.net/services/images/" + meal.getImage()).into(mealImage);

    }
}