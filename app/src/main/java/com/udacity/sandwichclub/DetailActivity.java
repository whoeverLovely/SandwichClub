package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            Log.d(TAG, "intent is null");
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.d(TAG, "EXTRA_POSITION is wrong.");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            Log.d(TAG, "sandwich data is wrong.");
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView nameTV = findViewById(R.id.mainName);
        nameTV.setText(sandwich.getMainName());

        TextView alsoKnownAsTV = findViewById(R.id.also_known);
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        String alsoKnownStr = null;
        for(String s:alsoKnownAs) {
            alsoKnownStr = alsoKnownStr+s+"  ";
        }
        alsoKnownAsTV.setText(alsoKnownStr);

        TextView placeOfOrigin = findViewById(R.id.place_of_origin);
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        TextView ingredientsTV = findViewById(R.id.ingredients);
        List<String> ingredientsList = sandwich.getIngredients();
        String ingredients = null;
        for(String s:ingredientsList) {
            ingredients = ingredients+s+"  ";
        }
        ingredientsTV.setText(ingredients);

        TextView description = findViewById(R.id.description);
        description.setText(sandwich.getDescription());


    }
}
