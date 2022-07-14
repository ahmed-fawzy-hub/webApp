package com.hanynemr.yat730webapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class XmlActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    Spinner signSpinner;
    TextView horText;

    Button showButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        signSpinner = findViewById(R.id.signSpinner);
        horText = findViewById(R.id.horText);
        showButton = findViewById(R.id.showButton);

        signSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showButton.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showButton.setEnabled(false);
            }
        });
    }

    public void show(View view) {
        showButton.setEnabled(false);
        String url = "https://www.findyourfate.com/rss/horoscope-astrology-feed.php?mode=view";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(url, this, this);
        queue.add(request);
    }

    @Override
    public void onResponse(String response) {

        SAXBuilder builder = new SAXBuilder();
        StringReader reader = new StringReader(response);
        try {
            Document document = builder.build(reader);

            List<Element> items = document.getRootElement().getChild("channel")
                    .getChildren("item");

            for (Element item : items) {
                if (item.getChildText("title").startsWith(signSpinner.getSelectedItem().toString())) {
                    horText.setText(item.getChildText("description"));
                    break;
                }
            }

//            showButton.setEnabled(true);


        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

        showButton.setEnabled(true);

    }
}