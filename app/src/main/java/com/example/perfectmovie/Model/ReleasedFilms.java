package com.example.perfectmovie.Model;

import java.util.ArrayList;

public class ReleasedFilms {

    private ArrayList<Release> releases;
    private int page;
    private int total;
    public ArrayList<Release> getItems(){ return releases;}
    public int getTotalPages() { return (int)Math.ceil(total / 10);}




}
