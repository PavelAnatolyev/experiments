package com.pa.android.tutor.countdown;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class CountdownActivity extends Activity {

	private static final int MILLIS_PER_SECOND = 1000;
	private static final int SECONDS_TO_COUNTDOWN = 30;
	private TextView countdownDisplay;
	private CountDownTimer timer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        countdownDisplay = (TextView) findViewById(R.id.time_display_box);
        Button startButton = (Button) findViewById(R.id.startbutton);
        startButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				try
				{
					showTimer(SECONDS_TO_COUNTDOWN * MILLIS_PER_SECOND);
				}
				catch (NumberFormatException e)
				{
				
				}
			}
        	 
        });
    }

    protected void showTimer(int countdownMillis) {
		if (timer != null)
		{
			timer.cancel();
		}
		timer = new CountDownTimer(countdownMillis, MILLIS_PER_SECOND) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				countdownDisplay.setText("counting down: " +
			millisUntilFinished / MILLIS_PER_SECOND);				
			}
			
			@Override
			public void onFinish() {
				countdownDisplay.setText("KABOOM!!!");
				
			}
		}.start();		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_countdown, menu);
        return true;
    }

    
}
