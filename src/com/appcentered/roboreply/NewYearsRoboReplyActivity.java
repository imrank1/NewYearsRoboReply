package com.appcentered.roboreply;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NewYearsRoboReplyActivity extends Activity {
    public static final String PREFS_NAME = "preferencesFile";

	Button toggleButton = null;
	boolean on;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        toggleButton = (Button) findViewById(R.id.toggleButton);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean isItOn = settings.getBoolean("on", false);
        on = isItOn;
        setToggleText();
        
    }
    
    
    
    private void setToggleText ( )
    {
    	if (on)
    	{
    		toggleButton.setText("Auto Reply is On!");
    	}
    	else {
    		toggleButton.setText("Auto Reply is Off!");
    	}
    }
    
    public void toggleState (View view )
    {
    	
    	
    	String state = (String) toggleButton.getText();
    	Toast toast = null;
    	if ( state.equals("Auto Reply is Off!"))
    	{
    		on = true;
    		toast = Toast.makeText(this, "SMS messages regarding Happy New Year will be replied to automatically!", 8);
    	}
    	else {
    		on = false;
    		toast = Toast.makeText(this, "Auto Reply has been turned off", 5);

    	}
    	
    	
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("on", on);

        // Commit the edits!
        editor.commit();
    	setToggleText();
    	toast.show();
    }
}