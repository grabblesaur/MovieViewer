package com.bbayar.movieviewer.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bbayar.movieviewer.R;
import com.bbayar.movieviewer.model.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Result> resultList;

    public MoviesAdapter(List<Result> resultList) {
        this.resultList = resultList;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        Result result = resultList.get(position);
        holder.movieName.setText(result.getOriginalName());
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_name)
        TextView movieName;

        MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
