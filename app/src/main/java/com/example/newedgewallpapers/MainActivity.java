package com.example.newedgewallpapers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newedgewallpapers.adapters.CategoryAdapter;
import com.example.newedgewallpapers.adapters.WallpaperAdapter;
import com.example.newedgewallpapers.data.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface {

    private RecyclerView categoryRV, wallpaperRV;
    private ProgressBar loadingPB;
    private ArrayList<Category> categoryRVModals;
    private ArrayList<String> wallpaperArrayList;
    private CategoryAdapter categoryRVAdapter;
    private WallpaperAdapter wallpaperRVAdapter;
    private EditText searchEdt;
    private ImageView searchIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // initializing all variables on below line.
        categoryRV = findViewById(R.id.idRVCategories);
        wallpaperRV = findViewById(R.id.idRVWallpapers);
        searchEdt = findViewById(R.id.idEdtSearch);
        searchIV = findViewById(R.id.idIVSearch);
        loadingPB = findViewById(R.id.idPBLoading);
        wallpaperArrayList = new ArrayList<>();
        categoryRVModals = new ArrayList<>();

        LinearLayoutManager manager1 = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);

        wallpaperRVAdapter = new WallpaperAdapter(wallpaperArrayList, this);
        categoryRVAdapter = new CategoryAdapter(categoryRVModals, this, this);
        categoryRV.setLayoutManager(manager1);
        categoryRV.setAdapter(categoryRVAdapter);


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);


        wallpaperRV.setLayoutManager(layoutManager);
        wallpaperRV.setAdapter(wallpaperRVAdapter);


        getCategories();
        getWallpapers();

        searchIV.setOnClickListener(v -> {

            String searchStr = searchEdt.getText().toString();
            if (searchStr.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter something to search", Toast.LENGTH_SHORT).show();
            } else {
                getWallpapersByCategory(searchStr);
            }
        });
    }


    private void getWallpapersByCategory(String category) {

        wallpaperArrayList.clear();

        loadingPB.setVisibility(View.VISIBLE);

        String url = "https://api.pexels.com/v1/search?query=" + category + "&per_page=65&page=1";

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", String.valueOf(response));
                try {
                    loadingPB.setVisibility(View.GONE);
                    JSONArray photos = response.getJSONArray("photos");
                    for (int i = 0; i < photos.length(); i++) {
                        JSONObject photoObj = photos.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");

                        wallpaperArrayList.add(imgUrl);
                    }

                    wallpaperRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            // displaying a simple toast message on error response.
            Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "SWptn2CYIhJ5nj9MOeBclcN3ntGSWNQiH8CWVzbUwb3ZdcjrA4j7SLVx");
                // at last returning headers.
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    private void getWallpapers() {
        wallpaperArrayList.clear();

        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/curated?per_page=65&page=1";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                loadingPB.setVisibility(View.GONE);
                try {
                    JSONArray photos = response.getJSONArray("photos");
                    for (int i = 0; i < photos.length(); i++) {
                        JSONObject photoObj = photos.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");

                        wallpaperArrayList.add(imgUrl);
                    }
                    wallpaperRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "SWptn2CYIhJ5nj9MOeBclcN3ntGSWNQiH8CWVzbUwb3ZdcjrA4j7SLVx");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    private void getCategories() {
        categoryRVModals.add(new Category("Technology", "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHRlY2hub2xvZ3l8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModals.add(new Category("Programming", "https://images.unsplash.com/photo-1542831371-29b0f74f9713?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cHJvZ3JhbW1pbmd8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModals.add(new Category("Nature", "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModals.add(new Category("Travel", "https://images.pexels.com/photos/672358/pexels-photo-672358.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModals.add(new Category("Architecture", "https://images.pexels.com/photos/256150/pexels-photo-256150.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModals.add(new Category("Arts", "https://images.pexels.com/photos/1194420/pexels-photo-1194420.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModals.add(new Category("Music", "https://images.pexels.com/photos/4348093/pexels-photo-4348093.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModals.add(new Category("Abstract", "https://images.pexels.com/photos/2110951/pexels-photo-2110951.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModals.add(new Category("Cars", "https://images.pexels.com/photos/3802510/pexels-photo-3802510.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModals.add(new Category("Flowers", "https://images.pexels.com/photos/1086178/pexels-photo-1086178.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModals.get(position).getCategory();
        getWallpapersByCategory(category);
    }
}