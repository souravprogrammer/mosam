package com.sourav.mosam;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;

import com.sourav.mosam.data.AppSettings;

public class Mypreference extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);
        updatePreferenceui();

    }


    private void updatePreferenceui() {

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();

        int count = getPreferenceScreen().getPreferenceCount();
        Preference preference = findPreference(AppSettings.LOCATION_KEY) ;
        preference.setSummary(sharedPreferences.getString(AppSettings.LOCATION_KEY, "Delhi"));


        Preference preference_temp = findPreference(AppSettings.TEMP_UNIT_CHANGE);
        preference_temp.setSummary(sharedPreferences.getString(AppSettings.TEMP_UNIT_CHANGE, "Celsius"));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (AppSettings.LOCATION_KEY.equals(key)) {
            Preference preference = findPreference(AppSettings.LOCATION_KEY);
            preference.setSummary(getPreferenceScreen().getSharedPreferences().getString(key, "Delhi"));
        }else if(AppSettings.TEMP_UNIT_CHANGE.equals(key)){

            Preference preference = findPreference(AppSettings.TEMP_UNIT_CHANGE);
            preference.setSummary(getPreferenceScreen().getSharedPreferences().getString(key,"Celsius"));
        }
    }
}
