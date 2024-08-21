package com.example.userlist.ui;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.userlist.R;

public class UserItem extends RecyclerView.ViewHolder {
    public ImageView avatarImageView;
    public TextView nameTextView;

    public UserItem(View itemView) {
        super(itemView);
        // Ensure these IDs match the ones in your layout XML
        avatarImageView = itemView.findViewById(R.id.avatar_image_view);
        nameTextView = itemView.findViewById(R.id.name_text_view);
    }
}