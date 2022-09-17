package com.kadyrov.denettest.di.module

import com.kadyrov.denettest.di.subcomponent.UIComponent
import dagger.Module

@Module(
	subcomponents = [
		UIComponent::class,
	],
	includes = [
		DataModule::class,
		DomainModule::class
	])
interface AppModule