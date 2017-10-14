package com.avegbird.barrage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BarrageSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	public static final String TAG = "BarrageSurfaceView";
	public static final boolean D = true;
	
	private Context context = null;//上下文，获取一些全局变量和系统服务
	private SurfaceHolder holder;//控制句柄
	private float speed = 0.1f;//弹幕移动速度：屏幕宽度%/秒
    private MyDrawThread randerTherad;
	
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
		//声明渲染线程
        if (randerTherad != null)
            return;
        randerTherad = new MyDrawThread(holder);
        randerTherad.setSurfaceMsg(this.getWidth(),this.getHeight(),speed);

        randerTherad.start();
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
        if (randerTherad == null)
            return;
        randerTherad.softstop();
	}
	
	/**
	 * 主要工作类
	 * 绘画线程
	 * 其中包含了画布处理，弹幕渲染，缓存画布渲染等操作
	 * */
	public class MyDrawThread extends Thread{
		private SurfaceHolder holder = null;//surfaceholder实例，用来控制surfaceview绘制
		private List<TaxtTheme> taxtThemes = null;//保存未绘制完成和未绘制的taxt（弹幕）实例
		private int MaxFrame = 60;//最大刷新帧率
		
		private boolean is_run = true;//控制此绘图线程是否存活，又称软退出
		private int showType = 1;//弹幕渲染规则，1弹幕不覆盖显示，2弹幕覆盖显示

		private int[][] is_clean = null; //保存渲染行信息，①决定第N行的弹幕是否可继续渲染其他弹幕；②保存渲染行高度
		private int width = 0;//屏幕宽度
		private int heigth = 0;//屏幕高度
        private float speed = 0.1f;//弹幕速度
		/**
		 * 构造方法
		 * @param holder surfaceholder实例，用来控制surfaceview绘制
		 * @param taxtThemes 保存未绘制完成和未绘制的taxt（弹幕）实例
		 * */
		public MyDrawThread(SurfaceHolder holder, List<TaxtTheme> taxtThemes) {
			this.holder = holder;
			this.taxtThemes = taxtThemes;
		}
		
		public MyDrawThread(SurfaceHolder holder) {
			this(holder, null);
		}

		//获取屏幕信息和全局信息
		public void setSurfaceMsg(int width, int height, float speed){
			is_clean = new int[height/5][2];
			this.width = width;
			this.heigth = height;
            this.speed = speed;
		}
		/**
		 * 线程必须要实现的方法
		 * 用start()来启动该方法，此方法将运行在独立线程中，不会阻塞主线程 
		 * */
		@Override
		public void run() {
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setAntiAlias(true);
			long last_time = System.currentTimeMillis();
			while(is_run) {
				long use_time = System.currentTimeMillis() - last_time;
				if (1000/MaxFrame - use_time > 0)//保证最大帧率
					try {
						sleep((1000/MaxFrame - use_time));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				if (holder == null) continue;//如果holder为空，则一直等待
				if (taxtThemes == null || taxtThemes.size() < 1) continue;//如果所有弹幕已经绘制完成，等待新的弹幕进入
				synchronized (holder) {
					Canvas lockCanvas = holder.lockCanvas();//获取surfaceview canvas
					lockCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//清空画布
					draw_cache_Canvas(lockCanvas,paint);
				}
			}
		}
		/**
		 * 绘制弹幕到画布
		 * */
		private void draw_cache_Canvas(Canvas canvas, Paint paint) {
			synchronized (taxtThemes) {//保证只有此方法单独操作所有弹幕
				for(TaxtTheme i : taxtThemes) {
					//循环取得单个taxt对象
					paint.setAlpha((int)(255*i.getTextApth()));//向画笔设置透明度
					paint.setTextSize(i.getTextFont());//设置字体
					paint.setColor(i.getTextColor());//设置颜色
				}
			}
			return;
		}
		/**
		 * 添加弹幕
		 * */
		public void setBarrage(String text) {
			synchronized(taxtThemes){
				if (taxtThemes == null)
					taxtThemes = new ArrayList<TaxtTheme>();
				taxtThemes.add(new TaxtTheme(text));
			}
		}
		/**
		 * 添加弹幕
		 * */
		public void setBarrage(TaxtTheme text) {
			synchronized(taxtThemes){
				if (taxtThemes == null)
					taxtThemes = new ArrayList<TaxtTheme>();
				taxtThemes.add(text);
			}
		}

		public void softstop(){is_run=false;}
	}
	/**
	 * 弹幕信息封装模块
	 * 注意！增加一种滚动方向，就需要增加一个画布
	 * */
	public class TaxtTheme {
		public static final int ROLLING_NORMAL = 1;//自右向左滚动
		public static final int ROLLING_RIGHT2LIFT = 2;//自左向右滚动
		public static final int ROLLING_KEEPING = 0;//不滚动，居中显示

		private String Text = "";//所发文字
		private int TextFont = 10;//字体大小 最小为5
		private int TextColor = 0xFFFFFF;//字体颜色
		private float TextApth = 1;//透明度 1不透明，0透明
		private int TextSpacing = 5;//间距默认间距为5dxp
		private int TextPosition = 0;//弹幕显示位置，0整个屏幕，1屏幕上三分之一，2屏幕中间三分之一，3屏幕下三分之一
		private int RollingType = 1;//弹幕滚动方式，1正常自右向左滚动，0不滚动，2，自左往右滚动
		private int TextPriority = 10;//弹幕显示优先级，默认同等优先级先来在下，高优先级MAX=99在最上面，最低优先级0在最下面

		private int head_x = 0;//此条弹幕当前头X位置
		private int head_y = 0;//此条弹幕当前头Y位置
		private int text_length = 0;//弹幕长度
		private int text_heigh = 0;//弹幕高度
		
		public TaxtTheme(String text) {
			if (text == null){
				Text = "";
			}else {
				Text = text;
			}
			head_x = 0;
			head_y = 0;
		}
		public int getTextFont() {
			return TextFont;
		}
		public void setTextFont(int textFont) {
			if (textFont < 5)
				textFont = 5;
			TextFont = textFont;
			getTextSize();
		}
		public int getTextColor() {
			return TextColor;
		}
		public void setTextColor(int textColor) {
			TextColor = textColor;
		}
		public float getTextApth() {
			return TextApth;
		}
		public void setTextApth(float textApth) {
			TextApth = textApth;
		}
		public int getTextSpacing() {
			return TextSpacing;
		}
		public void setTextSpacing(int textSpacing) {
			TextSpacing = textSpacing;
		}
		public int getTextPosition() {
			return TextPosition;
		}
		public void setTextPosition(int textPosition) {
			TextPosition = textPosition;
		}
		public int getRollingType() {
			return RollingType;
		}
		public void setRollingType(int rollingType) {
			RollingType = rollingType;
		}
		public int getTextPriority() {
			return TextPriority;
		}
		public void setTextPriority(int textPriority) {
			TextPriority = textPriority;
		}
		public int getHead_x() {
			return head_x;
		}
		public void setHead_x(int head_x) {
			this.head_x = head_x;
		}
		public int getHead_y() {
			return head_y;
		}
		public void setHead_y(int head_y) {
			this.head_y = head_y;
		}
		/**
		 * 获取text的长度和高度
		 * */
		private void getTextSize(){
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setTextSize(TextFont);
			text_length = (int) paint.measureText(Text);//获取总长
			Paint.FontMetrics fontMetrics = paint.getFontMetrics();
			text_heigh = (int) (fontMetrics.ascent + fontMetrics.leading + fontMetrics.descent);//获取字体高度
		}
		
	}
	
}
