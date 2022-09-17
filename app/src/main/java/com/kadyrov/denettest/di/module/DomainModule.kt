package com.kadyrov.denettest.di.module

import com.kadyrov.denettest.data.DeNetRepositoryImpl
import com.kadyrov.denettest.di.annotation.AppScope
import com.kadyrov.denettest.domain.DeNetRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

	@[Binds AppScope]
	fun bindRepository(impl: DeNetRepositoryImpl): DeNetRepository
}