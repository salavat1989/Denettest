package com.kadyrov.denettest.di.module

import android.content.Context
import androidx.room.Room
import com.kadyrov.denettest.data.database.model.NodeDb
import com.kadyrov.denettest.data.database.room.NodeDataBase
import com.kadyrov.denettest.data.datasource.LocalDataSource
import com.kadyrov.denettest.data.datasource.LocalDataSourceImpl
import com.kadyrov.denettest.di.annotation.AppScope
import com.kadyrov.denettest.di.annotation.IoDispatcherQualifier
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
interface DataModule {

	@[Binds AppScope]
	fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

	companion object {

		@[Provides AppScope]
		fun provideDao(context: Context) =
			Room.databaseBuilder(context, NodeDataBase::class.java, NodeDb.TABLE_NAME)
				.fallbackToDestructiveMigration().build().dao()

		@[Provides IoDispatcherQualifier]
		fun provideCoroutineDispatcher() = Dispatchers.IO
	}
}