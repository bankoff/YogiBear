package com.example.tripadvisor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;


public class AddPlaceActivity extends Activity implements View.OnClickListener {

    private Button btnAddPicture, btnCreatePlace;
    final Context context = this;
    private ImageView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        btnAddPicture = (Button) this.findViewById(R.id.picture_button);
        btnCreatePlace = (Button) this.findViewById(R.id.create_button);
        btnAddPicture.setOnClickListener(this);
        btnCreatePlace.setOnClickListener(this);
        mView = (ImageView) findViewById(R.id.imageView3);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            mView.setImageBitmap(photo);
        }
    }

    private void startCamera() {
        Intent camera = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        this.startActivityForResult(camera, 100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_place, menu);
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.picture_button) {
            Toast.makeText(context, "Make pic with the camera", Toast.LENGTH_SHORT)
                    .show();
            startCamera();
        }
        if (view.getId() == R.id.create_button) {
            Toast.makeText(context, "Send info to the database and create the place", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
