package edu.dartmouth.cs.a21days.views;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import edu.dartmouth.cs.a21days.R;

/**
 * The fragment in which users can change their preferences
 */
public class SettingsFragment extends PreferenceFragment {

    private final String TAG = "SettingsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from xml resource
        addPreferencesFromResource(R.xml.preferences);
        PreferenceScreen screen = getPreferenceScreen();

        // set up quiet hours check box
        final CheckBoxPreference enableQuietHours =
                (CheckBoxPreference) findPreference("quiet_hours_preference");
        final PreferenceCategory quiethours = (PreferenceCategory) findPreference("quiet_hours");

        // If enable quiet hours preference is not checked, don't show quiet hours settings
        if (!enableQuietHours.isChecked()) {
            screen.removePreference(quiethours);
        }

        // Set a listener
        enableQuietHours.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newVal) {
                // Get the boolean value of the checkbox
                final boolean enableQuietHours = (Boolean) newVal;
                PreferenceScreen screen = getPreferenceScreen();
                // If the quiet hours are not enabled, remove the preferences
                if (!enableQuietHours) {
                    screen.removePreference(quiethours);
                }
                // Otherwise, show the preferences
                else {
                    screen.addPreference(quiethours);
                }
                return true;
            }

        });
    }


}
