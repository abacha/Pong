/*-----------------------------------------------------------------------

 gameSpace2d (C) Copyright 2008-2011 Andre Santee
 http://www.asantee.net/gamespace/
 http://groups.google.com/group/gamespacelib

    This file is part of gameSpace2d.

    gameSpace2d is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    gameSpace2d is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with gameSpace2d. If not, see
    <http://www.gnu.org/licenses/>.

-----------------------------------------------------------------------*/

package net.asantee.gs2d;

import java.io.File;

import net.asantee.gs2d.audio.MediaStreamListener;
import net.asantee.gs2d.audio.SoundCommandListener;
import net.asantee.gs2d.io.AccelerometerListener;
import net.asantee.gs2d.io.KeyEventListener;
import net.asantee.gs2d.io.NativeCommandListener;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

public class GS2DActivity extends KeyEventListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setupExternalStorageDirectories();
	}

	@Override
	protected void onStart() {
		super.onStart();
		soundCmdListener = new SoundCommandListener(this);
		accelerometerListener = new AccelerometerListener(this);
		mediaStreamListener = new MediaStreamListener(this);
		surfaceView = new GL2JNIView(this, retrieveApkPath(), soundCmdListener, accelerometerListener, this, customCommandListener, mediaStreamListener);
		setContentView(surfaceView);
		/*
		 * this.runOnUiThread(new Runnable() { public void run() { Log.i("GS2D",
		 * GS2DJNI.runOnUIThread("null")); } });
		 */
	}

	public void setCustomCommandListener(NativeCommandListener commandListener) {
		this.customCommandListener = commandListener;
	}

	@Override
	protected void onPause() {
		super.onPause();
		accelerometerListener.onPause();
		surfaceView.onPause();

		surfaceView.destroy();
		soundCmdListener.clearAll();
		mediaStreamListener.stop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		accelerometerListener.onResume();
		surfaceView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mediaStreamListener.release();
	}
	
	String retrieveApkPath() {
		String apkFilePath = null;
		ApplicationInfo appInfo = null;
		PackageManager packMgmr = getPackageManager();
		try {
			appInfo = packMgmr.getApplicationInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to locate assets, aborting...");
		}
		apkFilePath = appInfo.sourceDir;
		return (apkFilePath);
	}

	private void setupExternalStorageDirectories() {
		externalStoragePath = Environment.getExternalStorageDirectory() + "/Android/data/" + this.getPackageName() + "/files/";
		{
			File dir = new File(externalStoragePath + LOG_DIRECTORY_NAME);
			dir.mkdirs();
		}
		{
			File dir = new File(Environment.getExternalStorageDirectory() + "/" + NON_CONTEXT_LOG_DIRECTORY_NAME);
			dir.mkdirs();
		}
	}
	
	public String getExternalStoragePath() {
		return externalStoragePath;
	}

	public static void toast(final String str, final Activity context) {
		context.runOnUiThread(new Runnable() {
			public void run() {
				Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
				toast.show();
			}
		});
	}

	private String externalStoragePath;
	private MediaStreamListener mediaStreamListener;
	private AccelerometerListener accelerometerListener;
	private SoundCommandListener soundCmdListener;
	private static final String LOG_DIRECTORY_NAME = "log";
	private static final String NON_CONTEXT_LOG_DIRECTORY_NAME = "gs2dlog";
	private GL2JNIView surfaceView;
	private NativeCommandListener customCommandListener = null;
}