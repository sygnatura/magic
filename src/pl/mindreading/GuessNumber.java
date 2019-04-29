package pl.mindreading;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.startapp.android.publish.banner.Banner;

public class GuessNumber extends Activity {

    private RelativeLayout plansza1;
    private RelativeLayout plansza2;
    private RelativeLayout plansza3;
    private RelativeLayout plansza4;
    
    
    private GridView gridView;
    private GridView m_tablica1;
    private GridView m_tablica2;
    private GridView m_tablica3;
    private GridView m_tablica4;
    private GridView m_tablica5;
    private GridView szachownica;
    private TextView wynik1;
    private TextView wynik2;
    private TextView wynik3;
    private Button m_restartuj;
    
    private final int ROZOWY = 0;
    private final int CZERWONY = 1;
    private final int ZIELONY = 2;
    private final int NIEBIESKI = 3;
    private final int CZARNY = 4;
    
    private int wybranyKolor;
    private int wybranaLiczba;
    private Vibrator wibracja;
    private Banner banner;
    
    private int liczby[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
    private int kolory[] = {0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4}; 
    private int falszywe[] = {26, 27, 28, 29, 30};
    
    private int tablica1[] = new int[6];
    private int tablica2[] = new int[6];
    private int tablica3[] = new int[6];
    private int tablica4[] = new int[6];
    private int tablica5[] = new int[6];
    
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guessnumber);
        plansza1 = (RelativeLayout) findViewById(R.id.plansza1);
        plansza2 = (RelativeLayout) findViewById(R.id.plansza2);
        plansza3 = (RelativeLayout) findViewById(R.id.plansza3);
        plansza4 = (RelativeLayout) findViewById(R.id.plansza4);
        gridView = (GridView) findViewById(R.id.gridLiczby);
        m_tablica1 = (GridView) findViewById(R.id.tablica1);
        m_tablica2 = (GridView) findViewById(R.id.tablica2);
        m_tablica3 = (GridView) findViewById(R.id.tablica3);
        m_tablica4 = (GridView) findViewById(R.id.tablica4);
        m_tablica5 = (GridView) findViewById(R.id.tablica5);
        szachownica = (GridView) findViewById(R.id.szachownica);
        wynik1 = (TextView) findViewById(R.id.wynik1);
        wynik2 = (TextView) findViewById(R.id.wynik2);
        wynik3 = (TextView) findViewById(R.id.wynik3);
        m_restartuj = (Button) findViewById(R.id.restartuj);
        wibracja = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        
        banner = (com.startapp.android.publish.banner.Banner) findViewById(R.id.startAppBanner); 

        pokazPlansze1();
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
    
    
    // Implementing Fisher–Yates shuffle
    public void shuffleArray(int[] ar)
    {
    	Random rnd = new Random();
    	for (int i = ar.length - 1; i >= 0; i--)
    	{
    		int index = rnd.nextInt(i + 1);
    		// Simple swap
    		int a = ar[index];
    		ar[index] = ar[i];
    		ar[i] = a;
    	}
    }

    public void sortowanie()
    {
    	int n = liczby.length;
    	do
    	{
		    for(int i = 0; i < n-1; i++)
		    {
		    	if(kolory[i] > kolory[i+1])
		    	{
		    		int kolor_tmp = kolory[i];
		    		int liczby_tmp = liczby[i];
		    		kolory[i] = kolory[i+1];
		    		liczby[i] = liczby[i+1];
		    		kolory[i+1] = kolor_tmp;
		    		liczby[i+1] = liczby_tmp;
		    	}
		    }
  		    n = n-1;
    	}while(n > 1);
    		  
    }
    
    public void pokazPlansze1()
    {
    	// ukryj layout z reklamami
    	banner.hideBanner();
        shuffleArray(liczby);
        shuffleArray(kolory);
        shuffleArray(falszywe);
        gridView.setAdapter(new LiczbyAdapter(this, liczby));
        
        plansza1.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        plansza1.setVisibility(View.VISIBLE);
    }
    
    public void pokazPlansze2()
    {
    	odtworzKlik();
    	banner.showBanner();
    	gridView.setAdapter(null);
    	int sz_kolory[] = {R.color.bezowy, R.color.czarny, R.color.czerwony, R.color.fioletowy, R.color.zolty, R.color.zielony, R.color.szary, R.color.niebieski, R.color.rozowy};
    	shuffleArray(sz_kolory);
    	SzachownicaAdapter adapter = new SzachownicaAdapter(this, sz_kolory);
    	szachownica.setAdapter(adapter);
    	szachownica.setOnItemClickListener(new OnItemClickListener()
    	{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				pokazPlansze3();
			}	
    	});
    	
		plansza1.setVisibility(View.GONE);
		plansza2.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		plansza2.setVisibility(View.VISIBLE);
    }
    
    public void pokazPlansze3()
    {
    	odtworzKlik();
    	sortowanie();
    	
    	int licznik = 0;
    	for(int i = 0; i < tablica1.length; i++)
    	{
			if(i == 5)
			{
				licznik = 0;
	    		tablica5[i] = falszywe[licznik++];
	    		tablica4[i] = falszywe[licznik++];
	    		tablica3[i] = falszywe[licznik++];
	    		tablica2[i] = falszywe[licznik++];
	    		tablica1[i] = falszywe[licznik++];
			}
			else
			{
	    		tablica5[i] = liczby[licznik++];
	    		tablica4[i] = liczby[licznik++];
	    		tablica3[i] = liczby[licznik++];
	    		tablica2[i] = liczby[licznik++];
	    		tablica1[i] = liczby[licznik++];
			}
		}
		shuffleArray(tablica5);
		shuffleArray(tablica4);
		shuffleArray(tablica3);
		shuffleArray(tablica2);
		shuffleArray(tablica1);
		
		m_tablica1.setAdapter(new TabeleAdapter(this, tablica1, R.color.czerwony));
		m_tablica2.setAdapter(new TabeleAdapter(this, tablica2, R.color.zielony));
		m_tablica3.setAdapter(new TabeleAdapter(this, tablica3, R.color.niebieski));
		m_tablica4.setAdapter(new TabeleAdapter(this, tablica4, R.color.rozowy));
		m_tablica5.setAdapter(new TabeleAdapter(this, tablica5, R.color.czarny));
		
		// czyszczenie
		szachownica.setAdapter(null);
		plansza2.setVisibility(View.GONE);
		plansza3.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		plansza3.setVisibility(View.VISIBLE);
    }
    
    public void pokazPlansze4()
    {
    	odtworzKlik();
    	m_tablica1.setAdapter(null);
    	m_tablica2.setAdapter(null);
    	m_tablica3.setAdapter(null);
    	m_tablica4.setAdapter(null);
    	m_tablica5.setAdapter(null);
    	
		plansza3.setVisibility(View.GONE);
		plansza4.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		plansza4.setVisibility(View.VISIBLE);
    }
    
    public void restartuj(View v)
    {
    	m_restartuj.setVisibility(View.GONE);
    	wynik1.setVisibility(View.GONE);
    	wynik2.setVisibility(View.GONE);
    	wynik3.setVisibility(View.GONE);
    	wynik1.setText("");
    	wynik2.setText("");
    	wynik3.setText("");
    	wybranaLiczba = -1;
    	wybranyKolor = -1;
    	pokazPlansze1();
    }
    
   
    public void rozowy(View v)
    {
    	wybranyKolor = ROZOWY;
    	pokazPlansze2();
    }
    public void czerwony(View v)
    {
    	wybranyKolor = CZERWONY;
    	pokazPlansze2();
    }
    public void zielony(View v)
    {
    	wybranyKolor = ZIELONY;
    	pokazPlansze2();
    }
    public void czarny(View v)
    {
    	wybranyKolor = CZARNY;
    	pokazPlansze2();
    }
    public void niebieski(View v)
    {
    	wybranyKolor = NIEBIESKI;
    	pokazPlansze2();
    }
    public void b_tablica1(View v)
    {
    	znajdzLiczbe(tablica1);
    	pokazPlansze4();
    }
    public void b_tablica2(View v)
    {
    	znajdzLiczbe(tablica2);
    	pokazPlansze4();
    }
    public void b_tablica3(View v)
    {
    	znajdzLiczbe(tablica3);
    	pokazPlansze4();
    }
    public void b_tablica4(View v)
    {
    	znajdzLiczbe(tablica4);
    	pokazPlansze4();
    }
    public void b_tablica5(View v)
    {
    	znajdzLiczbe(tablica5);
    	pokazPlansze4();
    }
    public void pokazKula1(View v)
    {
    	if(wynik1.getVisibility() == View.GONE)
    	{
    		wibruj();
    		odtworzDzwiek();
    		wynik1.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    		wynik1.setVisibility(View.VISIBLE);    		
        	// jezeli zadna liczba nie jest jeszcze ustawiona to pokaz wybrana
        	if(wynik2.getText().length() == 0 && wynik3.getText().length() == 0)
        	{
        		wynik1.setText(String.valueOf(wybranaLiczba));
        	}
        	// pokaz dowolna losowa liczbe
        	else
        	{
        		shuffleArray(liczby);
        		int test1 = -1;
        		int test2 = -1;
        		
        		try
        		{
            		test1 = Integer.valueOf(wynik2.getText().toString());
            		test2 = Integer.valueOf(wynik3.getText().toString());
        		}catch(NumberFormatException e) {}
        		
        		for(int i = 0; i < liczby.length; i++)
        		{
        			if(liczby[i] != wybranaLiczba && liczby[i] != test1 && liczby[i] != test2)
        			{
        				wynik1.setText(String.valueOf(liczby[i]));
        				break;
        			}
        		}
        		// pokaz przycisk ponow
        		if(test1 != -1 && test2 != -1) m_restartuj.setVisibility(View.VISIBLE);
        	}
    	}

    }

    public void pokazKula2(View v)
    {
    	if(wynik2.getVisibility() == View.GONE)
    	{
    		wibruj();
    		odtworzDzwiek();
    		wynik2.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    		wynik2.setVisibility(View.VISIBLE);
    		// jezeli zadna liczba nie jest jeszcze ustawiona to pokaz wybrana
        	if(wynik1.getText().length() == 0 && wynik3.getText().length() == 0)
        	{
        		wynik2.setText(String.valueOf(wybranaLiczba));
        	}
        	// pokaz dowolna losowa liczbe
        	else
        	{
        		shuffleArray(liczby);
        		int test1 = -1;
        		int test2 = -1;
        		
        		try
        		{
            		test1 = Integer.valueOf(wynik1.getText().toString());
            		test2 = Integer.valueOf(wynik3.getText().toString());
        		}catch(NumberFormatException e) {}
        		
        		for(int i = 0; i < liczby.length; i++)
        		{
        			if(liczby[i] != wybranaLiczba && liczby[i] != test1 && liczby[i] != test2)
        			{
        				wynik2.setText(String.valueOf(liczby[i]));
        				break;
        			}
        		}
        		// pokaz przycisk ponow
        		if(test1 != -1 && test2 != -1) m_restartuj.setVisibility(View.VISIBLE);

        	}
    	}
    }
    
    public void pokazKula3(View v)
    {
    	if(wynik3.getVisibility() == View.GONE)
    	{
    		wibruj();
    		odtworzDzwiek();
    		wynik3.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    		wynik3.setVisibility(View.VISIBLE);
    		// jezeli zadna liczba nie jest jeszcze ustawiona to pokaz wybrana
        	if(wynik1.getText().length() == 0 && wynik2.getText().length() == 0)
        	{
        		wynik3.setText(String.valueOf(wybranaLiczba));
        	}
        	// pokaz dowolna losowa liczbe
        	else
        	{
        		shuffleArray(liczby);
        		int test1 = -1;
        		int test2 = -1;
        		
        		try
        		{
            		test1 = Integer.valueOf(wynik1.getText().toString());
            		test2 = Integer.valueOf(wynik2.getText().toString());
        		}catch(NumberFormatException e) {}
        		
        		
        		for(int i = 0; i < liczby.length; i++)
        		{
        			if(liczby[i] != wybranaLiczba && liczby[i] != test1 && liczby[i] != test2)
        			{
        				wynik3.setText(String.valueOf(liczby[i]));
        				break;
        			}
        		}
        		// pokaz przycisk ponow
        		if(test1 != -1 && test2 != -1) m_restartuj.setVisibility(View.VISIBLE);
        	}
    	}
    }
    

    public void znajdzLiczbe(int[] tab)
    {

    	for(int i = 0; i < kolory.length; i++)
    	{
    		if(kolory[i] == wybranyKolor)
    		{
    			for(int j = 0; j < tab.length; j++)
    			{
    				if(tab[j] == liczby[i])
    				{
    					wybranaLiczba = liczby[i];
    					break;
    				}
    			}
    		}
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
        	long[] pattern = { 0, 500 }; 
        	wibracja.vibrate(pattern, -1);
    	}
    }
    

    public class LiczbyAdapter extends BaseAdapter {
    	private Context context;
    	private final int[] liczby;
     
    	public LiczbyAdapter(Context context, int[] liczby) {
    		this.context = context;
    		this.liczby = liczby;
    	}
     
    	public View getView(int position, View convertView, ViewGroup parent) {
     
    		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     
    		View v;
    		
    		if (convertView == null) {
    			v = new View(context);
    			// get layout from mobile.xml
    			v = inflater.inflate(R.layout.wiersz_guessnumber, null);
    		} else {
    			v = (View) convertView;
    		}
    		
			TextView m_Liczba = (TextView) v.findViewById(R.id.liczba);
			m_Liczba.setText(String.valueOf(liczby[position]));
			
			switch(kolory[position])
			{
				case ROZOWY:
					m_Liczba.setTextColor(getResources().getColor(R.color.rozowy));
					break;
				case CZERWONY:
					m_Liczba.setTextColor(getResources().getColor(R.color.czerwony));
					break;
				case ZIELONY:
					m_Liczba.setTextColor(getResources().getColor(R.color.zielony));
					break;
				case NIEBIESKI:
					m_Liczba.setTextColor(getResources().getColor(R.color.niebieski));
					break;
				case CZARNY:
					m_Liczba.setTextColor(getResources().getColor(R.color.czarny));
					break;
					
			}

    		return v;
    	}
     
    	@Override
    	public int getCount() {
    		return liczby.length;
    	}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
     
		@Override
		public boolean areAllItemsEnabled()
		{
		    return false;
		}

		@Override
		public boolean isEnabled(int position)
		{
		    return false;
		}
    }

    public class TabeleAdapter extends BaseAdapter {
    	private Context context;
    	private final int[] liczby;
    	private final int kolor;
     
    	public TabeleAdapter(Context context, int[] liczby, int kolor) {
    		this.context = context;
    		this.liczby = liczby;
    		this.kolor = kolor;
    	}
     
    	public View getView(int position, View convertView, ViewGroup parent) {
     
    		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     
    		View v;
    		
    		if (convertView == null) {
    			v = new View(context);
    			// get layout from mobile.xml
    			v = inflater.inflate(R.layout.wiersz_guessntable, null);
     		} else {
    			v = (View) convertView;
    		}
    		
			TextView m_Liczba = (TextView) v.findViewById(R.id.liczba);
			m_Liczba.setText(String.valueOf(liczby[position]));
			m_Liczba.setTextColor(getResources().getColor(kolor));

    		return v;
    	}
     
    	@Override
    	public int getCount() {
    		return liczby.length;
    	}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public boolean areAllItemsEnabled()
		{
		    return false;
		}
		@Override
		public boolean isEnabled(int position)
		{
		    return false;
		}

    }

    public class SzachownicaAdapter extends BaseAdapter {
    	private Context context;
    	private final int[] kolory;
     
    	public SzachownicaAdapter(Context context, int[] kolory) {
    		this.context = context;
    		this.kolory = kolory;
    	}
     
    	public View getView(int position, View convertView, ViewGroup parent) {
     
    		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     
    		View v;
    		
    		if (convertView == null) {
    			v = new View(context);
    			v = inflater.inflate(R.layout.wiersz_szachownica, parent, false);

     		} else {
    			v = (View) convertView;
    		}
    		
    		((LinearLayout) v.findViewById(R.id.linearGuess)).setBackgroundColor(getResources().getColor(kolory[position]));
			//TextView m_Liczba = (TextView) v.findViewById(R.id.liczba);
			//m_Liczba.setText(" ");
 
    		return v;
    	}
     
    	@Override
    	public int getCount() {
    		return kolory.length;
    	}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

    }
}
