package com.example.perfectmovie;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.perfectmovie.Adapter.RecyclerAdapterRelease;
import com.example.perfectmovie.Interface.IAPI;
import com.example.perfectmovie.Interface.IRecyclerView;
import com.example.perfectmovie.Model.Release;
import com.example.perfectmovie.Model.ReleasedFilms;
import com.google.gson.Gson;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpectedFragment extends Fragment implements IRecyclerView {

    public View view;
    NestedScrollView _nestedScrollView;
    Button _backButton;
    RecyclerView _recyclerView;
    Button _datePickButton;
    ProgressBar _progressBar;
    IAPI _apiInterface;
    IRecyclerView _recyclerViewInterface;
    ReleasedFilms _releasedList;
    int _page = 1;
    final int PAGES_LIMIT_FROM = 1;
    int _pagesLimitTo;
    String _strMonth = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_expected, container, false);
        return inflater.inflate(R.layout.fragment_expected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _progressBar = view.findViewById(R.id.progressBar);
        _progressBar.setVisibility(View.VISIBLE);
        _recyclerViewInterface = this;
        _recyclerView = view.findViewById(R.id.recyclerView);
        _datePickButton = view.findViewById(R.id.datepickButton);
        _nestedScrollView = view.findViewById(R.id.nestedScrollView);
        _apiInterface = RequestBuilder.buildRequest().create(IAPI.class);
        final Calendar date = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar updateDate = Calendar.getInstance();
                updateDate.set(year, month, dayOfMonth);
                _page = 1;
                getReleaseListApi(year, month);
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));

        _datePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        view.findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_page <= PAGES_LIMIT_FROM) return;
                --_page;
                _progressBar.setVisibility(View.VISIBLE);
                getReleaseListApi(datePickerDialog.getDatePicker().getYear(), datePickerDialog.getDatePicker().getMonth());
            }
        });

        view.findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_page >= _pagesLimitTo) return;
                ++_page;
                getReleaseListApi(datePickerDialog.getDatePicker().getYear(), datePickerDialog.getDatePicker().getMonth());
                _nestedScrollView.fullScroll(View.FOCUS_UP);
            }
        });

        getReleaseListApi(datePickerDialog.getDatePicker().getYear(), datePickerDialog.getDatePicker().getMonth());

    }



    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), AboutFilm.class);
        Release release = _releasedList.getItems().get(position);
        intent.putExtra("Release", new Gson().toJson(release));
        startActivity(intent);
    }


    private void getReleaseListApi(int year, int month) {
        switch(month+1){
            case 1: _strMonth = "JANUARY";break;
            case 2: _strMonth = "FEBRUARY";break;
            case 3: _strMonth = "MARCH";break;
            case 4: _strMonth = "APRIL";break;
            case 5: _strMonth = "MAY";break;
            case 6: _strMonth = "JUNE";break;
            case 7: _strMonth = "JULY";break;
            case 8: _strMonth = "AUGUST";break;
            case 9: _strMonth = "SEPTEMBER";break;
            case 10: _strMonth = "OCTOBER";break;
            case 11: _strMonth = "NOVEMBER";break;
            case 12: _strMonth = "DECEMBER";break;
            default: return;
        }
        _progressBar.setVisibility(View.VISIBLE);
            Call<ReleasedFilms> getReleases = _apiInterface.getReleases(year, _strMonth, _page);
        getReleases.enqueue(new Callback<ReleasedFilms>() {
            @Override
            public void onResponse(Call<ReleasedFilms> call, Response<ReleasedFilms> response) {
                if(response.isSuccessful()){
                    _recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    _recyclerView.setHasFixedSize(true);
                    _releasedList = response.body();
                    _pagesLimitTo = _releasedList.getTotalPages();
                    _recyclerView.setAdapter(new RecyclerAdapterRelease(getContext(), _releasedList.getItems() , _recyclerViewInterface, year, _strMonth));
                    _progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ReleasedFilms> call, Throwable t) {
                Toast.makeText(view.getRootView().getContext(), "Can't get films list", Toast.LENGTH_SHORT).show();
            }
        });
    }
}