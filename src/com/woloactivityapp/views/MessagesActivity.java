package com.woloactivityapp.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.connection.UserAPI;
import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.Contest;
import com.woloactivityapp.constants.User;
import com.woloactivityapp.main.NavigationBaseActivity;
import com.woloactivityapp.main.R;

public class MessagesActivity extends NavigationBaseActivity {
	/** Called when the activity is first created. */
	public CustomTextView 					titleCustomTextView_ = null;
	private LinearLayout 					chatgrouplayout = null;
	private Button 							chatsendbtn = null;
	private EditText 						chatEditText = null;
	private ScrollView 						scrollView = null;
	private RelativeLayout					rootlayout = null;
	public static MessagesActivity			messagesActivity_ = null; 			
	private float						    screenScale_ = 0.0f;
	public static Contest					currentContest_ = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setLayoutId(this, R.layout.activity_messages);
		super.onCreate(savedInstanceState);
		messagesActivity_ = this;
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				super.handleMessage(msg);
				baseProgress.hide();
				switch (msg.what) {
				case Constants.MSG_SUCCESS: {

				}
					break;
				case Constants.MSG_SEND: {
					String val = (String) (msg.obj);
					sendMessageFunc(val);
				}
					break;
				case Constants.MSG_RECEIVE: {

					String message = (String) (msg.obj);
					receiveMessageFunc(message);

				}
					break;
				}
			}

		};
		initView();
		setListener();
		initData();

	}
	public void onResume() {
		super.onResume();
		if (rootlayout != null)
			rootlayout.postInvalidate();
	}
	public void initView() {
		screenScale_ = getWidth() / 800.0f;
    	titleCustomTextView_ = (CustomTextView) findViewById(R.id.titleTextView);
    	RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) titleCustomTextView_.getLayoutParams();
		param.setMargins(0, 0, 0, 0);
		param.width = getWidth();
		param.height = (int) (100 * screenScale_); 
		titleCustomTextView_.setLayoutParams(param);
		titleCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60 * screenScale_);
		
		chatgrouplayout = (LinearLayout) findViewById(R.id.chatgrouplayout);
		chatsendbtn = (Button) findViewById(R.id.chatsendbtn);
		if (chatsendbtn != null)
			chatsendbtn.setEnabled(false);
		chatEditText = (EditText) findViewById(R.id.chatEditText);
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		
    	rootlayout = (RelativeLayout) findViewById(R.id.rootlayout);

    }

	public void setListener() {
		if (chatsendbtn != null)
			chatsendbtn.setOnClickListener(this);
		if (chatEditText != null)
			chatEditText.addTextChangedListener(textWatcherInput);
	}
	TextWatcher textWatcherInput = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {

			String somestring = chatEditText.getText().toString();
			if (somestring.length() == 0)
				chatsendbtn.setEnabled(false);
			else
				chatsendbtn.setEnabled(true);

		}
	};
	public void initData() {

	}
	public void sendScroll() {
		final Handler mHandler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						scrollView.fullScroll(View.FOCUS_DOWN);
					}
				});
			}
		}).start();
	}
	public void sendMessageFunc(final String msg) {
		LayoutInflater inflater = LayoutInflater.from(MessagesActivity.this);
		RelativeLayout itemlayout = (RelativeLayout) inflater.inflate(R.layout.chatsenditemlayout, null);
		chatgrouplayout.addView(itemlayout);
		EditText msgTextView = (EditText) itemlayout.findViewById(R.id.msgTextView);
		msgTextView.setText(msg);
		final User user= Constants.getCurrentUser(getParent());
		sendScroll();
		
		new Thread() {
			public void run() {
				if (currentContest_ != null)
					UserAPI.sendMessage(currentContest_.getName(), user.getEmail(), msg);
			}
		}.start();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(chatEditText.getWindowToken(), 0);

	}
	public void receiveMessageFunc(final String msg) {
		LayoutInflater inflater = LayoutInflater.from(MessagesActivity.this);
		RelativeLayout itemlayout = (RelativeLayout) inflater.inflate(R.layout.chatreceiveitemlayout, null);
		chatgrouplayout.addView(itemlayout);
		EditText msgTextView = (EditText) itemlayout.findViewById(R.id.msgTextView);
		msgTextView.setText(msg);
		sendScroll();

	}
	public void onClick(View view) {
		super.onClick(view);
		int viewId = view.getId();
		switch (viewId) {
		case R.id.chatsendbtn: {
			String message = chatEditText.getText().toString();
			Message msg = new Message();
			msg.obj = message;
			msg.what = Constants.MSG_SEND;
			handler.sendMessage(msg);
			chatEditText.setText("");
		}
			break;
		}
	}

}