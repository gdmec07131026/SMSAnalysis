package com.example.smsanalysis;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsMessage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	EditText et_sms;
	
	MySMSReceiver SMSReceiver ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et_sms=(EditText)this.findViewById(R.id.editText1);
		
		SMSReceiver=new MySMSReceiver();
		IntentFilter intentFilter=new IntentFilter();
		
		intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		
		registerReceiver(SMSReceiver, intentFilter);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(SMSReceiver);
	}




	public class  MySMSReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			
			Object[] pduses= (Object[])intent.getExtras().get("pdus");
		       for(Object pdus: pduses){
		           byte[] pdusmessage = (byte[])pdus;
		           SmsMessage sms = SmsMessage.createFromPdu(pdusmessage);
		           String mobile = sms.getOriginatingAddress();//发送短信的手机号码
		           String content = sms.getMessageBody(); //短信内容
		           Date date = new Date(sms.getTimestampMillis());
		           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		           String time = format.format(date);  //得到发送时间
		           
		           System.out.println("收到了短信"+content);
		           et_sms.setText(mobile+":"+content);
		       }
			
		       System.out.println("over!");
			
		}
		
	}
}
