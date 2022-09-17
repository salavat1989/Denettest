package com.kadyrov.denettest.di.subcomponent

import com.kadyrov.denettest.di.module.PresentationModule
import com.kadyrov.denettest.ui.NodeFragment
import dagger.Subcomponent

@Subcomponent(
	modules = [PresentationModule::class],
)
interface UIComponent {
	fun inject(fragment: NodeFragment)

	@Subcomponent.Factory
	interface Factory {
		fun create(): UIComponent
	}
}