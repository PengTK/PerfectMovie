package com.example.perfectmovie.Model;

import java.util.ArrayList;

public class Videos {
    int total;
    ArrayList<Source> items;
    public String getVideoUrl(){
        if(items.size() > 0) {
            return items.get(0).url;
        }
        return "";
    }

    private class Source{
        String url;
        String name;
        String site;
    }
}
