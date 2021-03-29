package com.sourav.mosam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sourav.mosam.data.WeatherObject;

import java.util.ArrayList;

public class DateRecycleViewAdapter extends RecyclerView.Adapter<DateRecycleViewAdapter.ViewHolder> {

    ArrayList<WeatherObject> data = new ArrayList<WeatherObject>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_itemview_layout,parent,false);
        return new ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WeatherObject item = data.get(position);
        holder.setMinTemp(item.getMin_temp());
        holder.setMaxTemp(item.get_max_temp());

    }
    public void updateData(ArrayList<WeatherObject> data){
        this.data = data ;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

   public class ViewHolder extends RecyclerView.ViewHolder {
       private TextView maxTemp , minTemp , day ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day_text);
            maxTemp = itemView.findViewById(R.id.max_temp_second);
            minTemp = itemView.findViewById(R.id.min_temp_second);
        }

       public void setMaxTemp(String maxTemp) {
           this.maxTemp.setText(maxTemp);
       }

       public void setMinTemp(String minTemp) {
           this.minTemp.setText(minTemp);
       }

       public void setDay(String day) {
           this.day.setText(day);
       }
   }
}
