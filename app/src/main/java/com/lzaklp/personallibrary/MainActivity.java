package com.lzaklp.personallibrary;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final List<Book> allBooks = new ArrayList<>();
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
        adapter = new BookAdapter(this::confirmDelete);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.addBookButton).setOnClickListener(view -> showAddBookDialog());
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

    private void showAddBookDialog() {
        View content = LayoutInflater.from(this).inflate(R.layout.dialog_add_book, null);
        EditText titleInput = content.findViewById(R.id.inputBookTitle);
        EditText authorInput = content.findViewById(R.id.inputBookAuthor);
        EditText notesInput = content.findViewById(R.id.inputBookNotes);
        RatingBar ratingInput = content.findViewById(R.id.inputBookRating);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a book")
                .setView(content)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save book", null)
                .create();

        dialog.setOnShowListener(ignored -> dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(view -> {
                    String title = titleInput.getText().toString().trim();
                    String author = authorInput.getText().toString().trim();
                    String notes = notesInput.getText().toString().trim();

                    if (title.isEmpty()) {
                        titleInput.setError("Enter a book title");
                        return;
                    }

                    allBooks.add(0, new Book(title, author,
                            Math.round(ratingInput.getRating()), notes));
                    if (!storage.saveBooks(allBooks)) {
                        allBooks.remove(0);
                        Toast.makeText(this, "Could not save the book.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    searchInput.setText("");
                    refreshBooks();
                    dialog.dismiss();
                }));
        dialog.show();
    }

    private void confirmDelete(Book book) {
        new AlertDialog.Builder(this)
                .setTitle("Remove book?")
                .setMessage("“" + book.getTitle() + "” will be removed from your library.")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Remove", (dialog, which) -> {
                    allBooks.removeIf(item -> item.getId().equals(book.getId()));
                    storage.saveBooks(allBooks);
                    refreshBooks();
                })
                .show();
    }

    private void filterBooks(String query) {
        String normalized = query.trim().toLowerCase(Locale.ROOT);
        List<Book> filtered = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getTitle().toLowerCase(Locale.ROOT).contains(normalized)
                    || book.getAuthor().toLowerCase(Locale.ROOT).contains(normalized)) {
                filtered.add(book);
            }
        }
        adapter.submitList(filtered);
        emptyState.setVisibility(filtered.isEmpty() ? View.VISIBLE : View.GONE);
        TextView emptyTitle = (TextView) ((LinearLayout) emptyState).getChildAt(0);
        emptyTitle.setText(allBooks.isEmpty() ? "Your library is waiting" : "No books found");
    }

    private void refreshBooks() {
        countText.setText(allBooks.size() == 1 ? "1 book" : allBooks.size() + " books");
        filterBooks(searchInput.getText() == null ? "" : searchInput.getText().toString());
    }
}
