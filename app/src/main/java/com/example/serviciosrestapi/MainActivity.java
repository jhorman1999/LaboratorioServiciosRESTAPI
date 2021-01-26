package com.example.serviciosrestapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();
    private ArrayAdapter arrayAdapter;
    EditText filtarID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Arrayadapter
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        list = findViewById(R.id.list);

        list.setAdapter(arrayAdapter);

        filtarID =(EditText)findViewById(R.id.editTextFiltrar);
        getPosts();
    }
    public void onClickFiltrar(View view){
        String aux=filtarID.getText().toString();
        if(aux.equals("")){
            getPosts();
        } else {
            getOnePost(Integer.parseInt(aux));
        }

    }

    private void getPosts() {
        titles.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostService postService = retrofit.create(PostService.class);
        Call<List<Post>> call = postService.getPost();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                for(Post post : response.body()){
                    titles.add(post.getTitle());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }

    private void getOnePost(int id){
        titles.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<Post> call = postService.getOnePost(id);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                titles.add(response.body().getTitle());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }
        });

    }


}