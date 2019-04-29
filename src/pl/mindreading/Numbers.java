package pl.mindreading;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class Numbers extends Activity {

	
    private TextView opis;
    private EditText pozostaleLiczby;
    private TextView wynik;
    private Button m_restartuj;
    private ImageButton dalej;
    private ImageButton wstecz;
    private ImageView magik;
    private int plansza = 0;
    private String liczba;
    private Integer MagikAnim[] = {R.drawable.mag01, R.drawable.mag02, R.drawable.mag03, R.drawable.mag04, R.drawable.mag05, R.drawable.mag06, R.drawable.mag07};
    
    private Vibrator wibracja;
    
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        opis = (TextView) findViewById(R.id.opis1);
        wynik = (TextView) findViewById(R.id.wynik);
        pozostaleLiczby = (EditText) findViewById(R.id.pozostaleLiczby);
        dalej = (ImageButton) findViewById(R.id.dalej);
        wstecz = (ImageButton) findViewById(R.id.wstecz);
        magik = (ImageView) findViewById(R.id.imageMagik);
        m_restartuj = (Button) findViewById(R.id.restartuj);
        wibracja = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        
        InputFilter[] FilterArray = new InputFilter[2];
        FilterArray[0] = new InputFilter.LengthFilter(3);
        FilterArray[1] = numericFilter;
        pozostaleLiczby.setFilters(FilterArray);
        
        pokazPlansze0();
        new PobieraczZdjec().execute(MagikAnim);
    }
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		
		try
		{
		    unbindDrawables(findViewById(R.id.RootView));
		    System.gc();
		}catch(Exception e) {}
	}
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
    
    

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
   
    public void pokazPlansze0()
    {
    	wstecz.setVisibility(View.GONE);
    	opis.setText(Html.fromHtml(getResources().getString(R.string.plansza0)));
    	opis.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    	magik.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    	opis.setVisibility(View.VISIBLE);
    }
    
    public void pokazPlansze1()
    {
    	wstecz.setVisibility(View.GONE);
    	opis.setText(Html.fromHtml(getResources().getString(R.string.plansza1)));
    	opis.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    	magik.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    	opis.setVisibility(View.VISIBLE);
    }
    
    public void pokazPlansze2()
    {
    	wstecz.setVisibility(View.VISIBLE);
    	opis.setText(Html.fromHtml(getResources().getString(R.string.plansza2)));
    	opis.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    	magik.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    	opis.setVisibility(View.VISIBLE);
    }
    
    public void pokazPlansze3()
    {
    	opis.setText(Html.fromHtml(getResources().getString(R.string.plansza3)));
    	opis.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    	magik.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    	opis.setVisibility(View.VISIBLE);
    }
    
    public void pokazPlansze4()
    {
    	opis.setText(Html.fromHtml(getResources().getString(R.string.plansza4)));
    	opis.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    	magik.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    	opis.setVisibility(View.VISIBLE);
    	pozostaleLiczby.setVisibility(View.VISIBLE);
    }
    
    public void pokazPlansze5()
    {
    	pozostaleLiczby.setVisibility(View.GONE);
    	wstecz.setVisibility(View.GONE);
    	dalej.setVisibility(View.GONE);
    	opis.setText(Html.fromHtml(getResources().getString(R.string.plansza5)));
    	opis.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    	magik.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    	opis.setVisibility(View.VISIBLE);
    	
    	wibruj();
	    Handler handler = new Handler(); 
	    handler.postDelayed(new Runnable() { 
	         public void run() { 
	        	opis.setText("");

	    	    odtworzDzwiek();
	    	    wynik.setText(liczba);
	    	    wynik.startAnimation(AnimationUtils.loadAnimation(Numbers.this, android.R.anim.fade_in));
	    	    wynik.setVisibility(View.VISIBLE);

	    	    m_restartuj.setVisibility(View.VISIBLE);
	         } 
	    }, 4000);
    }
    
    public String znajdzLiczbe(String liczby)
    {
    	int len = liczby.length();
    	if(len > 1)
    	{
    		char cyfry[] = liczby.toCharArray();

    		int suma = 0;
    		for(int i = 0; i <len; i++) suma += Character.getNumericValue(cyfry[i]);

    		if(suma == 9) return String.valueOf(suma);
    		return znajdzLiczbe(String.valueOf(suma));
    	}
    	else
    	{
    		int liczba = Integer.valueOf(liczby);
    		if(liczba == 9) return String.valueOf(liczba);
    		else return String.valueOf(9 - liczba);
    	}
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
            //SystemClock.sleep(300);
    	}
    }
    
    public void odtworzKlik()
    {
    	if(Main.pref_sound)
    	{
        	MediaPlayer mp = MediaPlayer.create(this, R.raw.click);   
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
        	long[] pattern = { 0, 500, 0, 500, 0, 500 }; 
        	wibracja.vibrate(pattern, -1);
    	}
    }
    
    public void dalej(View v)
    {
    	odtworzKlik();
    	switch(plansza)
    	{
			case 0:
				plansza++;
				pokazPlansze1();
				break;
			
    		case 1:
    			plansza++;
    			pokazPlansze2();
    			break;
    
    		case 2:
    			plansza++;
    			pokazPlansze3();
    			break;
    			
    		case 3:
    			plansza++;
    			pokazPlansze4();
    			break;
    			
    		case 4:
    			if(pozostaleLiczby.getText().length() > 0)
    			{
        			liczba = znajdzLiczbe(pozostaleLiczby.getText().toString());
        			plansza++;
        			pokazPlansze5();
    			}
    			break;
    	}
    }
    
    public void wstecz(View v)
    {
    	odtworzKlik();
    	switch(plansza)
    	{
			case 2:
				plansza--;
				pokazPlansze1();
				break;
			
    		case 3:
    			plansza--;
    			pokazPlansze2();
    			break;
    
    		case 4:
    			plansza--;
    			pokazPlansze3();
    			break;
    	}
    }
    
    public void restartuj(View v)
    {
    	wynik.setVisibility(View.GONE);
    	m_restartuj.setVisibility(View.GONE);
    	dalej.setVisibility(View.VISIBLE);
    	wstecz.setVisibility(View.GONE);
    	plansza = 1;
    	wynik.setText("");
    	pozostaleLiczby.setText("");
    	
    	pokazPlansze1();
    }

    InputFilter numericFilter = new InputFilter() {   
    	@Override  
        public CharSequence filter(CharSequence arg0, int arg1, int arg2, Spanned arg3, int arg4, int arg5)  
		{  
    		for (int k = arg1; k < arg2; k++) {   
    			if (!Character.isDigit(arg0.charAt(k))) return "";   
    		}   
		    return null;   
		}   
	}; 

	   public class PobieraczZdjec extends AsyncTask<Integer, Integer, Integer> {

	        protected Integer doInBackground(Integer... urls) {

	            	int ilosc = urls.length;
	    			for(int i = 0; i < ilosc; i++)
	    			{
	    				
	    				publishProgress(urls[i]);
	    				try {
	    					if(i == ilosc-1) i = 0;
	    					if(i == 0 ) Thread.sleep(5000);
	    					else Thread.sleep(100);
	    				} catch (InterruptedException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}
	    				
	    			}
	    			return null;
	        }
	        
	        protected void onProgressUpdate(Integer... img) {
	        	magik.setImageResource(img[0]);
	        }

	        protected void onPostExecute(int img) {
				

	        }



	     }


}
