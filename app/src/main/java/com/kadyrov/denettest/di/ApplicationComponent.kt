package com.kadyrov.denettest.di

import android.content.Context
import com.kadyrov.denettest.di.annotation.AppScope
import com.kadyrov.denettest.di.module.AppModule
import com.kadyrov.denettest.di.subcomponent.UIComponent
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface ApplicationComponent {

	fun getUiComponentFactory(): UIComponent.Factory

	@Component.Factory
	interface Factory {
		fun create(
			@BindsInstance context: Context,
		): ApplicationComponent
	}
}