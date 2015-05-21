package com.thinkful.cartrax;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkful.contract.dto.VehicleDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VehicleDto dto = new VehicleDto();

        new AsyncTask<Void,Void,String>() {
            @Override
            protected String doInBackground(Void... params) {

                HttpURLConnection urlConnection = null;
                BufferedReader bufferedReader = null;
                StringBuilder json = new StringBuilder();

                try {
//                    URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?lat=45&lon=23&mode=json&units=metric&cnt=1");
                    URL url = new URL("http://10.0.2.2:8080/vehicles");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        json.append(line + "\n");
                    }

                    return json.toString();

                } catch (IOException e) {
                    Log.e("MainActivity", "Error ", e);

                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(String json) {
                try {
                    List<VehicleDto> vehicleDtos = new ObjectMapper().readValue(json, new TypeReference<List<VehicleDto>>() {});
                    Log.i("","Successful.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                super.onPostExecute(json);
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
