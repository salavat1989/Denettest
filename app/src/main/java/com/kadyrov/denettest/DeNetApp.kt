package com.kadyrov.denettest

import android.app.Application
import android.content.Context
import com.kadyrov.denettest.di.ApplicationComponent
import com.kadyrov.denettest.di.DaggerApplicationComponent

class DeNetApp: Application(){

	val appComponent: ApplicationComponent by lazy {
		DaggerApplicationComponent.factory().create(applicationContext)
	}
}

val Context.appComponent: ApplicationComponent
	get() = when (this) {
		is DeNetApp -> appComponent
		else -> applicationContext.appComponent
	}