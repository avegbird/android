package com.avegbird.barrage;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BarrageSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
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
	 * �첽��������������
	 * ����surfaceview������Ҫ���������̣߳��ʻ��ƴ��뽫�ڴ����
	 * */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ��Ļ��Ϣ��װģ��
	 * */
	public class TaxtTheme {
		private int TextFont = 10;//�����С
		private int TextColor = 0xFFFFFF;//������ɫ
		private int TextApth = 1;//͸���� 1��͸����0͸��
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
