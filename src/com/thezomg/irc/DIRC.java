package com.thezomg.irc;

import com.urbanairship.Logger;
import com.urbanairship.UAirship;
import com.urbanairship.push.CustomPushNotificationBuilder;
import com.urbanairship.push.PushManager;
import com.urbanairship.push.PushPreferences;

import android.app.Application;
import android.util.Log;

public class DIRC extends Application {

	@Override
	public void onCreate() {
		UAirship.takeOff(this);
		com.urbanairship.Logger.logLevel = Log.ERROR;
		PushManager.enablePush();
		
		PushPreferences prefs = PushManager.shared().getPreferences();
		Logger.info("DIRC Application onCreate - App APID: " + prefs.getPushId());
		Logger.info("DIRC Application onCreate - C2DM ID: " + prefs.getC2DMId());
		
		CustomPushNotificationBuilder nb = new CustomPushNotificationBuilder();
		
		nb.statusBarIconDrawableId = R.drawable.ic_launcher;
		
		/*nb.layout = R.layout.notification_layout;
		nb.layoutIconDrawableId = R.drawable.ic_launcher; // The icon you want to display
        nb.layoutIconId = R.id.icon; // The icon's layout 'id'
        nb.layoutSubjectId = R.id.subject; // The id for the 'subject' field
        nb.layoutMessageId = R.id.message; // The id for the 'message' field*/

        //set this ID to a value > 0 if you want a new notification to replace the previous one
        //nb.constantNotificationId = 100;

        //set this if you want a custom sound to play
        //nb.soundUri = Uri.parse("android.resource://"+this.getPackageName()+"/" +R.raw.cat);

        PushManager.shared().setIntentReceiver(IntentReceiver.class);
        
        // Set the builder
        //PushManager.shared().setNotificationBuilder(nb);
	}
}
