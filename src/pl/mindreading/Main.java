package pl.mindreading;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

public class Main extends Activity {

	public static final String INTENT_KEY = "pl.mindreading";
	public static boolean pref_vibration;
	public static boolean pref_sound;
	public static String APP_ID = "204525877";
	public static String DEV_ID = "104683993";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, DEV_ID, APP_ID, true);
        new StartAppAd(this);
		StartAppAd.showSplash(this, savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    
    public void startGuessNumber(View v)
    {
    	Intent intent = new Intent(this.getApplicationContext(), GuessNumber.class);
    	startActivity(intent);
    }
    
    public void startMindReader(View v)
    {
    	Intent intent = new Intent(this.getApplicationContext(), CrystalBall.class);
    	startActivity(intent);
    }
    
    public void startNumbers(View v)
    {
    	Intent intent = new Intent(this.getApplicationContext(), Numbers.class);
    	startActivity(intent);
    }
    
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
        view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            unbindDrawables(((ViewGroup) view).getChildAt(i));
            }

        	((ViewGroup) view).removeAllViews();


        }
    }
    
    @Override
    protected void onDestroy()
    {
	    super.onDestroy();
	
		try
		{
		    unbindDrawables(findViewById(R.id.RootView));
		    System.gc();
		}catch(Exception e) {}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.settings:
        	Intent ustawienia = new Intent(getBaseContext(), Ustawienia.class);
        	startActivity(ustawienia);
        	
        	return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    private void getPrefs()
    {
        // Get the xml/preferences.xml preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        pref_vibration = prefs.getBoolean("pref_vibration", true);
        pref_sound = prefs.getBoolean("pref_sound", true);
    }


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		getPrefs();
	}
    
}
