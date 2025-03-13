package com.example.perfectmovie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectmovie.Interface.IRecyclerView;
import com.example.perfectmovie.Model.Release;
import com.example.perfectmovie.Model.ReleasedFilms;
import com.example.perfectmovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapterRelease extends RecyclerView.Adapter<RecyclerAdapterRelease.ViewHolder> {
    private IRecyclerView _recyclerViewInterface;
    private Context _context;
    private ArrayList<Release> _itemsList;
    private int _year = 2023;
    private String _month;

    public RecyclerAdapterRelease(Context context, ArrayList<Release> itemsList, IRecyclerView recyclerViewInterface, int year, String month){
        _context = context;
        _itemsList = itemsList;
        _recyclerViewInterface = recyclerViewInterface;
        _year = year;
        _month = month;
    }

    @NonNull
    @Override
    public RecyclerAdapterRelease.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.filmcard, parent, false);
        return new ViewHolder(view, _recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterRelease.ViewHolder holder, int position) {
        if(!_itemsList.get(position).getNameRu().equals("")) {
            holder.titleView.setText(_itemsList.get(position).getNameRu());
        }
        else {
            holder.titleView.setText(_itemsList.get(position).getNameEn());
        }
        holder.expectedDate.setText(_year + " " + _month);
        Picasso.with(_context).load(_itemsList.get(position).getImg()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {return _itemsList.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView expectedDate;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView, IRecyclerView recyclerViewInterface) {
            super(itemView);
            titleView = itemView.findViewById(R.id.filmName);
            imageView = itemView.findViewById(R.id.imageView2);
            expectedDate = itemView.findViewById(R.id.expectedDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface == null)
                        return;

                    int pos = getAdapterPosition();

                    if(pos ==RecyclerView.NO_POSITION)
                        return;

                    recyclerViewInterface.onItemClick(pos);
                }
            });

        }
    }
}
