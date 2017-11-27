package org.sorribas.cordova.android.locale;

import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

public class CordovaAndroidSetLocale extends CordovaPlugin {
  private Context context;
  private android.app.Activity activity;

  @Override
  public void initialize(final CordovaInterface cordova, final CordovaWebView webView) {
    super.initialize(cordova, webView);
    this.context = (Context) cordova.getActivity().getApplication();
    this.activity = cordova.getActivity();
  }

  @Override
  public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
    if (action.equals("setLocale")) {
      Handler mainHandler = new Handler(context.getMainLooper());

      final String lang = data.getString(0);
      Runnable myRunnable = new Runnable() {
        @Override 
        public void run() {
          try {
            Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");                
            Object am=activityManagerNative.getMethod("getDefault").invoke(activityManagerNative); 
            Object config=am.getClass().getMethod("getConfiguration").invoke(am); 
            config.getClass().getDeclaredField("locale").set(config, new Locale(lang)); 
            config.getClass().getDeclaredField("userSetLocale").setBoolean(config, true); 

            am.getClass().getMethod("updateConfiguration", Configuration.class).invoke(am,config); 

          } catch (Exception e) {

          }
        }
      };
      mainHandler.post(myRunnable);
      return true;

    } else {

      return false;

    }
  }
}
