package com.example.tripadvisor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.model.system.DownloadedFile;
import com.telerik.everlive.sdk.core.result.RequestResult;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.UUID;

public class ImageAdapter extends BaseAdapter {

    public EverliveApp app = new EverliveApp("iEFw0m2S2kX61YKR");
    private ImageView iv;
    List<String> images;
    private Context context;

    public ImageAdapter(Context applicationContext, List<String> imgUrls) {
        context = applicationContext;
        this.images = imgUrls;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            iv = (ImageView) convertView;
        } else {
            iv = new ImageView(context);
            iv.setLayoutParams(new GridView.LayoutParams(150, 100));
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            iv.setPadding(5, 5, 5, 5);
        }

        new PictureAsyncDownloader(iv).execute(images.get(position));

        return iv;
    }

    public class PictureAsyncDownloader extends AsyncTask<String, Integer, Bitmap> {

        private final WeakReference imageViewReference;

        public PictureAsyncDownloader(ImageView imageView) {
            imageViewReference = new WeakReference(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            UUID uid = UUID.fromString(params[0]);

            Bitmap tempImage;
            RequestResult result = app.workWith().files().download(uid)
                    .executeSync();

//            if (result.getSuccess()) {

            DownloadedFile file = (DownloadedFile) result.getValue();
            tempImage = BitmapFactory.decodeStream(file.getInputStream());
//            }
//            else{
//                tempImage = BitmapFactory.decodeResource(getResources(),R.drawable.error);
//            }

            return tempImage;
        }

        @Override
        protected void onPostExecute(Bitmap img) {
            // progressDialog.dismiss();
            if (isCancelled()) {
                img = null;
            }
            if (imageViewReference != null) {
                ImageView imageView = (ImageView) imageViewReference.get();
                if (imageView != null) {
                    if (img != null) {
                        imageView.setImageBitmap(img);
                    } else {
                        imageView.setImageDrawable(imageView.getContext()
                                .getResources()
                                .getDrawable(R.drawable.ic_launcher));
                    }
                }
            }
        }

    }
}