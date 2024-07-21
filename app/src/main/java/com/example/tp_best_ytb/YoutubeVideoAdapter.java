package com.example.tp_best_ytb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_best_ytb.model.YoutubeVideo;
import java.util.List;

public class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoAdapter.YoutubeVideoViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(YoutubeVideo item);
    }

    private final List<YoutubeVideo> videoList;
    private final OnItemClickListener listener;

    public YoutubeVideoAdapter(List<YoutubeVideo> videoList, OnItemClickListener listener) {
        this.videoList = videoList;
        this.listener = listener;
    }

    @Override
    public YoutubeVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_youtube_video, parent, false);
        return new YoutubeVideoViewHolder(view);
    }

    @Override public void onBindViewHolder(YoutubeVideoViewHolder holder, int position) {
        holder.bind(videoList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class YoutubeVideoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvCategory;
        TextView tvDescription;

        public YoutubeVideoViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(final YoutubeVideo item, final OnItemClickListener listener) {
            tvTitle.setText(item.getTitre());
            tvDescription.setText(item.getDescription());
            tvCategory.setText(item.getCategorie());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
