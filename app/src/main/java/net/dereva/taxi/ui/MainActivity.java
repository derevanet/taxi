package net.dereva.taxi.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;


import net.dereva.taxi.R;
import net.dereva.taxi.adapter.OrderAdapter;
import net.dereva.taxi.helper.JsonHelper;
import net.dereva.taxi.interfaces.NoInternetDialogListener;
import net.dereva.taxi.interfaces.OnOrderClickListener;
import net.dereva.taxi.model.Order;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements NoInternetDialogListener {

    public static final String ORDERS_URL = "https://www.roxiemobile.ru/careers/test/orders.json";

    private ProgressBar progressBar;
    private OrderAdapter adapter;
    private NoInternetDialog noInternetDialog;
    private ArrayList<Order> sortedOrders;
    private View parentLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentLayout = findViewById(android.R.id.content);
        progressBar = findViewById(R.id.act_progressBar);

        if(savedInstanceState==null) {
            tryToShowOrders();
        } else {
            hideProgressBar(progressBar);
        }

    }

    private void tryToShowOrders() {
        if(isOnline()){
            showOrderList();
        } else {
            showNoInternetDialog();
        }
    }

    private void showOrderList() {
        URL url = JsonHelper.createUrl(ORDERS_URL);
        new ParseOrders().execute(url);
    }

    private class ParseOrders extends OrderListParser{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Snackbar.make(parentLayout, R.string.orders_are_downloading, Snackbar.LENGTH_SHORT).show();
        }


        @Override
        protected void onProgressUpdate(Void... values){
            progressBar = new android.widget.ProgressBar(getApplicationContext(),
                    null,
                    android.R.attr.progressBarStyle);
        }


        @Override
        protected void onPostExecute(Order[] orders) {
            super.onPostExecute(orders);
            Snackbar.make(parentLayout, R.string.orders_successfully_downloaded, Snackbar.LENGTH_SHORT).show();

            hideProgressBar(progressBar);

            Arrays.sort(orders, OrderDateComparator);
            sortedOrders = new ArrayList<>(Arrays.asList(orders));

            showRecyclerView(sortedOrders);
        }

    }

    public static class OrderListParser extends AsyncTask<URL, Void, Order[]> {
        // this Order[] will pass to onPostExecute(Order[] orders)
        @Override
        protected Order[] doInBackground(URL... params) {
            JsonHelper jsonhelper = new JsonHelper();
            return jsonhelper.parseJsonFromUrl(params[0]);
        }
    }

    public static Comparator<Order> OrderDateComparator = new Comparator<Order>() {

        @Override
        public int compare(Order o1, Order o2) {
            return ((o2.getOrderTime()).compareTo(o1.getOrderTime()));
        }
    };

    private void showRecyclerView(ArrayList<Order> orders){
        RecyclerView rv = findViewById(R.id.act_order_list_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderAdapter(orders);
        adapter.setOrderClickListener(new OnOrderClickListener() {
            @Override
            public void onOrderClicked(int position) {
                openOrderInDetailsActivity(position);
            }
        });
        rv.setAdapter(adapter);
    }

    private void openOrderInDetailsActivity(int position){
        Order clickedOrder = adapter.getOrder(position);
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("clickedOrder", clickedOrder);
        startActivity(intent);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showNoInternetDialog() {
        noInternetDialog = new NoInternetDialog();
        noInternetDialog.setCancelable(false);
        noInternetDialog.show(getSupportFragmentManager(), "noInternet");
    }

    public void onDialogRetryInternetConnectionClick() {
        noInternetDialog.dismiss();
        tryToShowOrders();
    }

    private void hideProgressBar(View view){
        //((ViewGroup)view.getParent()).removeView(view);
        view.setVisibility(View.GONE);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        sortedOrders = savedInstanceState.getParcelableArrayList("sortedOrders");
        showRecyclerView(sortedOrders);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("sortedOrders", sortedOrders);
    }

}