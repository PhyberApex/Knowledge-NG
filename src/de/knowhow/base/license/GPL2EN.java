package de.knowhow.base.license;

import de.knowhow.base.Constants;


public class GPL2EN extends License{
	
	public GPL2EN(){
		licenseText = Constants.readInternFileToString("/de/knowhow/resource/license/GPLv2_EN.txt"); 
	}
}
