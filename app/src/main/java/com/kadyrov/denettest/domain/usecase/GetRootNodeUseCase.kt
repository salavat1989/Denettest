package com.kadyrov.denettest.domain.usecase

import com.kadyrov.denettest.domain.DeNetRepository
import com.kadyrov.denettest.domain.entity.Node
import javax.inject.Inject

class GetRootNodeUseCase @Inject constructor(
	private val repository: DeNetRepository,
	private val addRootNodeUseCase: AddRootNodeUseCase,
) {

	suspend operator fun invoke(): Node = repository.getRootNode() ?: addRootNodeUseCase()
}