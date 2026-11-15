package com.lzaklp.personallibrary;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BookStorage {
    private static final String FILE_NAME = "personal_library.json";
    private final Context context;

    public BookStorage(Context context) {
        this.context = context.getApplicationContext();
    }

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (FileInputStream input = context.openFileInput(FILE_NAME);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(input, StandardCharsets.UTF_8))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) json.append(line);
            JSONArray array = new JSONArray(json.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.optJSONObject(i);
                if (object != null) {
                    try {
                        books.add(Book.fromJson(object));
                    } catch (JSONException ignored) {
                        // Skip malformed entries while preserving the remaining library.
                    }
                }
            }
        } catch (java.io.FileNotFoundException ignored) {
            // A missing file means the library is empty.
        } catch (Exception exception) {
            android.util.Log.e("BookStorage", "Could not read library", exception);
        }
        return books;
    }

    public boolean saveBooks(List<Book> books) {
        JSONArray array = new JSONArray();
        for (Book book : books) {
            try {
                array.put(book.toJson());
            } catch (JSONException exception) {
                android.util.Log.e("BookStorage", "Could not serialize book", exception);
                return false;
            }
        }

        try (FileOutputStream output = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            output.write(array.toString().getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (Exception exception) {
            android.util.Log.e("BookStorage", "Could not save library", exception);
            return false;
        }
    }
}
