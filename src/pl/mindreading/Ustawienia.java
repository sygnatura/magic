package pl.mindreading;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class Ustawienia extends PreferenceActivity {

	 @Override
     protected void onCreate(Bundle savedInstanceState)
	 {
		 super.onCreate(savedInstanceState);
             //getPreferenceManager().setSharedPreferencesName("android");
             addPreferencesFromResource(R.xml.ustawienia);
     }
	 

}
