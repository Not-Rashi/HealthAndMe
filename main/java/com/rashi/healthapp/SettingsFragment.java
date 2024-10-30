package com.rashi.healthapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey){
            setPreferencesFromResource(R.xml.user_preferences, rootKey);
            CheckBoxPreference notif = findPreference("send_notifications");
            //SwitchPreference step = findPreference("track_steps");
            EditTextPreference goal = findPreference("set_goal");
            notif.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
                @Override
                public boolean onPreferenceChange(Preference pref, Object newVal){
                    SharedPreferences prefs = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = prefs.edit();
                    boolean val = (boolean)newVal;
                    ed.putBoolean("send_notifications", val);
                    ed.commit();
                    Toast.makeText(getActivity().getApplicationContext(), "Changed to : "+String.valueOf(newVal), Toast.LENGTH_LONG).show();
                    return true;
                }
            });
//            step.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
//                @Override
//                public boolean onPreferenceChange(Preference pref, Object newVal){
//                    SharedPreferences prefs = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor ed = prefs.edit();
//                    boolean val = (boolean)newVal;
//                    ed.putBoolean("track_steps", val);
//                    ed.commit();
//                    Toast.makeText(getActivity().getApplicationContext(), "Changed to : "+String.valueOf(newVal), Toast.LENGTH_LONG).show();
//                    return true;
//                }
//            });
            goal.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
                @Override
                public boolean onPreferenceChange(Preference pref, Object newVal){
                    SharedPreferences prefs = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = prefs.edit();
                    String val = (String)newVal;
                    ed.putInt("set_goal", Integer.parseInt(val));
                    ed.commit();
                    Toast.makeText(getActivity().getApplicationContext(), "Changed to : "+String.valueOf(newVal), Toast.LENGTH_LONG).show();
                    return true;
                }
            });
    }
}
