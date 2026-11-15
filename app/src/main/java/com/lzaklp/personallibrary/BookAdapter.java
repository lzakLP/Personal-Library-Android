package com.lzaklp.personallibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    public interface OnBookDeleteListener {
        void onDelete(Book book);
    }

    private final List<Book> books = new ArrayList<>();
    private final OnBookDeleteListener deleteListener;

    public BookAdapter(OnBookDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void submitList(List<Book> newBooks) {
        books.clear();
        books.addAll(newBooks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor().isEmpty() ? "Author not informed" : book.getAuthor());
        holder.rating.setText(book.getRating() == 0
                ? "NOT RATED"
                : String.format(Locale.getDefault(), "★  %d / 5", book.getRating()));
        holder.delete.setOnClickListener(view -> deleteListener.onDelete(book));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView author;
        final TextView rating;
        final ImageButton delete;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.bookAuthor);
            rating = itemView.findViewById(R.id.bookRating);
            delete = itemView.findViewById(R.id.deleteBookButton);
        }
    }
}
