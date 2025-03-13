package com.example.perfectmovie;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.perfectmovie.Adapter.RecyclerAdapterTop;
import com.example.perfectmovie.Interface.IAPI;
import com.example.perfectmovie.Interface.IRecyclerView;
import com.example.perfectmovie.Model.Film;
import com.example.perfectmovie.Model.TopFilms;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopFragment extends Fragment implements IRecyclerView {

    public View view;
    RecyclerView _recyclerView;
    NestedScrollView _nestedScrollView;
    ProgressBar _progressBar;
    TopFilms _filmsList;
    IAPI _apiInterface;
    IRecyclerView _recyclerViewInterface;
    int _page = 1;
    int _pagesLimitTo;
    final int PAGES_LIMIT_FROM = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_top, container, false);
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _progressBar = view.findViewById(R.id.progressBar);
        _progressBar.setVisibility(View.VISIBLE);
         _recyclerViewInterface = this;
        _recyclerView = view.findViewById(R.id.recyclerView);
        _nestedScrollView = view.findViewById(R.id.nestedScrollView);
        _apiInterface = RequestBuilder.buildRequest().create(IAPI.class);
        view.findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_page <= PAGES_LIMIT_FROM) return;
                --_page;
                _progressBar.setVisibility(View.VISIBLE);
                getFilmsListApi();
            }
        });

        view.findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_page >= _pagesLimitTo) return;
                ++_page;
                getFilmsListApi();
                _nestedScrollView.fullScroll(View.FOCUS_UP);
            }
        });

        getFilmsListApi();


    }

    private void getFilmsListApi(){
        _progressBar.setVisibility(View.VISIBLE);
        Call<TopFilms> getTopFilms = _apiInterface.getTop250Films("TOP_250_MOVIES", _page);
        getTopFilms.enqueue(new Callback<TopFilms>() {
            @Override
            public void onResponse(Call<TopFilms> call, Response<TopFilms> response) {
                if(response.isSuccessful()){
                    _recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    _recyclerView.setHasFixedSize(true);
                    _filmsList = response.body();
                    _pagesLimitTo = _filmsList.getTotalPages();
                    _recyclerView.setAdapter(new RecyclerAdapterTop(getContext(), _filmsList.getItems() , _recyclerViewInterface));
                    _progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<TopFilms> call, Throwable t) {
                Toast.makeText(view.getRootView().getContext(), "Can't get films list", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), AboutFilm.class);
        Film film = _filmsList.getItems().get(position);
        intent.putExtra("Film", new Gson().toJson(film));
        startActivity(intent);
    }
}