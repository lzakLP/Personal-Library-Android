package com.lzaklp.personallibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    public interface OnBookDeleteListener { void onDelete(Book book); }
    public interface OnBookClickListener { void onClick(Book book); }

    private final List<Book> books = new ArrayList<>();
    private final OnBookDeleteListener deleteListener;
    private final OnBookClickListener clickListener;

    public BookAdapter(OnBookDeleteListener deleteListener, OnBookClickListener clickListener) {
        this.deleteListener = deleteListener;
        this.clickListener = clickListener;
    }

    public void submitList(List<Book> newBooks) {
        books.clear();
        books.addAll(newBooks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor().isEmpty()
                ? holder.itemView.getContext().getString(R.string.author_not_informed) : book.getAuthor());
        holder.rating.setText(book.getRating() == 0
                ? holder.itemView.getContext().getString(R.string.not_rated)
                : String.format(Locale.getDefault(), "★  %d / 5", book.getRating()));

        Picasso.get().cancelRequest(holder.cover);
        if (book.getCoverUrl().isEmpty()) {
            holder.cover.setImageResource(R.drawable.book_cover_placeholder);
        } else {
            Picasso.get().load(book.getCoverUrl())
                    .placeholder(R.drawable.book_cover_placeholder)
                    .error(R.drawable.book_cover_placeholder)
                    .fit().centerCrop().into(holder.cover);
        }
        holder.delete.setOnClickListener(view -> deleteListener.onDelete(book));
        holder.itemView.setOnClickListener(view -> clickListener.onClick(book));
    }

    @Override public int getItemCount() { return books.size(); }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        final TextView title, author, rating;
        final ImageButton delete;
        final ImageView cover;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.bookAuthor);
            rating = itemView.findViewById(R.id.bookRating);
            delete = itemView.findViewById(R.id.deleteBookButton);
            cover = itemView.findViewById(R.id.bookCover);
        }
    }
}