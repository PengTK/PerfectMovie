package com.example.perfectmovie.Model;

import java.util.ArrayList;

public class Release {
        int filmId;
        String nameRu;
        String nameEn;
        int year;
        String posterUrl;
        String posterUrlPreview;
        ArrayList<Country> countries;
        ArrayList<Genre> genres;
        double rating;
        int ratingVoteCount;
        double expectationsRating;
        int expectationsRatingVoteCount;
        int duration;
        String releaseDate;

        public String getNameRu(){ return nameRu; }
        public String getNameEn(){ return nameEn; }
        public String getDetails() {
                StringBuilder sb = new StringBuilder();
                sb.append("Genres: ");
                for (Genre genrein:
                        genres) {sb.append(genrein.genre.toString() + " | ");}
                sb.append("\n");
                sb.append("Countries: ");
                for (Country countryin : countries) {sb.append(countryin.country.toString() + " | ");}return sb.toString();}
        public String getDate(){ return releaseDate; }
        public int getKinopoiskId() { return filmId; }
        public String getImg() {
                return posterUrlPreview;
        }
        public double getRating() {
                return expectationsRating;
        }
    }
