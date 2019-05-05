package com.mecodroid.quate_realm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.Bidi;
import java.util.List;

public class QuateAdapter extends RecyclerView.Adapter<QuateAdapter.QuateHolder> {
    private Context context;
    private List<QuateModel> quateList;

    public QuateAdapter(Context context, List<QuateModel> quateList) {
        this.context = context;
        this.quateList = quateList;
    }

    @Override
    public QuateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false);
        return new QuateHolder(row);
    }

    @Override
    public void onBindViewHolder(QuateHolder holder, int position) {
        final QuateModel quate = quateList.get(position);
        Bidi bidi = new Bidi(quateList.get(position).getQuateTitle(), Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        if (bidi.getBaseLevel() == 0) {
            holder.quateTitlerec.setGravity(Gravity.LEFT);
        } else {
            holder.quateTitlerec.setGravity(Gravity.RIGHT);
        }
        holder.quateTitlerec.setText(quate.getQuateTitle());
        holder.quateTitlerec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Quatesetting.class);
                intent.putExtra("id", quate.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quateList != null ? quateList.size() : 0;
    }

    class QuateHolder extends RecyclerView.ViewHolder {
        TextView quateTitlerec;

        public QuateHolder(View itemView) {
            super(itemView);
            quateTitlerec = itemView.findViewById(R.id.titquate);
        }
    }
}
