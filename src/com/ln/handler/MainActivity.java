package com.ln.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	ViewPager ee;
	PagerAdapter pa;
	private ProgressBar pbProgress;
	private TextView tvProgress;
	private Button btnStart;
	private InnerHandler handler;
	private static final int MESSAGE_UPDATE_START = 0;
	private static final int MESSAGE_UPDATE_FINISH = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		btnStart.setOnClickListener(this);
		handler = new InnerHandler();
	}

	private void initViews() {
		pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
		tvProgress = (TextView) findViewById(R.id.tv_progress);
		btnStart = (Button) findViewById(R.id.btn_start);

	}

	private class InnerThread extends Thread {
		@Override
		public void run() {
			for (int i = 1; i <= 100; i++) {
				Message msg = Message.obtain();
				msg.what = MESSAGE_UPDATE_START;
				msg.arg1 = i;
				handler.sendMessage(msg);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			Message msg = Message.obtain();
//			msg.what = MESSAGE_UPDATE_FINISH;
//			handler.sendMessage(msg);
			handler.sendEmptyMessage(MESSAGE_UPDATE_FINISH);

		}
	}

	private class InnerHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_UPDATE_START:
				int progress = msg.arg1;
				pbProgress.setProgress(progress);
				tvProgress.setText(progress + "/100");
				break;

			case MESSAGE_UPDATE_FINISH:
				btnStart.setEnabled(true);
				break;
			}

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		InnerThread thread = new InnerThread();
		thread.start();
		btnStart.setEnabled(false);
	}

}
