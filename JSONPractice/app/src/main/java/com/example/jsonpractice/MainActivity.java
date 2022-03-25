package com.example.jsonpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jsonpractice.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<String> userList;
    ArrayAdapter<String> listAdapter;
    Handler mainHandler = new Handler();

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeUserList();
        binding.fetchDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new fetchData().start();
            }
        });

        binding.fetchDataBtn2.setOnClickListener(new View.OnClickListener() {
            class deleteData extends Thread{

                String data = "";
                public void run(){
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setMessage("Fetching Data");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                        }
                    });
                    try {
                        URL url = new URL("https://raw.githubusercontent.com/HarshitDhawan/JSON-Practice-Android/main/index.html");
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        while((line = bufferedReader.readLine()) != null){
                            data = data + line;
                        }
                        if (!data.isEmpty()){
                            JSONObject jsonObject = new JSONObject(data);

                            JSONArray users = jsonObject.getJSONArray("studentsinfo");
                            userList.clear();





                        }

                    }catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            listAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onClick(View view) {
                new deleteData().start();
            }
        });




    }

    private void initializeUserList() {
        userList = new ArrayList<>();
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,userList);
        binding.userList.setAdapter(listAdapter);
    }

    class fetchData extends Thread{

        String data = "";
        public void run(){
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Fetching Data");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    }
                });
               try {
                    URL url = new URL("https://raw.githubusercontent.com/HarshitDhawan/JSON-Practice-Android/main/index.html");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        data = data + line;
                    }
                if (!data.isEmpty()){
                    JSONObject jsonObject = new JSONObject(data);

                    JSONArray users = jsonObject.getJSONArray("studentsinfo");
                    userList.clear();
                    for(int i = 0;i<users.length();i++){
                        JSONObject names = users.getJSONObject(i);

                        String name = names.getString("name");
                        String id = names.getString("id");
                        String email = names.getString("email");
                        String address = names.getString("address");
                        String gen = names.getString("gender");
                        String phone = names.getString("phone");



                        userList.add("Id: " + id);
                        userList.add("Name: " + name);
                        userList.add("Email: " + email);
                        userList.add("Address: " + address);
                        userList.add("Gender: " + gen);
                        userList.add("Phone: " + phone);



                    }
                }

            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    listAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}