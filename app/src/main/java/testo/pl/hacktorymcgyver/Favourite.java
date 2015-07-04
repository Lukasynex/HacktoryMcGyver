package testo.pl.hacktorymcgyver;

/**
 * Created by Lukasz Marczak on 2015-07-04.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Lukasz Marczak on 2015-07-03.
 */
public class Favourite {
    public static final String TAG = Favourite.class.getSimpleName();
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public Favourite() {
    }

    public void saveFavorites(Context context, List<Record> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_WORLD_READABLE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Record product) {
        List<Record> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(final Context context, final Record product) {
        Log.d(TAG, "removing from favourite");
        new Thread(new Runnable() {
            @Override
            public void run() {

                ArrayList<Record> favorites = getFavorites(context);
                if (favorites != null) {
                    String banned_item = product.value;
                    ArrayList<Record> list = new ArrayList<>();

                    for (int i = 0; i < favorites.size(); i++) {
                        if (!favorites.get(i).value.equals(banned_item))
                            list.add(favorites.get(i));
                    }
                    saveFavorites(context, list);
                }

            }
        }).start();

    }

    public ArrayList<Record> getFavorites(Context context) {
        SharedPreferences settings;
        List<Record> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Record[] favoriteItems = gson.fromJson(jsonFavorites,
                    Record[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Record>(favorites);
        } else
            return null;

        return (ArrayList<Record>) favorites;
    }
}