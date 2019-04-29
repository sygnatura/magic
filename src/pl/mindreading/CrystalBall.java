package pl.mindreading;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class CrystalBall extends Activity {

	private GridView gridView;
	private int losowaIkona;
	private final int ACTIVITY_RESULT = 0;
	
	
	final int IKONY[] = {R.drawable.ikona1, R.drawable.ikona2, R.drawable.ikona3, R.drawable.ikona4,
			R.drawable.ikona5, R.drawable.ikona6, R.drawable.ikona7, R.drawable.ikona8, R.drawable.ikona9,
			R.drawable.ikona10, R.drawable.ikona11, R.drawable.ikona12, R.drawable.ikona13, R.drawable.ikona14,
			R.drawable.ikona15,R.drawable.ikona16, R.drawable.ikona17, R.drawable.ikona18, R.drawable.ikona19,
			R.drawable.ikona20, R.drawable.ikona21, R.drawable.ikona22, R.drawable.ikona23, R.drawable.ikona24,
			R.drawable.ikona25, R.drawable.ikona26, R.drawable.ikona27, R.drawable.ikona28, R.drawable.ikona29,
			R.drawable.ikona30};
	
	public int losoweIkony[] = new int[100];
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mind_reader);
        
        gridView = (GridView) findViewById(R.id.gridView1);
        
        Object data = getLastNonConfigurationInstance();
       	tworzIkony(data);
       	// 1 uruchomienie pokaz pomoc
       	if(data == null) pokazPomoc(null);

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
    
    public void tworzIkony(Object data)
    {
    	
    	if(data != null)
    	{
    		losoweIkony = (int[]) data;
    		losowaIkona = losoweIkony[9];
    	}
    	else
    	{
            int ilosc = IKONY.length;
            Random r = new Random();
            losowaIkona = IKONY[r.nextInt(ilosc)];
            
            for(int i = 0; i < losoweIkony.length; i++)
            {
            	if(i % 9 == 0 && i != 0 && i != 90 && i != 99) losoweIkony[i] = losowaIkona;
            	else losoweIkony[i] = IKONY[r.nextInt(ilosc)];
            }
            
    	}

        
        gridView.setAdapter(new ImageAdapter(CrystalBall.this, losoweIkony));
    }
    
    public void pokazPomoc(View v)
    {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CrystalBall.this);
 
			// set dialog message
		alertDialogBuilder
				.setMessage(Html.fromHtml(getString(R.string.crystalball_opis)))
				.setCancelable(false)
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						dialog.cancel();
					}
				  });
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		// show it
		alertDialog.show();
    }
    
    public void wyjdz(View v)
    {
    	this.finish();
    }
    
    public void pokazWynik(View v)
    {
    	Intent intent = new Intent(this, CrystalBallResult.class);
    	intent.putExtra(Main.INTENT_KEY, losowaIkona);
    	startActivityForResult(intent, this.ACTIVITY_RESULT);

    }
    

    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == ACTIVITY_RESULT && resultCode == Activity.RESULT_OK)
		{
			tworzIkony(null);
		}
	}

	@Override
    public Object onRetainNonConfigurationInstance() {
        return losoweIkony;
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

    public class ImageAdapter extends BaseAdapter {
    	private Context context;
    	private final int[] ikony;
     
    	public ImageAdapter(Context context, int[] ikony) {
    		this.context = context;
    		this.ikony = ikony;
    	}
     
    	public View getView(int position, View convertView, ViewGroup parent) {
     
    		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     
    		View v;
    		
    		int pozycja = (getCount()-1) - position;
     
    		if (convertView == null) {
    			v = new View(context);
    			// get layout from mobile.xml
    			v = inflater.inflate(R.layout.wiersz_mindreader, null);
    		} else {
    			v = (View) convertView;
    		}
    		
			TextView m_Liczba = (TextView) v.findViewById(R.id.liczba);
			if(pozycja<10) m_Liczba.setText("  "+String.valueOf(pozycja));
			else m_Liczba.setText(String.valueOf(pozycja));
 
			ImageView m_Ikona = (ImageView) v.findViewById(R.id.ikona);
 
			m_Ikona.setImageResource(ikony[pozycja]);

     
    		return v;
    	}
     
    	@Override
    	public int getCount() {
    		return ikony.length;
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
}
