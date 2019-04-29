package pl.mindreading;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CrystalBallResult extends Activity {
	
	private Vibrator wibracja;
	private int ikona;
	private Button m_Button;
	private TextView mysliszo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wynik_mindreader);
        
        Bundle extras = getIntent().getExtras();
        
        if(extras != null) ikona = extras.getInt(Main.INTENT_KEY);
        wibracja = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        m_Button = (Button) findViewById(R.id.ponow);
        mysliszo = (TextView) findViewById(R.id.mysliszo);
    }

    public void pokazWynik(View v)
    {
    	if(m_Button.getVisibility() == View.GONE)
    	{
        	((TextView) findViewById(R.id.opis_wynik)).setVisibility(View.GONE);
        	mysliszo.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        	mysliszo.setVisibility(View.VISIBLE);
        	
    	    Handler handler = new Handler(); 
    	    handler.postDelayed(new Runnable() { 
    	         public void run() { 
    	         	ImageView m_Znak = (ImageView) findViewById(R.id.znak);
    	        	m_Znak.setImageResource(ikona);

    	        	wibruj();
    	    	    odtworzDzwiek();
    	    	    m_Znak.startAnimation(AnimationUtils.loadAnimation(CrystalBallResult.this, android.R.anim.fade_in));
    	    	    m_Znak.setVisibility(View.VISIBLE);

    	    	    m_Button.setVisibility(View.VISIBLE);
    	         } 
    	    }, 2000);
    	}
    }
    
    public void ponow(View v)
    {    	
    	Intent data = new Intent();
    	if (getParent() == null) {
    	    setResult(Activity.RESULT_OK, data);
    	} else {
    	    getParent().setResult(Activity.RESULT_OK, data);
    	}
    	finish();

    }
    
    public void odtworzDzwiek()
    {
    	if(Main.pref_sound)
    	{
        	MediaPlayer mp = MediaPlayer.create(this, R.raw.magic_sound);   
            mp.start();

            mp.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mp.release();
                }

            });
    	}
    }
    
    public void wibruj()
    {
    	if(Main.pref_vibration)
    	{
        	long[] pattern = { 0, 500 }; 
        	wibracja.vibrate(pattern, -1);
    	}
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			// jezeli wynik juz jest pokazany to wygeneruj nowe ikony po zamknieciu
			if(m_Button.getVisibility() == View.VISIBLE)
			{
		    	Intent data = new Intent();
		    	if (getParent() == null) {
		    	    setResult(Activity.RESULT_OK, data);
		    	} else {
		    	    getParent().setResult(Activity.RESULT_OK, data);
		    	}
		    	finish();
		    	return true;
			}
		}
		return super.onKeyDown(keyCode, event);
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


    
}
