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

import net.asantee.gs2d.io.NativeCommandListener;
import android.app.Activity;

public class MediaStreamListener extends MediaStreamManager implements NativeCommandListener {

	static final String CMD_PLAY_MUSIC = "play_music";
	static final String CMD_LOAD_MUSIC = "load_music";
	static final String CMD_STOP_MUSIC = "stop_music";
	static final String CMD_DELETE_MUSIC = "delete_music";

	public MediaStreamListener(Activity activity) {
		super(activity);
	}

	public void parseAndExecuteCommands(String commands) {
		String[] commandArray = commands.split("\n");
		for (int t = 0; t < commandArray.length; t++) {
			execute(commandArray[t]);
		}
	}

	private void execute(String command) {
		String[] pieces = command.split(" ");
		if (pieces[0].equals(CMD_LOAD_MUSIC)) {
			super.load(pieces[1]);
		} else if (pieces[0].equals(CMD_PLAY_MUSIC)) {
			Float volume = Float.parseFloat(pieces[2]);
			Integer loop = Integer.parseInt(pieces[3]);
			Float speed = Float.parseFloat(pieces[4]);
			super.play(pieces[1], volume, speed, (loop.equals(0)) ? false : true);
		} else if (pieces[0].equals(CMD_STOP_MUSIC)) {
			super.stop();
		}
	}
}
