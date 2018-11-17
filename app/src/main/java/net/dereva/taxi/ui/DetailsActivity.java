package net.dereva.taxi.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.dereva.taxi.R;
import net.dereva.taxi.helper.DateHelper;
import net.dereva.taxi.helper.PhotoHelper;
import net.dereva.taxi.model.Order;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailsActivity extends AppCompatActivity {

    private static final String URL_WHERE_IMAGE_STORE = "https://www.roxiemobile.ru/careers/test/images/";
    private static final String TAG = "photo";

    private ProgressBar progressBar;
    private ImageView vehiclePhoto;
    private String vehiclePhotoName;
    private Context myContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        myContext = getBaseContext();
        setToolbar();

        Order order = getOrder();
        bindOrderFieldsWithViewsAndSetValues(order);

        vehiclePhotoName = order.getVehicle().getPhoto();
        showVehiclePhoto(vehiclePhotoName);
    }


    private void showVehiclePhoto(String vehiclePhotoName){
        Bitmap b = PhotoHelper.loadPhotoFromCache(myContext, vehiclePhotoName);
        if(b != null){
            setBitmapInImageView(b);
            Log.i(TAG, "photo got from cache");
            hideProgressBar(progressBar);
        }
        else {
            URL url = getUrlForImage(URL_WHERE_IMAGE_STORE, vehiclePhotoName);
            new DownloadBitmap().execute(url);
        }
    }


    private class DownloadBitmap extends DownloadBitmapTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            progressBar = new android.widget.ProgressBar(getApplicationContext(),
                    null,
                    android.R.attr.progressBarStyle);
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            hideProgressBar(progressBar);
            setBitmapInImageView(bitmap);
            Log.i(TAG, "photo got from server");
            PhotoHelper.savePhotoInCache(myContext, bitmap, vehiclePhotoName);
            PhotoHelper.startTimerToDeletePhotoFromCache(myContext, vehiclePhotoName);

        }

    }


    public static class DownloadBitmapTask extends AsyncTask<URL, Void, Bitmap>{

        @Override
        // this bitmap will pass to onPostExecute(Bitmap bitmap)
        protected Bitmap doInBackground(URL... params) {
            return getBitmapFromUrl(params[0]);
        }
    }


    public static Bitmap getBitmapFromUrl(URL url){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            Log.w(TAG, "не удалось загрузить картинку", e);

            return null;
        }

    }


    private void hideProgressBar(View view){
        //((ViewGroup)view.getParent()).removeView(view);
        view.setVisibility(View.GONE);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private Order getOrder() {
        Intent intent = getIntent();
        return intent.getParcelableExtra("clickedOrder");
    }


    private URL getUrlForImage(String urlLinkFirstPart, String urlLinkLastPart){
        StringBuilder link = new StringBuilder();
        link.append(urlLinkFirstPart).append(urlLinkLastPart);

        try {
            return new URL(link.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        }

    }


    private void setBitmapInImageView(Bitmap bitmap) {
        vehiclePhoto.setImageBitmap(bitmap);
    }


    private void bindOrderFieldsWithViewsAndSetValues(Order order) {
        TextView startCity = findViewById(R.id.frag_start_city_tv);
        TextView startAddress = findViewById(R.id.frag_start_address_tv);
        TextView endCity = findViewById(R.id.frag_end_city_tv);
        TextView endAddress = findViewById(R.id.frag_end_address_tv);
        TextView orderTime = findViewById(R.id.frag_trip_time_tv);
        TextView orderDate = findViewById(R.id.frag_trip_date_tv);
        TextView orderAmount = findViewById(R.id.frag_order_amount_tv);
        TextView currency = findViewById(R.id.frag_currency_tv);
        TextView vehicle = findViewById(R.id.frag_vehicle_tv);
        TextView regNumber = findViewById(R.id.frag_reg_number_tv);
        TextView driverName = findViewById(R.id.frag_driver_name_tv);
        vehiclePhoto = findViewById(R.id.frag_vehicle_photo_iv);
        progressBar = findViewById(R.id.frag_progressBar);

        startCity.setText(order.getStartAddress().getCity());
        startAddress.setText(order.getStartAddress().getAddress());
        endCity.setText(order.getEndAddress().getCity());
        endAddress.setText(order.getEndAddress().getAddress());

        orderTime.setText(DateHelper.showOrderTime(order));
        orderDate.setText(DateHelper.showOrderDate(order));

        orderAmount.setText(String.valueOf(order.getPrice().getAmount()).substring(0,3));
        currency.setText(order.getPrice().getCurrency());

        vehicle.setText(order.getVehicle().getModelName());
        regNumber.setText(order.getVehicle().getRegNumber());
        driverName.setText(order.getVehicle().getDriverName());
    }


    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar(). setDisplayShowHomeEnabled(true);
    }

}