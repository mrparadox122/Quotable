package com.paradox.quotable;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Results> postsList;
    private Context context;

    public PostAdapter(List<Results> postsList , Context context) {
        this.postsList = postsList;
        this.context = context;
    }
    //some extra touches :)
    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.AuthorName.setText(postsList.get(position).getAuthor());
        holder.AuthorSlug.setText(postsList.get(position).getAuthorSlug());
        holder.Content.setText(postsList.get(position).getContent());
        holder.tags.setText(postsList.get(position).getTags());
        holder.Content.setMovementMethod(new ScrollingMovementMethod());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Quote Stolen successfully", Toast.LENGTH_SHORT).show();
                setClipboard(context,holder.Content.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView AuthorName;
        TextView AuthorSlug;
        TextView Content;
        TextView tags;
        ImageButton imageButton;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            AuthorName = itemView.findViewById(R.id.AuthorName);
            AuthorSlug = itemView.findViewById(R.id.AuthorSlug);
            Content = itemView.findViewById(R.id.Content);
            tags = itemView.findViewById(R.id.tags);
            Content.setMovementMethod(new ScrollingMovementMethod());
            imageButton = itemView.findViewById(R.id.StealQuote);
        }
    }
}
