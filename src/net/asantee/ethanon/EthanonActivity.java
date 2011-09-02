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

package net.asantee.ethanon;

import net.asantee.gs2d.GS2DActivity;
import net.asantee.gs2d.io.NativeCommandListener;
import android.os.Bundle;

public class EthanonActivity extends GS2DActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		super.setCustomCommandListener(new NativeCommandListener() {

			public void parseAndExecuteCommands(String commands) {
				if (!commands.equals("")) {
					String[] commandArray = commands.split("\n");
					for (int t = 0; t < commandArray.length; t++) {
						// TODO
					}					
				}
			}
		});
	}
}
