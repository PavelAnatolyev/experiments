package com.miratech.pavel.anatolyev.test.game;

import android.graphics.Canvas;

public class GameLoopThread extends Thread {
	private static final int FPS = 10;
	private GameView view;
	private boolean running = false;
	
	public GameLoopThread(GameView view)
	{
		this.view = view;
	}
	
	public void setRunning (boolean run)
	{
		running = run;
	}

	@Override
	public void run() {
		long tickPS = 1000 / FPS;
		long startTime;
		long sleepTime;
		while (running)
		{
			Canvas c = null;
			startTime = System.currentTimeMillis();
			try
			{
				c = view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					view.onDraw(c);
				}
			}
			finally
			{
				if (c != null)
				{
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime = tickPS - (System.currentTimeMillis() - startTime);
			if (sleepTime <= 0)
			{
				sleepTime = 10;
			}
			try
			{	
				sleep(sleepTime);
			}
			catch (Exception ex)
			{				
			}
		}
	}
	
	

}
