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

package net.asantee.gs2d.audio;

import java.io.IOException;

import net.asantee.gs2d.GS2DActivity;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class MediaStreamManager {

	private static MediaPlayer mediaPlayer = null;
	private Activity activity;
	private AssetManager assets;
	private AudioManager manager;
	private static float globalVolume = 1.0f;
	private String currentTrack = "";

	public MediaStreamManager(Activity activity) {
		this.activity = activity;
		this.manager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
		this.assets = activity.getAssets();
	}

	public static void setGlobalVolume(float volume) {
		globalVolume = volume;
		setVolume(volume);
	}

	public static float getGlobalVolume() {
		return globalVolume;
	}

	public boolean load(String fileName) {
		// dummy (will be loaded on play time)
		return true;
	}

	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop(); // Hammertime!
		}
	}

	public void release() {
		if (mediaPlayer != null) {
			mediaPlayer.release();
		}
		mediaPlayer = null;
	}

	public static void setVolume(float volume) {
		if (mediaPlayer != null) {
			mediaPlayer.setVolume(volume * globalVolume, volume * globalVolume);
		}
	}

	private boolean mayKeepSong(String fileName) {
		return (fileName.equals(currentTrack) && mediaPlayer.isPlaying());
	}

	public void play(String fileName, float volume, float speed, boolean loop) {
		if (mediaPlayer != null) {
			if (mayKeepSong(fileName)) {
				return;
			} else {
				mediaPlayer.stop();
			}
		}
		currentTrack = fileName;

		float streamVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume /= manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		streamVolume *= MediaStreamManager.getGlobalVolume() * volume;

		String relativeFileName = SoundEffectManager.removePrefix(fileName, SoundEffectManager.PREFIX);
		AssetFileDescriptor afd;
		try {
			afd = assets.openFd(relativeFileName);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			mediaPlayer.prepare();
			if (afd != null) {
				try {
					afd.close();
				} catch (IOException e) {
					GS2DActivity.toast(fileName + " couldn't close asset", activity);
				}
			}
		} catch (IOException e) {
			GS2DActivity.toast(fileName + " file not found", activity);
			return;
		}

		mediaPlayer.setLooping(loop);
		setVolume(streamVolume);
		mediaPlayer.start();
	}
}
