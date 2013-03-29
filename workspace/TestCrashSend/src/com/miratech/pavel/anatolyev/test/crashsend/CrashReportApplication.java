package com.miratech.pavel.anatolyev.test.crashsend;

import android.app.Application;
import org.acra.*;
import org.acra.annotation.*;

@ReportsCrashes(formKey = "dGVRbWM4Yl9oUlRBRzhLTW9tci1xUXc6MQ")
public class CrashReportApplication extends Application {
	@Override
	public void onCreate()
	{
		// The following line triggers the initialization of ACRA
		ACRA.init(this);
		super.onCreate();
	}
}
