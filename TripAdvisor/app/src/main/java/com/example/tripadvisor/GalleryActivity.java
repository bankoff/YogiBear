package com.example.tripadvisor;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.model.system.File;
import com.telerik.everlive.sdk.core.result.RequestResult;

import java.util.ArrayList;

public class GalleryActivity extends Activity {
    public EverliveApp app = new EverliveApp("iEFw0m2S2kX61YKR");
    private ImageAdapter imageAdapter;
    private GridView gallery;
    RequestResult<ArrayList<File>> requestResult;
    ArrayList<File> allFiles;

    public class UidAsyncDownloader extends AsyncTask<Void, Void, Void>{
        ArrayList<String> imgUrls = new ArrayList<String>();

        @Override
        protected Void doInBackground(Void... params) {
            requestResult = app.workWith().data(File.class)
                    .getAll().executeSync();

            if (requestResult.getSuccess()) {
                allFiles = requestResult.getValue();

                for (int i = 0; i < allFiles.size(); i++) {
                    String currentUuid = allFiles.get(i).getId().toString();
                    imgUrls.add(currentUuid);
                }
            } else {
                Log.i("ERROR", "BAD THING HAPPENED");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            imageAdapter = new ImageAdapter(GalleryActivity.this, imgUrls);
            gallery.setAdapter(imageAdapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        gallery = (GridView) findViewById(R.id.grid_gallery);
        new UidAsyncDownloader().execute();
    }
}