package com.miratech.pavel.anatolyev.test.game;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private long lastClick = 0;
	private Bitmap bloodBmp;
	private List<TempSprite> temps =  new ArrayList<TempSprite>();;

	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameLoopThread(this);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder arg0) {
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry)
				{
					try
					{
						gameLoopThread.join();
						retry = false;
					}
					catch (InterruptedException e)
					{

					}
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				createSprites();
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}



			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});
		bloodBmp = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
	}

	private void createSprites() {
		sprites.add(createSprite(R.drawable.bad1));
		sprites.add(createSprite(R.drawable.bad2));
		sprites.add(createSprite(R.drawable.bad3));
		sprites.add(createSprite(R.drawable.bad4));
		sprites.add(createSprite(R.drawable.bad5));
		sprites.add(createSprite(R.drawable.bad6));
		sprites.add(createSprite(R.drawable.good1));
		sprites.add(createSprite(R.drawable.good2));
		sprites.add(createSprite(R.drawable.good3));
		sprites.add(createSprite(R.drawable.good4));
		sprites.add(createSprite(R.drawable.good5));
		sprites.add(createSprite(R.drawable.good6));
		
//		sprites.add(createSprite(R.drawable.bad1));
//		sprites.add(createSprite(R.drawable.bad2));
//		sprites.add(createSprite(R.drawable.bad3));
//		sprites.add(createSprite(R.drawable.bad4));
//		sprites.add(createSprite(R.drawable.bad5));
//		sprites.add(createSprite(R.drawable.bad6));
//		sprites.add(createSprite(R.drawable.good1));
//		sprites.add(createSprite(R.drawable.good2));
//		sprites.add(createSprite(R.drawable.good3));
//		sprites.add(createSprite(R.drawable.good4));
//		sprites.add(createSprite(R.drawable.good5));
//		sprites.add(createSprite(R.drawable.good6));
		
//		for (int i = 0; i < 10; ++i)
//		{
//			sprites.add(createSprite(R.drawable.pic1));
//			sprites.add(createSprite(R.drawable.pic2));
//			sprites.add(createSprite(R.drawable.pic3));
//			sprites.add(createSprite(R.drawable.pic4));
//		}
	}
	
	private Sprite createSprite(int resource) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		return new Sprite(this, bmp);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);
		for (int i = temps.size() - 1; i >= 0; --i)
		{
			temps.get(i).onDraw(canvas);
		}
		for (Sprite sprite : sprites)
		{
			sprite.onDraw(canvas);
		}
		
		if (sprites.size() <= 0)
		{
			createSprites();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (System.currentTimeMillis() - lastClick > 500)
		{
			synchronized (getHolder())
			{
				lastClick = System.currentTimeMillis();
				float x = event.getX();
				float y = event.getY();
				for (int i = sprites.size() - 1; i >= 0; --i)
				{
					Sprite sprite = sprites.get(i);
					if (sprite.isCollision(x, y))
					{
						sprites.remove(i);
						temps.add(new TempSprite(temps, this, x, y, bloodBmp));
						break;
					}
				}
			}
		}
		return super.onTouchEvent(event);
	}

}
