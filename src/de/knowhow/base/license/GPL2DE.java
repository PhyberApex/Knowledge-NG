package de.knowhow.base.license;

import de.knowhow.base.Constants;

public class GPL2DE extends License {

	public GPL2DE() {
		licenseText = Constants.readInternFileToString("/de/knowhow/resource/license/GPLv2_DE.txt"); 
	}
}
