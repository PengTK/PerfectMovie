package com.example.perfectmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perfectmovie.Interface.IAPI;
import com.example.perfectmovie.Interface.IRecyclerView;
import com.example.perfectmovie.Model.TopFilms;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView _bottomNavigationView;
    TextView _fragmentTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _fragmentTitle = findViewById(R.id.fragment_title);
        _bottomNavigationView = findViewById(R.id.bottom_nav);
        setFragment(new TopFragment());

        _bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.topFilms:
                        setFragment(new TopFragment());
                        return true;
                    case R.id.expectedFilms:
                        setFragment(new ExpectedFragment());
                        return true;
                }
                return false;
            }
        });

            }

    public void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, fragment, null).commit();
        _fragmentTitle.setText(fragment.toString().substring(0,fragment.toString().indexOf('{')));
    }

}