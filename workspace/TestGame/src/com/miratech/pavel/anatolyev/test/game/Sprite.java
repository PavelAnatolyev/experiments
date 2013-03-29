package com.miratech.pavel.anatolyev.test.game;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 3;
	static final private int DEFAULT_SPEED = 5;
	int [] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};
	private int x = 0;
	private int y = 0;
	private int xSpeed = DEFAULT_SPEED;
	private GameView gameView;
	private Bitmap bmp;
	private int currentFrame = 0;
	private int width = 0;
	private int height = 0;
	private int ySpeed;
	
	public Sprite(GameView gameView, Bitmap bmp)
	{
		this.gameView = gameView;
		this.bmp = bmp;
		this.width = bmp.getWidth() / BMP_COLUMNS;
		this.height = bmp.getHeight() / BMP_ROWS;
//		this.width = bmp.getWidth();
//		this.height = bmp.getHeight();
		Random rnd = new Random();
		x = rnd.nextInt(gameView.getWidth() - width);
		y = rnd.nextInt(gameView.getHeight() - height);
		xSpeed = rnd.nextInt(10) - DEFAULT_SPEED;
		ySpeed = rnd.nextInt(10) - DEFAULT_SPEED;
	}
	
	private void update()
	{
		if (x > gameView.getWidth() - width - xSpeed || x + xSpeed < 0)
		{
			xSpeed = -xSpeed;
		}
		x = x + xSpeed;
		if (y > gameView.getHeight() - height - ySpeed || y + ySpeed < 0)
		{
			ySpeed = -ySpeed;
		}
		y = y + ySpeed;
		currentFrame = ++currentFrame % BMP_COLUMNS;
	}
	
	public void onDraw(Canvas canvas)
	{
		update();
		int srcX = currentFrame * width;
		int srcY = getAnimationRow() * height;
//		int srcX = 0;
//		int srcY = 0;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(x, y, x + width, y + height);
		canvas.drawBitmap(bmp, src, dst, null);
	}

	private int getAnimationRow() {
		double dirDouble = Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2;
		int direction = (int)Math.round(dirDouble) % BMP_ROWS;
		return DIRECTION_TO_ANIMATION_MAP[direction];
	}

	public boolean isCollision(float x2, float y2) {
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
	}
	
	

}