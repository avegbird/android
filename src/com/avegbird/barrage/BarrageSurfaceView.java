package com.avegbird.barrage;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BarrageSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
	public static final String TAG = "BarrageSurfaceView";
	public static final boolean D = true;
	
	private Context context = null;//上下文，获取一些全局变量和系统服务
	private SurfaceHolder holder;//控制句柄
	private float speed = 0.1f;//弹幕移动速度：屏幕宽度%/秒
	private int MAX_lines = 6;//弹幕最大行数
	
	public BarrageSurfaceView(Context context) {
		super(context);
		this.context = context;
		holder = this.getHolder();//获取控制句柄
		holder.addCallback(this);
		
	}

	/**
	 * surfaceview 被创建的时候调用，里面运行一些初始化工作
	 * @param holder:控制句柄
	 * */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * surfaceview 状态发生改变时候调用，里面可获得当前屏幕宽高等参数
	 * @param holder:控制句柄
	 * @param format:像素格式
	 * @param width:surfaceview 宽度
	 * @param height:surfaceview 高度
	 * */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * surfaceview 被销毁时候调用，里面经常做一些释放资源的操作
	 * @param holder:控制句柄
	 * */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 异步代码在这里运行
	 * 由于surfaceview绘制需要单独开辟线程，故绘制代码将在此完成
	 * */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 弹幕信息封装模块
	 * */
	public class TaxtTheme {
		private int TextFont = 10;//字体大小
		private int TextColor = 0xFFFFFF;//字体颜色
		private int TextApth = 1;//透明度 1不透明，0透明
		public TaxtTheme() {}
		public TaxtTheme(int TextFont,int TextColor,int TextApth) {
			this.TextApth = TextApth;
			this.TextColor = TextColor;
			this.TextFont = TextFont;
		}
		public int getTextFont() {
			return TextFont;
		}
		public void setTextFont(int textFont) {
			TextFont = textFont;
		}
		public int getTextColor() {
			return TextColor;
		}
		public void setTextColor(int textColor) {
			TextColor = textColor;
		}
		public int getTextApth() {
			return TextApth;
		}
		public void setTextApth(int textApth) {
			TextApth = textApth;
		}
		
	}
}
