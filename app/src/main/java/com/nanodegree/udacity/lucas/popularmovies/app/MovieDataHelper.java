package com.nanodegree.udacity.lucas.popularmovies.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Mobile on 04/11/2016.
 */

public class MovieDataHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    static final String DATABASE_NAME = "movie.db";

    Movie movie;
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues values;

    private static final String TABLE_FAVORITE_MOVIES = "favoritemovies";

    private static final String TABLE_CREATE_FAVORITE_MOVIES =
            "create table favoritemovies (id integer primary key not null, " +
                    " movie_id integer not null, original_title text not null, overview text not null, " +
                    "poster_path text not null, adult integer not null, release_date text not null, " +
                    " genre_ids text not null, original_language text not null, " +
                    "title text not null, backdrop_path text not null, " +
                    " popularity text not null, vote_count text not null, vote_average integer not null);";

    public MovieDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE_FAVORITE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_MOVIES);
        onCreate(sqLiteDatabase);
    }

    private void readableDatabase(){ sqLiteDatabase = this.getReadableDatabase(); }

    private void writableDatabase() { sqLiteDatabase = this.getWritableDatabase(); }

    private void instancingContentValue(){ values = new ContentValues(); }

    public void insertMovie(Movie movie) {
        writableDatabase();
        instancingContentValue();
        moviePutValues(movie, values);
        sqLiteDatabase.insert(TABLE_FAVORITE_MOVIES, null , values);
    }

    private void moviePutValues(Movie movie, ContentValues values){
        values.put(movie.TAG_POSTER_PATH, movie.getPoster_path());
        values.put(movie.TAG_OVERVIEW, movie.getOverview());
        values.put(movie.TAG_RELEASE_DATA, movie.getRelease_date());
        values.put(movie.TAG_GENRE_IDS, movie.getGenre_ids());
        values.put(movie.TAG_ID, movie.getId());
        values.put(movie.TAG_ORIGINAL_TITLE, movie.getOriginal_title());
        values.put(movie.TAG_ORIGINAL_LANGUAGE, movie.getOriginal_language());
        values.put(movie.TAG_TITLE, movie.getTitle());
        values.put(movie.TAG_BACKDROP_PATH, movie.getBackdrop_path());
        values.put(movie.TAG_POPULARITY, movie.getPopularity());
        values.put(movie.TAG_VOTE_COUNT, movie.getVote_count());
        values.put(movie.TAG_VOTE_AVERAGE, movie.getVote_average());
    }

    public ArrayList<Movie> getFavoriteMovies(){
        ArrayList<Movie> list = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_FAVORITE_MOVIES;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Movie movie = new Movie();
                    movie.setPoster_path(cursor.getString(cursor.getColumnIndex(movie.TAG_POSTER_PATH)));
                    movie.setOverview(cursor.getString(cursor.getColumnIndex(movie.TAG_OVERVIEW)));
                    movie.setRelease_date(cursor.getString(cursor.getColumnIndex(movie.TAG_RELEASE_DATA)));
                    movie.setGenre_ids(cursor.getString(cursor.getColumnIndex(movie.TAG_GENRE_IDS)));
                    movie.setId(cursor.getString(cursor.getColumnIndex(movie.getId())));
                    movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(movie.TAG_ORIGINAL_TITLE)));
                    movie.setOriginal_language(cursor.getString(cursor.getColumnIndex(movie.TAG_ORIGINAL_LANGUAGE)));
                    movie.setTitle(cursor.getString(cursor.getColumnIndex(movie.TAG_TITLE)));
                    movie.setBackdrop_path(cursor.getString(cursor.getColumnIndex(movie.TAG_BACKDROP_PATH)));
                    movie.setPopularity(cursor.getString(cursor.getColumnIndex(movie.TAG_POPULARITY)));
                    movie.setVote_count(cursor.getString(cursor.getColumnIndex(movie.TAG_VOTE_COUNT)));
                    movie.setVote_average(cursor.getString(cursor.getColumnIndex(movie.TAG_VOTE_AVERAGE)));
                    list.add(movie);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DBDB", "ERROOO");
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

}