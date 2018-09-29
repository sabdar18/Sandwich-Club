package com.udacity.sandwichclub.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.DetailActivity;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import java.util.List;

public class SandwichAdapter extends RecyclerView.Adapter<SandwichAdapter.ViewHolder> {

    private final List<Sandwich> mSandwichList;

    public SandwichAdapter(List<Sandwich> sandwichList) {
        mSandwichList = sandwichList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Sandwich sandwich = mSandwichList.get(i);
        viewHolder.sandwichName.setText(sandwich.getMainName());
        Picasso.get()
                .load(sandwich.getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(viewHolder.sandwichImage);
    }

    @Override
    public int getItemCount() {
        return mSandwichList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sandwichName;
        ImageView sandwichImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sandwichName = itemView.findViewById(R.id.sandwichName);
            sandwichImage = itemView.findViewById(R.id.sandwichImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchDetailActivity(v, getAdapterPosition());
                }
            });
        }

        private void launchDetailActivity(View v, int position) {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_POSITION, position);
            v.getContext().startActivity(intent);
        }
    }
}
