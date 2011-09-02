package br.com.jera.pong;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.asantee.gs2d.io.NativeCommandListener;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import br.com.jeramobstats.JeraAgent;

public class PongCommandListener implements NativeCommandListener {

	private final Activity activity;
	Properties properties = new Properties();

	public PongCommandListener(Activity activity) {
		this.activity = activity;
		loadProperties();
	}

	private void loadProperties() {
		try {
			// TODO: nao pegar o nome direto por aqui
			properties.load(activity.getAssets().open("util.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parseAndExecuteCommands(String commands) {
		if (!commands.equals("")) {
			String[] commandArray = commands.split("\n");
			for (int t = 0; t < commandArray.length; t++) {
				executeCommand(commandArray[t]);
			}
		}
	}

	private void executeCommand(String cmd) {
		String[] words = cmd.split(" ");
		Log.i("VVZ Command", cmd);
		if (words[0].equals("flurry")) {
			flurryEvent(words);
		} else if (words[0].equals("vibrate")) {
			vibrateEvent(words);
		} else if (words[0].equals("open_url")) {
			urlEvent(words);
		} else if (words[0].equals("open_store")) {
			openStore(words);
		}
	}

	private void urlEvent(final String[] words) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(words[1]));
				activity.startActivity(intent);
			}
		});
	}

	private void openStore(final String[] words) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (properties.get("targetStore").equals("Positivo")) {
					Intent intent = activity.getPackageManager().getLaunchIntentForPackage("br.com.positivo.appstore");
					intent.putExtra("id", "apk-pago-beeavenger0001");
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					activity.startActivity(intent);
				} else {
					Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String) properties.get("paidUrl")));
					activity.startActivity(intent);
				}
			}
		});
	}

	private void vibrateEvent(String[] words) {
		final long time = Long.parseLong(words[1]);
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				((Vibrator) activity.getSystemService(Activity.VIBRATOR_SERVICE)).vibrate(time);
			}
		});
	}

	private void flurryEvent(String[] words) {
		if (words.length > 2) {
			Map<String, String> params = new HashMap<String, String>();
			for (int i = 2; i < words.length; i++) {
				String[] attribute = words[i].split(":");
				params.put(attribute[0], attribute[1]);
				Log.i("GS2D_FLURRY", attribute[0] + " = " + attribute[1]);
			}
			JeraAgent.logEvent(words[1], params);
			Log.i("GS2D_FLURRY", words[1]);

		} else {
			JeraAgent.logEvent(words[1]);
			Log.i("GS2D_FLURRY", words[1]);
		}
	}
}
