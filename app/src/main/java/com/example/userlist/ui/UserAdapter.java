package com.example.userlist.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userlist.R;
import com.example.userlist.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList = new ArrayList<>();

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        // Safeguard against null values
        String fullName = (user.getFirstName() != null ? user.getFirstName() : "") + " " +
                (user.getLastName() != null ? user.getLastName() : "");
        holder.nameTextView.setText(fullName.trim());

        // Load image using Picasso with a null check
        if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            Picasso.get()
                    .load(user.getAvatar())
                    .placeholder(R.drawable.ic_avatar_placeholder_foreground) // Placeholder image
                    .error(R.drawable.ic_avatar_placeholder_foreground) // Error image if loading fails
                    .into(holder.avatarImageView);
        } else {
            // Set a placeholder if avatar URL is null or empty
            holder.avatarImageView.setImageResource(R.drawable.ic_avatar_placeholder_foreground);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUsers(List<User> users) {
        this.userList = users != null ? users : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarImageView;
        public TextView nameTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatar_image_view);
            nameTextView = itemView.findViewById(R.id.name_text_view);
        }
    }
}