package net.dereva.taxi.helper;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.dereva.taxi.model.Order;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonHelper {

    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";

    private Order[] ordersList;

    public static URL createUrl(String link){

        try {
            return new URL(link);
        } catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        }
    }

    public Order[] parseJsonFromUrl(URL url){

        try (Reader reader = new InputStreamReader(url.openStream())) {
            ordersList = new GsonBuilder()
                    .setDateFormat(DATE_PATTERN)
                    .create().fromJson(reader, new TypeToken<Order[]>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ordersList;
    }

}