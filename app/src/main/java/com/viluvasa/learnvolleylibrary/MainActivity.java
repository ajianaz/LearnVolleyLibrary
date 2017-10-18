package com.viluvasa.learnvolleylibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.viluvasa.learnvolleylibrary.controller.AppConfig;
import com.viluvasa.learnvolleylibrary.controller.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainVolley";
    //tambahan
    TextView edData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tambahan
        edData = (TextView)findViewById(R.id.edData);

        //tambahan untuk request data online
        ///Req_Data();
    }

    //tambahan
    public void LoadData(View view){
        Req_Data();
    }

    private void Req_Data(){
        //showPDialog("Loading ...");
        // Creating volley request obj
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_Maps, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                //hidePDialog();
                // Parsing json
                try {
                    JSONObject jObj = new JSONObject(response); //output pertama json object

                    JSONArray Geocode = jObj.getJSONArray("geocoded_waypoints");

                    JSONObject data0 = Geocode.getJSONObject(0);


                    JSONArray types = data0.getJSONArray("types");

                    String sampel = types.getString(0);
                    //String Data = "";

//                    for (int i = 0; i<data_museum.length(); i++){
//                        //looping data
//                        JSONObject data = data_museum.getJSONObject(i);
//                        //String nomer =  data.get("id");
//
//                        Data = Data +(i+1)+". "+ data.getString("koleksi") +"\n";
//                    }

                    edData.setText(sampel); //pasang data

                    //Toast.makeText(MainActivity.this, ""+data_0.getString("nama_museum"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // notifying list adapter about data changes
                // so that it renders the list view with updated data
                //adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hidePDialog();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                finish();

            }
        });

        // Adding request to request queue
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));
        AppController.getInstance().addToRequestQueue(strReq);
    }
}
