package com.avegbird.barrage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.nfc.tech.IsoDep;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BarrageSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	public static final String TAG = "BarrageSurfaceView";
	public static final boolean D = true;
	
	private Context context = null;//�����ģ���ȡһЩȫ�ֱ�����ϵͳ����
	private SurfaceHolder holder;//���ƾ��
	private float speed = 0.1f;//��Ļ�ƶ��ٶȣ���Ļ���%/��
	private int MAX_lines = 6;//��Ļ�������
	
	public BarrageSurfaceView(Context context) {
		super(context);
		this.context = context;
		holder = this.getHolder();//��ȡ���ƾ��
		holder.addCallback(this);
		
	}

	/**
	 * surfaceview ��������ʱ����ã���������һЩ��ʼ������
	 * @param holder:���ƾ��
	 * */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * surfaceview ״̬�����ı�ʱ����ã�����ɻ�õ�ǰ��Ļ��ߵȲ���
	 * @param holder:���ƾ��
	 * @param format:���ظ�ʽ
	 * @param width:surfaceview ���
	 * @param height:surfaceview �߶�
	 * */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * surfaceview ������ʱ����ã����澭����һЩ�ͷ���Դ�Ĳ���
	 * @param holder:���ƾ��
	 * */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * ��Ҫ������
	 * �滭�߳�
	 * ���а����˻���������Ļ��Ⱦ�����滭����Ⱦ�Ȳ���
	 * */
	public class MyDrawThread extends Thread {
		private SurfaceHolder holder = null;//surfaceholderʵ������������surfaceview����
		private List<TaxtTheme> taxtThemes = null;//����δ������ɺ�δ���Ƶ�taxt����Ļ��ʵ��
		
		private boolean is_run = true;//���ƴ˻�ͼ�߳��Ƿ���ֳ����˳�
		private int showType = 1;//��Ļ��Ⱦ����1��Ļ��������ʾ��2��Ļ������ʾ
		
		HashMap<Integer,Canvas> canvas = null;//�����������cache canvas
		/**
		 * ���췽��
		 * @param holder surfaceholderʵ������������surfaceview����
		 * @param taxtThemes ����δ������ɺ�δ���Ƶ�taxt����Ļ��ʵ��
		 * */
		public MyDrawThread(SurfaceHolder holder, List<TaxtTheme> taxtThemes) {
			this.holder = holder;
			this.taxtThemes = taxtThemes;
		}
		
		public MyDrawThread(SurfaceHolder holder) {
			this(holder, null);
		}
		
		/**
		 * �̱߳���Ҫʵ�ֵķ���
		 * ��start()�������÷������˷����������ڶ����߳��У������������߳� 
		 * */
		@Override
		public void run() {
			while(is_run) {
				if (holder == null) continue;//���holderΪ�գ���һֱ�ȴ�
				if (taxtThemes == null || taxtThemes.size() < 1) continue;//������е�Ļ�Ѿ�������ɣ��ȴ��µĵ�Ļ����
				draw_cache_Canvas();
				synchronized (holder) {
					Canvas lockCanvas = holder.lockCanvas();//��ȡsurfaceview canvas
				}
			}
		}
		/**
		 * ���Ʊ���ͼ�㲢����
		 * @return HashMap<Integer,Canvas>: ���ػ��滭��
		 * */
		private void draw_cache_Canvas() {
			synchronized (taxtThemes) {//��ֻ֤�д˷��������������е�Ļ
				for(TaxtTheme i : taxtThemes) {
					//ѭ��ȡ�õ���taxt����
				}
			}
			return;
		}
	}
	/**
	 * ��Ļ��Ϣ��װģ��
	 * ע�⣡����һ�ֹ������򣬾���Ҫ����һ������
	 * */
	public class TaxtTheme {
		public static final int ROLLING_NORMAL = 1;//�����������
		public static final int ROLLING_RIGHT2LIFT = 2;//�������ҹ���
		public static final int ROLLING_KEEPING = 0;//��������������ʾ
		
		private int TextFont = 10;//�����С
		private int TextColor = 0xFFFFFF;//������ɫ
		private float TextApth = 1;//͸���� 1��͸����0͸��
		private int TextSpacing = 5;//���Ĭ�ϼ��Ϊ5dxp
		private int TextPosition = 0;//��Ļ��ʾλ�ã�0������Ļ��1��Ļ������֮һ��2��Ļ�м�����֮һ��3��Ļ������֮һ
		private int RollingType = 1;//��Ļ������ʽ��1�����������������0��������2���������ҹ���
		
		public TaxtTheme() {}
		public TaxtTheme(int TextFont,int TextColor,float TextApth) {
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
		
	}
	
	
	
}
