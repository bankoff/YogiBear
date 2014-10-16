package com.example.tripadvisor;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import database.Place;
import database.PlaceDataSource;


public class AddToDatabaseActivity extends ListActivity  {

    private PlaceDataSource datasource;
    final Context context = this;
    //  private  String picturePath;
    String titlePlace = (AddPlaceActivity.title).getText().toString();
    String descriptionPlace = (AddPlaceActivity.description).getText().toString();
    String longitudePic = (AddPlaceActivity.longitudeValue);
    String latitudePic = (AddPlaceActivity.latitudeValue);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_database);


        datasource = new PlaceDataSource(this);
        datasource.open();

        List<Place> values = datasource.getAllPlaces();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Place> adapter = new ArrayAdapter<Place>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_to_database, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onClick(View view) {
        if (view.getId() == R.id.button_add_to_database) {



            ArrayAdapter<Place> adapter = (ArrayAdapter<Place>) getListAdapter();
            Place place = null;

 place = datasource.createPlace(titlePlace,descriptionPlace,longitudePic,latitudePic,"gore");

          adapter.add(place);
           Toast.makeText(context, "Place successfully added!", Toast.LENGTH_SHORT).show();
        }
    }
}
