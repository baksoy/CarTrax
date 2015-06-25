package com.thinkful.cartrax;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkful.cartrax.services.impl.VehicleServiceImpl;
import com.thinkful.contract.dto.VehicleDto;
import com.thinkful.contract.dto.VehicleMakeDto;

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

//        createMake("Toyota");
        loadVehicleMakes();
    }

    private void createMake(final String make) {
        new AsyncTask<Void,Void,VehicleMakeDto>() {
            @Override
            protected VehicleMakeDto doInBackground(Void... params) {
                return VehicleServiceImpl.getInstance().createVehicleMake(make);
            }

            @Override
            protected void onPostExecute(VehicleMakeDto json) {
                Toast.makeText(MainActivity.this,"Vehicle Make Created: " + json.toString(),Toast.LENGTH_LONG).show();
//                try {
//                    List<VehicleDto> vehicleDtos = new ObjectMapper().readValue(json, new TypeReference<List<VehicleDto>>() {});
//                    Log.i("","Successful.");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }.execute();
    }

    private void loadVehicleMakes() {
        new AsyncTask<Void,Void,List<VehicleMakeDto>>() {
            @Override
            protected List<VehicleMakeDto> doInBackground(Void... params) {
                return VehicleServiceImpl.getInstance().getVehicleMakes();
            }

            @Override
            protected void onPostExecute(List<VehicleMakeDto> data) {
                Toast.makeText(MainActivity.this,"Vehicle Makes Loaded: " + data.size() + "; " + data.toString(),Toast.LENGTH_LONG).show();
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
