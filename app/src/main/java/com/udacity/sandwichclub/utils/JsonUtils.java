package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");
            String mainName = name.getString("mainName");
            Log.d(TAG, "mainName is " + mainName);

            JSONArray jsonArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new LinkedList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                alsoKnownAs.add((String) jsonArray.get(i));
            }

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");

            JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
            List<String> ingredients = new LinkedList<String>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add((String) ingredientsArray.get(i));
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
