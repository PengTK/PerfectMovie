package com.example.perfectmovie.Model;

import java.util.ArrayList;

public class Film {
        int kinopoiskId;
        String imdbId;
        String nameRu;
        String nameEn;
        String nameOriginal;
        ArrayList<Country> countries;
        ArrayList<Genre> genres;
        double ratingKinopoisk;
        double ratingImdb;
        int year;
        String type;
        String posterUrl;
        String posterUrlPreview;

        public String getDetails() {
                StringBuilder sb = new StringBuilder();
                sb.append("Genres: ");
                for (Genre genrein:
                     genres) {
                        sb.append(genrein.genre.toString() + " | ");
                }
                sb.append("\n");
                sb.append("Countries: ");
                for (Country countryin :
                        countries) {
                        sb.append(countryin.country.toString() + " | ");
                }
                return sb.toString();
        }
        public int getDate(){ return year; }

        public int getKinopoiskId() { return kinopoiskId; }

        public String getNameRu() {
            return nameRu;
        }
        public String getNameEn() {
                return nameEn;
        }

        public String getImg() {
            return posterUrlPreview;
        }

         public double getRating() {
            return ratingKinopoisk;
        }
}
