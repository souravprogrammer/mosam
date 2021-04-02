package com.sourav.mosam;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sourav.mosam.data.AppSettings;
import com.sourav.mosam.data.MyTimeConveter;
import com.sourav.mosam.data.WeatherObject;

import java.util.ArrayList;

public class DateRecycleViewAdapter extends RecyclerView.Adapter<DateRecycleViewAdapter.ViewHolder> {

    ArrayList<WeatherObject> data = new ArrayList<WeatherObject>();
    private OnclicklistnerRecycle listner;
    private String temp_units;

    public DateRecycleViewAdapter(OnclicklistnerRecycle listner) {
        this.listner = listner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_itemview_layout, parent, false);
        return new ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WeatherObject item = data.get(position);

        holder.setImageStatus(AppSettings.drawablecondition(item.getDescription(), Long.parseLong(item.getDt())));

        if (temp_units.equals(AppSettings.KELVIN)) {
            holder.setMinTemp( WeatherObject.convertToKelvin( item.getMin_temp()));
            holder.setMaxTemp(WeatherObject.convertToKelvin( item.get_max_temp() ));
            holder.setDay(MyTimeConveter.getDay(Long.parseLong(item.gettime())));

        } else if (temp_units.equals(AppSettings.CELSIUS)) {
            holder.setMinTemp(WeatherObject.converttoCelsius(item.getMin_temp()));
            holder.setMaxTemp(WeatherObject.converttoCelsius(item.get_max_temp()));
            holder.setDay(MyTimeConveter.getDay(Long.parseLong(item.gettime())));

        } else if (temp_units.equals(AppSettings.FAHEENHEIT)) {
            holder.setMinTemp(WeatherObject.contverttoFahrenheit(item.getMin_temp()));
            holder.setMaxTemp(WeatherObject.contverttoFahrenheit(item.get_max_temp()));
            holder.setDay(MyTimeConveter.getDay(Long.parseLong(item.gettime())));
        }
    }

    public void updateData(ArrayList<WeatherObject> data, Context context) {
        this.data = data;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.temp_units = sharedPreferences.getString(AppSettings.TEMP_UNIT_CHANGE, AppSettings.CELSIUS);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView maxTemp, minTemp, day;
        private ImageView imageStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day_text);
            maxTemp = itemView.findViewById(R.id.max_temp_second);
            minTemp = itemView.findViewById(R.id.min_temp_second);
            imageStatus = itemView.findViewById(R.id.weather_image_status_second);
            itemView.setOnClickListener(this);
        }

        public void setImageStatus(int imageStatus) {
            this.imageStatus.setImageResource(imageStatus);
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

        @Override
        public void onClick(View v) {
            listner.onClicked(data.get(getAdapterPosition()));
        }
    }

    public interface OnclicklistnerRecycle {
        public void onClicked(WeatherObject weather);
    }
}
