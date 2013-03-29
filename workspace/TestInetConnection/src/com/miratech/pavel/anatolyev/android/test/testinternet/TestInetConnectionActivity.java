package com.miratech.pavel.anatolyev.android.test.testinternet;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.widget.TextView;

public class TestInetConnectionActivity extends Activity {
	private static String cellId = "";
	private static String lac = "";
	private static String mcc = "";
	private static String mnc = "";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GetCurrentCellLocation(this);
        TextView view = (TextView)findViewById(R.id.cellIdTextView);        
        view.setText("Cell ID: " + getCellId());
        
        view = (TextView)findViewById(R.id.locTextView);        
        view.setText("Lac: " + getLac());
        
        view = (TextView)findViewById(R.id.mccTextView);        
        view.setText("Mcc: " + getMcc());
        
        view = (TextView)findViewById(R.id.mncTextView);        
        view.setText("Mnc: " + getMnc());
        
        final String androidId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        
        view = (TextView)findViewById(R.id.androidIdTextView);        
        view.setText("Android ID: " + androidId);
        
        String serial = android.os.Build.SERIAL;  
        
        

        view.setText("Android ID: " + serial);
    }
    
    
    public static void GetCurrentCellLocation(Context context)
	{
		if (context == null)
		{
			return;
		}
		cellId = "";
		lac = "";
		mcc = "";
		mnc = "";
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		CellLocation location = telephonyManager.getCellLocation();
		if (location != null && location instanceof GsmCellLocation)
		{
			cellId = String.valueOf(((GsmCellLocation)location).getCid());
			lac = String.valueOf(((GsmCellLocation)location).getLac());
		}
		
		String operator = telephonyManager.getNetworkOperator();
		mcc = operator.substring(0, 3);
	    mnc = operator.substring(3);
		return; 
	}

	public static String getCellId() {
		return cellId;
	}

	public static String getLac() {
		return lac;
	}

	public static String getMcc() {
		return mcc;
	}

	public static String getMnc() {
		return mnc;
	}
}