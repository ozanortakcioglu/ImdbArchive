package com.example.tazemeta.izlemelik;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class movie {
    public String mId;
    public String mtitle;
    public String mYear;
    public String mRuntime;
    public String mGenre;
    public String mDirector;
    public String mActors;
    public String mPlot;
    public String mCountry;
    public String mPosterUrl;
    public String mRating;

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String mType;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getmYear() {
        return mYear;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public String getmRuntime() {
        return mRuntime;
    }

    public void setmRuntime(String mRuntime) {
        this.mRuntime = mRuntime;
    }

    public String getmGenre() {
        return mGenre;
    }

    public void setmGenre(String mGenre) {
        this.mGenre = mGenre;
    }

    public String getmDirector() {
        return mDirector;
    }

    public void setmDirector(String mDirector) {
        this.mDirector = mDirector;
    }

    public String getmActors() {
        return mActors;
    }

    public void setmActors(String mActors) {
        this.mActors = mActors;
    }

    public String getmPlot() {
        return mPlot;
    }

    public void setmPlot(String mPlot) {
        this.mPlot = mPlot;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmPosterUrl() {
        return mPosterUrl;
    }

    public void setmPosterUrl(String mPosterUrl) {
        this.mPosterUrl = mPosterUrl;
    }

    public String getmRating() {
        return mRating;
    }

    public void setmRating(String mRating) {
        this.mRating = mRating;
    }



    public void fill(String jsonData) throws JSONException{
        JSONObject movieData = new JSONObject(jsonData);
        mtitle =  movieData.getString("Title");
        mYear = movieData.getString("Year");
        mGenre = movieData.getString("Genre");
        mDirector = movieData.getString("Director");
        mActors= movieData.getString("Actors");
        mPlot = movieData.getString("Plot");
        mCountry = movieData.getString("Country");
        mRuntime=movieData.getString("Runtime");
        mRating = movieData.getString("imdbRating");
        mId = movieData.getString("imdbID");
        mPosterUrl=movieData.getString("Poster");

    }
}
