package com.hanynemr.yat730webapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    EditText movieText;
    TextView detailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieText = findViewById(R.id.movieText);
        detailText = findViewById(R.id.detailText);
    }

    public void search(View view) {
        //request queue request add request to queue
        String url = String.format("http://www.omdbapi.com/?t=%s&apikey=8de0f502", movieText.getText().toString());
//        String url="http://www.omdbapi.com/?t="+ movieText.getText().toString() +"&apikey=8de0f502";
        JsonObjectRequest request = new JsonObjectRequest(url, null, this, this);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "internet error", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            String plot = response.getString("Plot");
            detailText.setText(plot);

            String actors = response.getString("Actors");
            detailText.append("\n Actors " + actors);

            detailText.append("\n Ratings");
            JSONArray ratings = response.getJSONArray("Ratings");
            for (int i = 0; i < ratings.length(); i++) {
                String source = ratings.getJSONObject(i).getString("Source");
                String value = ratings.getJSONObject(i).getString("Value");
                detailText.append("\n " + source + ":" + value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}