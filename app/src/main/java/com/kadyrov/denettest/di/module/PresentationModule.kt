package com.kadyrov.denettest.di.module

import androidx.lifecycle.ViewModel
import com.kadyrov.denettest.di.annotation.ViewModelKey
import com.kadyrov.denettest.presentation.NodeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {

	@[Binds IntoMap ViewModelKey(NodeViewModel::class)]
	fun bindNodeViewModel(impl: NodeViewModel): ViewModel
}