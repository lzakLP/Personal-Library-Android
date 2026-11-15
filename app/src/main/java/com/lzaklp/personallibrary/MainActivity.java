package com.lzaklp.personallibrary;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private final List<Book> allBooks = new ArrayList<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private BookStorage storage;
    private BookAdapter adapter;
    private TextView countText;
    private View emptyState;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storage = new BookStorage(this);
        countText = findViewById(R.id.bookCountText);
        emptyState = findViewById(R.id.emptyState);
        searchInput = findViewById(R.id.searchInput);

        RecyclerView recyclerView = findViewById(R.id.booksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(this::confirmDelete, this::showBookDetails);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.addBookButton).setOnClickListener(view -> showBookEditor(null));
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBooks(s.toString());
            }
            @Override public void afterTextChanged(Editable s) { }
        });
        allBooks.addAll(storage.loadBooks());
        refreshBooks();
    }

    private void showBookEditor(Book existing) {
        View content = LayoutInflater.from(this).inflate(R.layout.dialog_add_book, null);
        EditText titleInput = content.findViewById(R.id.inputBookTitle);
        EditText authorInput = content.findViewById(R.id.inputBookAuthor);
        EditText isbnInput = content.findViewById(R.id.inputBookIsbn);
        EditText notesInput = content.findViewById(R.id.inputBookNotes);
        RatingBar ratingInput = content.findViewById(R.id.inputBookRating);

        if (existing != null) {
            titleInput.setText(existing.getTitle());
            authorInput.setText(existing.getAuthor());
            isbnInput.setText(existing.getIsbn());
            notesInput.setText(existing.getNotes());
            ratingInput.setRating(existing.getRating());
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(existing == null ? R.string.add_book_title : R.string.edit_book_title)
                .setView(content)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(existing == null ? R.string.save_book : R.string.save_changes, null)
                .create();

        dialog.setOnShowListener(ignored -> dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(view -> {
                    String title = titleInput.getText().toString().trim();
                    String author = authorInput.getText().toString().trim();
                    String isbn = isbnInput.getText().toString().trim();
                    String notes = notesInput.getText().toString().trim();
                    if (title.isEmpty()) {
                        titleInput.setError(getString(R.string.title_required));
                        return;
                    }

                    Book updated = new Book(title, author, Math.round(ratingInput.getRating()),
                            notes, isbn, existing == null ? "" : existing.getCoverUrl());
                    int oldIndex = existing == null ? -1 : indexOfBook(existing.getId());
                    Book savedBook = oldIndex >= 0 ? updatedWithId(existing, updated) : updated;
                    if (oldIndex >= 0) allBooks.set(oldIndex, savedBook);
                    else allBooks.add(0, savedBook);

                    if (!storage.saveBooks(allBooks)) {
                        if (oldIndex >= 0) allBooks.set(oldIndex, existing);
                        else allBooks.remove(savedBook);
                        Toast.makeText(this, R.string.save_error, Toast.LENGTH_LONG).show();
                        return;
                    }
                    searchInput.setText("");
                    refreshBooks();
                    dialog.dismiss();
                    lookupCover(savedBook.getId(), savedBook.getTitle(), savedBook.getAuthor(), isbn);
                }));
        dialog.show();
    }

    private Book updatedWithId(Book old, Book value) {
        // Preserve the stable ID by replacing the matching entry through its JSON representation.
        try {
            JSONObject json = value.toJson();
            json.put("id", old.getId());
            return Book.fromJson(json);
        } catch (Exception exception) {
            return value;
        }
    }

    private int indexOfBook(String id) {
        for (int i = 0; i < allBooks.size(); i++) if (allBooks.get(i).getId().equals(id)) return i;
        return -1;
    }

    private void lookupCover(String bookId, String title, String author, String isbn) {
        executor.execute(() -> {
            String cover = "";
            HttpURLConnection connection = null;
            try {
                String query = !isbn.isEmpty()
                        ? "isbn:" + isbn.replace("-", "")
                        : "intitle:" + title + (author.isEmpty() ? "" : "+inauthor:" + author);
                String address = "https://www.googleapis.com/books/v1/volumes?q="
                        + URLEncoder.encode(query, StandardCharsets.UTF_8.name()) + "&maxResults=1";
                connection = (HttpURLConnection) new URL(address).openConnection();
                connection.setConnectTimeout(7000);
                connection.setReadTimeout(7000);
                connection.setRequestMethod("GET");
                try (InputStream stream = connection.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                    StringBuilder body = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) body.append(line);
                    JSONObject response = new JSONObject(body.toString());
                    JSONArray items = response.optJSONArray("items");
                    if (items != null && items.length() > 0) {
                        JSONObject info = items.getJSONObject(0).getJSONObject("volumeInfo");
                        JSONObject images = info.optJSONObject("imageLinks");
                        if (images != null) cover = images.optString("thumbnail", "");
                        cover = cover.replace("http://", "https://");
                    }
                }
            } catch (Exception ignored) {
                // Cover lookup is optional; the local book entry remains available offline.
            } finally {
                if (connection != null) connection.disconnect();
            }
            final String result = cover;
            if (!result.isEmpty()) mainHandler.post(() -> saveCover(bookId, result));
        });
    }

    private void saveCover(String bookId, String coverUrl) {
        int index = indexOfBook(bookId);
        if (index < 0) return;
        allBooks.set(index, allBooks.get(index).withCoverUrl(coverUrl));
        storage.saveBooks(allBooks);
        refreshBooks();
    }

    private void showBookDetails(Book book) {
        View content = LayoutInflater.from(this).inflate(R.layout.dialog_book_details, null);
        ImageView cover = content.findViewById(R.id.detailBookCover);
        TextView title = content.findViewById(R.id.detailBookTitle);
        TextView author = content.findViewById(R.id.detailBookAuthor);
        TextView isbn = content.findViewById(R.id.detailBookIsbn);
        TextView rating = content.findViewById(R.id.detailBookRating);
        TextView notes = content.findViewById(R.id.detailBookNotes);
        title.setText(book.getTitle());
        author.setText(book.getAuthor().isEmpty() ? getString(R.string.author_not_informed) : book.getAuthor());
        isbn.setText(book.getIsbn().isEmpty() ? getString(R.string.isbn_not_informed) : "ISBN: " + book.getIsbn());
        rating.setText(book.getRating() == 0 ? getString(R.string.not_rated)
                : getString(R.string.rating_format, book.getRating()));
        notes.setText(book.getNotes().isEmpty() ? getString(R.string.no_notes) : book.getNotes());
        if (book.getCoverUrl().isEmpty()) cover.setImageResource(R.drawable.book_cover_placeholder);
        else Picasso.get().load(book.getCoverUrl()).placeholder(R.drawable.book_cover_placeholder)
                .error(R.drawable.book_cover_placeholder).fit().centerInside().into(cover);

        new AlertDialog.Builder(this).setView(content)
                .setNegativeButton(R.string.close, null)
                .setPositiveButton(R.string.edit, (d, w) -> showBookEditor(book))
                .show();
    }

    private void confirmDelete(Book book) {
        new AlertDialog.Builder(this).setTitle(R.string.remove_book)
                .setMessage(getString(R.string.remove_book_message, book.getTitle()))
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.remove, (dialog, which) -> {
                    allBooks.removeIf(item -> item.getId().equals(book.getId()));
                    storage.saveBooks(allBooks);
                    refreshBooks();
                }).show();
    }

    private void filterBooks(String query) {
        String normalized = query.trim().toLowerCase(Locale.ROOT);
        List<Book> filtered = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getTitle().toLowerCase(Locale.ROOT).contains(normalized)
                    || book.getAuthor().toLowerCase(Locale.ROOT).contains(normalized)
                    || book.getIsbn().toLowerCase(Locale.ROOT).contains(normalized)) filtered.add(book);
        }
        adapter.submitList(filtered);
        emptyState.setVisibility(filtered.isEmpty() ? View.VISIBLE : View.GONE);
        TextView emptyTitle = (TextView) ((LinearLayout) emptyState).getChildAt(0);
        emptyTitle.setText(allBooks.isEmpty() ? R.string.empty_library_title : R.string.no_books_found);
    }

    private void refreshBooks() {
        countText.setText(getResources().getQuantityString(R.plurals.book_count, allBooks.size(), allBooks.size()));
        filterBooks(searchInput.getText() == null ? "" : searchInput.getText().toString());
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}