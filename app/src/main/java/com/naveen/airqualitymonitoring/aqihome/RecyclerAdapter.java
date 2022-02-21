package com.naveen.airqualitymonitoring.aqihome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.naveen.airqualitymonitoring.R;
import com.naveen.airqualitymonitoring.aqicitydetail.OnDataClickListner;
import com.naveen.airqualitymonitoring.repository.Weather;
import com.naveen.airqualitymonitoring.utils.TimeAgo2;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_LIST = 1;
    private List<Weather> arrayList;
    private Context mContext;
    private OnDataClickListner mListener;

    public RecyclerAdapter(Context context, List<Weather> weathers, OnDataClickListner listener) {
        mContext = context;
        this.arrayList = weathers;
        this.mListener = listener;
    }

    public void setArrayList(List<Weather> arrayList) {
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerViewHolder recyclerViewHolder;

        if(viewType == TYPE_HEAD){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_layout, parent, false);
            recyclerViewHolder = new RecyclerViewHolder(view,viewType);
            return recyclerViewHolder;
        }
        else if (viewType == TYPE_LIST){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
            recyclerViewHolder = new RecyclerViewHolder(view,viewType);
            return recyclerViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecyclerViewHolder holder, int position) {

        if(holder.mViewType == TYPE_LIST) {
            Weather weather =arrayList.get(position-1);
            TextView city = holder.city;
            TextView aqi = holder.aqi;
            TextView time = holder.time;
            if(weather.getAqi() <= 50.00 ){
                holder.aqi.setTextColor(ContextCompat.getColor(mContext, R.color.dark_green));
            }
            else if(weather.getAqi() <= 100.00 ){
                holder.aqi.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            }
            else if(weather.getAqi() <= 200.00 ) {
                holder.aqi.setTextColor(ContextCompat.getColor(mContext, R.color.yellow));
            }
            else if (weather.getAqi() <= 300.00 ){
                holder.aqi.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
            }
            else if (weather.getAqi() <= 400.00 ) {
                holder.aqi.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }
            else if (weather.getAqi() <= 500.00 ) {
                holder.aqi.setTextColor(ContextCompat.getColor(mContext, R.color.dark_red));
            }
            city.setText(weather.getCity());
            aqi.setText(weather.getAqi() + "");
            time.setText(TimeAgo2.covertTimeToText(weather.getTime()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDataClick(weather);
                }
            });
        }
        else if(holder.mViewType == TYPE_HEAD){
            holder.cityH.setText(R.string.city);
            holder.aqiH.setText(R.string.aqi);
            holder.timeH.setText(R.string.last_updated);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size() + 1;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        int mViewType;
        //Variable for list
        TextView city;
        TextView aqi;
        TextView time;

        //Variable for head
        TextView cityH;
        TextView aqiH;
        TextView timeH;

        public RecyclerViewHolder(View view,int viewType) {
            super(view);
            if (viewType == TYPE_LIST) {
                city = (TextView) view.findViewById(R.id.city1);
                aqi = (TextView) view.findViewById(R.id.aqi1);
                time = (TextView) view.findViewById(R.id.time1);
                mViewType = viewType;
            }
            else if (viewType == TYPE_HEAD) {
                cityH = (TextView) view.findViewById(R.id.city_h);
                aqiH = (TextView) view.findViewById(R.id.aqi_h);
                timeH = (TextView) view.findViewById(R.id.time_h);
                mViewType = viewType;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }
        else
            return TYPE_LIST;
    }
}


