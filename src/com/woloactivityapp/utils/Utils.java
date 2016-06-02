/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.woloactivityapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

public class Utils {

    private Utils() {}

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static void showMessage(String strTitle, String strMessage, Activity parent) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(parent);
		dialog.setTitle(strTitle);
		dialog.setMessage(strMessage);
		dialog.setIcon(android.R.drawable.ic_dialog_info);
		dialog.setNegativeButton("Ok", null);
		dialog.show();
	}
    
    public static String getPreferenceValue(Context context,String name,String key) {
		if(key == null) {
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
		String value = preferences.getString(key, "");
		return value;
	}
    
    public static void savePreferenceValue(Context context, String name, String key, String value) {
		if(key == null) {
			return;
		}
		if(value == null) {
			value = "";
		}
		
		SharedPreferences preferences = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putString(key, value);
		edit.commit();
	}
    
    public static void cleanPreferences(Context context,String name) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.clear();
		edit.commit();
	}

}
