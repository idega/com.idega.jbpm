package com.idega.jbpm;

import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;

public class IWBundleStarter implements IWBundleStartable {

	@Override
	public void start(IWBundle starterBundle) {
		Example example = new Example();
		example.test();
	}

	@Override
	public void stop(IWBundle starterBundle) {
	}

}