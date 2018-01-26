package com.example.huaizhi.xtpfinalhomework;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by huaizhi on 1/6/18.
 */

public class XTPInfoAdapter extends RecyclerView.Adapter<XTPInfoAdapter.ViewHolder> {
    private List<XTPInformation> mInfoList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        // I need something here
        TextView visitTime_textview, visitor_textview;
        public ViewHolder(View view) {
            super(view);
            visitor_textview = (TextView) view.findViewById(R.id.visitor);
            visitTime_textview = (TextView)view.findViewById(R.id.visit_time);
        }
    }

    public XTPInfoAdapter(List<XTPInformation> xtpInformations) {
        mInfoList = xtpInformations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xtp_info, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.visitor_textview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                XTPInformation xtpInformation = mInfoList.get(position);
                String id = xtpInformation.getId();
                Context context = v.getContext();
                Intent intent = new Intent(context, XTPDetail.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        viewHolder.visitTime_textview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                XTPInformation xtpInformation = mInfoList.get(position);
                String id = xtpInformation.getId();  // pass id
                Context context = v.getContext();
                Intent intent = new Intent(context, XTPDetail.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        XTPInformation xtpInformation = mInfoList.get(position);
        holder.visitTime_textview.setText(xtpInformation.getTime());
        holder.visitor_textview.setText(xtpInformation.getName());
    }

    @Override
    public int getItemCount() {
        return mInfoList.size();
    }
}





