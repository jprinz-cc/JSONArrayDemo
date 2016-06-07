package com.example.jprinz.testtabbedapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class demoActivity extends Activity {

    TextView employeeNameBox;
    String fName;
    String lName;
    String fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);


        String jsonData = loadJSONFromAsset();  //This is where we are grabbing the json data, converted to a String
        Log.i("info", "Data: " + jsonData);

        employeeNameBox = (TextView)findViewById(R.id.dropTextHere);

        //Try is needed when accessing JSON data
        try{

            JSONObject jsonResponse = new JSONObject(jsonData);   //This converts the String back to json data as jsonResponse
            //List<String> allNames = new ArrayList<String>();   //This can be used to generate a list of your json data

            JSONArray cast = jsonResponse.getJSONArray("employees");  //This creates the JSONArray cast which is an array of the json objects;
                                                                        //'employees' is the main branch of my json data in myData.json

            //This snip of code will grab one random object from your JSONArray of cast
            /*Random random = new Random();

            int randInt = random.nextInt(cast.length());

            JSONObject employee = cast.getJSONObject(randInt);
            fullName = employee.getString("firstName") + " " + employee.getString("lastName");
            Log.i("info", "FullName: " + fullName);*/
            //End of snip

            for (int i=0; i<cast.length(); i++) {   //This for loop will loop over the JSONArray of cast exposing the objects properties for access
                JSONObject employee = cast.getJSONObject(i);
                fullName = employee.getString("firstName") + " " + employee.getString("lastName");
                Log.i("info", "FullName: " + fullName);
                //allNames.add(fullName);  //This will add the fullName to the allNames list declared above
            }


        } catch (Throwable t){
            Log.e("My App", "Could not parse JSON: \"" + jsonData + "\"");
        }


        employeeNameBox.setText("" + fullName);

    }

    public String loadJSONFromAsset() {   //This is the code that grabs the JSON data from the assets folder and returns as a String
        String json = null;
        try {
            InputStream is = this.getAssets().open("myData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
