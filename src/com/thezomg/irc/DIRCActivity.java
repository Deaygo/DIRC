package com.thezomg.irc;

import com.urbanairship.UAirship;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class DIRCActivity extends Activity {

	EditText etMessage;
	TextView tvChat;
	ScrollView scChat;

	
	
	@Override
	protected void onStart() {
		super.onStart();
		UAirship.shared().getAnalytics().activityStarted(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		UAirship.shared().getAnalytics().activityStopped(this);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		etMessage = (EditText) findViewById(R.id.etMessage);
		tvChat = (TextView) findViewById(R.id.tvChat);
		scChat = (ScrollView) findViewById(R.id.scMessage);

		etMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
					if (!etMessage.getText().toString().trim().equals("")) {
						DIRCActivity.this.addChatMessage("Username", etMessage.getText().toString());
						return true;
					}
				}
				return false;
			}
		});
	}

	void addChatMessage(String username, String message) {
		CharSequence str = tvChat.getText();

		SpannableStringBuilder builder = new SpannableStringBuilder(str);

		SmartColorString text = new SmartColorString("<" + username + "> " + message);
		
		//SpannableString text = new SpannableString("<" + username + "> " + message);
		
		text.setForeground(username, Color.RED);

		//text.setSpan(new ForegroundColorSpan(Color.RED), 1, username.length() + 1, 0);

		if (!str.toString().trim().equals("")) {
			builder.append("\n");
		}
		builder.append(text);

		tvChat.setText(builder, BufferType.SPANNABLE);
		
		scChat.postDelayed(new Runnable() {
			
			public void run() {
				scChat.fullScroll(ScrollView.FOCUS_DOWN);
				etMessage.requestFocus();
			}
		}, 100L);
		etMessage.setText("");
	}
	
	class SmartColorString extends SpannableString {

		public SmartColorString(CharSequence source) {
			super(source);
		}
		
		public void setForeground(String text, int colour) {
			int start = this.toString().indexOf(text);
			int end = start + text.length();
			this.setSpan(new ForegroundColorSpan(colour), start, end, 0);
		}
	}
}