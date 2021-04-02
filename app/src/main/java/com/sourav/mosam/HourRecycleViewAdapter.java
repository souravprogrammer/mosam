package com.sourav.mosam;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.style.LineHeightSpan;
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

public class HourRecycleViewAdapter extends RecyclerView.Adapter<HourRecycleViewAdapter.HourViewHolder> {
    private ArrayList<WeatherObject> data = new ArrayList<WeatherObject>();
    private String temp_unit;

    @NonNull
    @Override
    public HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_item_view_layout, parent, false);
        return new HourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourViewHolder holder, int position) {
        WeatherObject item = this.data.get(position);
        holder.setImage(AppSettings.drawablecondition(item.getDescription() , Long.parseLong( item.getDt() )));
        if (temp_unit.equals(AppSettings.KELVIN)) {
            String humidity = item.getHumidity() + "%";
            holder.humidity.setText(humidity);
            holder.temp.setText(WeatherObject.convertToKelvin( item.getTemp()) );
            String speed = item.getWind_speed() + " Mi/h";
            holder.wind_speed.setText(speed);
            holder.time.setText(MyTimeConveter.getTime(Long.parseLong(item.gettime())));

        }
        else if (temp_unit.equals(AppSettings.FAHEENHEIT)) {
            String humidity = item.getHumidity() + "%";
            holder.humidity.setText(humidity);
            holder.temp.setText( WeatherObject.contverttoFahrenheit(item.getTemp()));
            String speed = item.getWind_speed() + " Mi/h";
            holder.wind_speed.setText(speed);
            holder.time.setText(MyTimeConveter.getTime(Long.parseLong(item.gettime())));
        }
        else if (temp_unit.equals(AppSettings.CELSIUS)) {
            String humidity = item.getHumidity() + "%";
            holder.humidity.setText(humidity);
            holder.temp.setText(WeatherObject.converttoCelsius(item.getTemp()));
            String speed = item.getWind_speed() + " Mi/h";
            holder.wind_speed.setText(speed);
            holder.time.setText(MyTimeConveter.getTime(Long.parseLong(item.gettime())));
        }


    }

    @Override
    public int getItemCount() {
        return data.size();

    }

    public void updateData(ArrayList<WeatherObject> data, Context context) {
        this.data = data;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.temp_unit = sharedPreferences.getString(AppSettings.TEMP_UNIT_CHANGE, AppSettings.CELSIUS);
        notifyDataSetChanged();
    }

    public class HourViewHolder extends RecyclerView.ViewHolder {
        private TextView time, temp, humidity, wind_speed;
        private ImageView image;

        public HourViewHolder(@NonNull View itemView) {
            super(itemView);
            this.time = itemView.findViewById(R.id.hour_time);
            this.temp = itemView.findViewById(R.id.hour_temp);
            this.wind_speed = itemView.findViewById(R.id.hour_wind);
            this.humidity = itemView.findViewById(R.id.hour_percent);
            this.image = itemView.findViewById(R.id.hour_inage_status) ;
        }
        public void setImage(int imageStatus){
            this.image.setImageResource(imageStatus);
        }
        public void setTime(TextView time) {
            this.time = time;
        }

        public void setTemp(TextView temp) {
            this.temp = temp;
        }

        public void setHumidity(TextView humidity) {
            this.humidity = humidity;
        }

        public void setWind_speed(TextView wind_speed) {
            this.wind_speed = wind_speed;
        }

        public void setImage(ImageView image) {
            this.image = image;
        }
    }

}
