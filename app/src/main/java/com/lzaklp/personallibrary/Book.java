package com.lzaklp.personallibrary;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Book {
    private final String id;
    private final String title;
    private final String author;
    private final int rating;
    private final String notes;
    private final String isbn;
    private final String coverUrl;

    public Book(String title, String author, int rating, String notes) {
        this(UUID.randomUUID().toString(), title, author, rating, notes, "", "");
    }

    public Book(String title, String author, int rating, String notes, String isbn, String coverUrl) {
        this(UUID.randomUUID().toString(), title, author, rating, notes, isbn, coverUrl);
    }

    private Book(String id, String title, String author, int rating, String notes,
                 String isbn, String coverUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.rating = Math.max(0, Math.min(5, rating));
        this.notes = notes == null ? "" : notes;
        this.isbn = isbn == null ? "" : isbn;
        this.coverUrl = coverUrl == null ? "" : coverUrl;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getRating() { return rating; }
    public String getNotes() { return notes; }
    public String getIsbn() { return isbn; }
    public String getCoverUrl() { return coverUrl; }

    public Book withCoverUrl(String url) {
        return new Book(id, title, author, rating, notes, isbn, url);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("title", title);
        object.put("author", author);
        object.put("rating", rating);
        object.put("notes", notes);
        object.put("isbn", isbn);
        object.put("coverUrl", coverUrl);
        return object;
    }

    public static Book fromJson(JSONObject object) throws JSONException {
        return new Book(
                object.optString("id", UUID.randomUUID().toString()),
                object.getString("title"),
                object.optString("author", ""),
                object.optInt("rating", 0),
                object.optString("notes", ""),
                object.optString("isbn", ""),
                object.optString("coverUrl", "")
        );
    }
}