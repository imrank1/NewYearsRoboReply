package com.appcentered.roboreply;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class SmsReplier extends BroadcastReceiver
{
	private static final String matchPattern = ".*h*a*p*y n*e*w* y*e*a*r*.*";
	Pattern pattern = Pattern.compile(matchPattern);

    public static final String PREFS_NAME = "preferencesFile";

    @Override
    public void onReceive(Context context, Intent intent) 
    {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String str = "";
        String textContents = "";
        String phoneNumber = "";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                str += "SMS from " + msgs[i].getOriginatingAddress(); 
                phoneNumber +=  msgs[i].getOriginatingAddress();
                str += " :";
                str += msgs[i].getMessageBody().toString();
                textContents +=  msgs[i].getMessageBody().toString();
                str += "\n";        
            }
            Matcher matcher = pattern.matcher(textContents.toLowerCase());
            boolean matchFound = matcher.matches();
            if ( matchFound)
            {
            	sendSMS(phoneNumber, "Happy New Year to You as Well!",context);
            }
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }                         
    }
    
    
    private void sendSMS(String phoneNumber, String message,Context context)
    {      
    	SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
         boolean isItOn = settings.getBoolean("on", false);
    	if ( isItOn)
    	{
	    	@SuppressWarnings("deprecation")
			SmsManager sm = SmsManager.getDefault();
	    	String number = phoneNumber;
	    	sm.sendTextMessage(number, null, message, null, null); 
    	}
    }    
    
}
