package com.example.hngleaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardVH> {

    Context context;
    private List<Users> users;

    public LeaderBoardAdapter(Context context){
        this.context = context;
    }

    class LeaderBoardVH extends RecyclerView.ViewHolder {

        TextView index, name, slackHandle, slackPoint;
        ImageView photo;

        LeaderBoardVH(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.leaderboard_index);
            name = itemView.findViewById(R.id.leaderboard_name);
            slackHandle = itemView.findViewById(R.id.leaderboard_slack_handle);
            slackPoint = itemView.findViewById(R.id.leaderboard_slack_point);
            photo = itemView.findViewById(R.id.leaderboard_image);

        }
    }

    @NonNull
    @Override
    public LeaderBoardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler_view_list_items, parent, false);
        return new LeaderBoardVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardVH holder, int position) {
        if (users != null) {
            Users currentUser = users.get(position);
            holder.index.setText(currentUser.getIndex());
            holder.name.setText(currentUser.getName());
            holder.slackHandle.setText(currentUser.getSlackHandle());
            holder.slackPoint.setText(currentUser.getSlackPoint() + " points");
            holder.photo.setImageResource(currentUser.getPhoto());
        }
    }

    @Override
    public int getItemCount() {
        if (users != null)
            return users.size();
        else return 0;
    }

    public void setLeaderBoardList(List<Users> usersList) {
        users = usersList;
        notifyDataSetChanged();
    }

}
