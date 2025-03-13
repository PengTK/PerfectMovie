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
import com.example.perfectmovie.Model.Film;
import com.example.perfectmovie.Model.TopFilms;
import com.example.perfectmovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapterTop extends RecyclerView.Adapter<RecyclerAdapterTop.ViewHolder> {

    private final IRecyclerView _recyclerViewInterface;
    private Context _context;
    private  ArrayList<Film> _itemsList;

    public RecyclerAdapterTop(Context context, ArrayList<Film> itemsList, IRecyclerView recyclerViewInterface){
        _context = context;
        _itemsList = itemsList;
        _recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecyclerAdapterTop.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.filmcard, parent, false);
        return new ViewHolder(view, _recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterTop.ViewHolder holder, int position) {
        Film film = _itemsList.get(position);
        if(!film.getNameRu().equals("")) {
            holder.titleView.setText(film.getNameRu());
        }
        else {
            holder.titleView.setText(film.getNameEn());
        }
        holder.ratingView.setText("Rate: " + film.getRating());
        Picasso.with(_context).load(film.getImg()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {return _itemsList.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView ratingView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView, IRecyclerView recyclerViewInterface) {
            super(itemView);
            titleView = itemView.findViewById(R.id.filmName);
            imageView = itemView.findViewById(R.id.imageView2);
            ratingView = itemView.findViewById(R.id.rating);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface == null)
                        return;

                    int pos = getAdapterPosition();

                    if(pos == RecyclerView.NO_POSITION)
                        return;

                    recyclerViewInterface.onItemClick(pos);
                }
            });

        }
    }
}
