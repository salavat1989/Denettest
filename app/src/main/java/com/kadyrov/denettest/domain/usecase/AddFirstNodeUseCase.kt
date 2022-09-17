package com.kadyrov.denettest.domain.usecase

import com.kadyrov.denettest.domain.DeNetRepository
import com.kadyrov.denettest.domain.entity.Node
import javax.inject.Inject

class AddFirstNodeUseCase @Inject constructor(
	private val repository: DeNetRepository,
	private val addNodeUseCase: AddNodeUseCase,
) {
	suspend operator fun invoke(): Node {

		addNodeUseCase.invoke(
			Node(null, emptyList())
		)
		val addedNode = repository.getRootNode()
		return addedNode ?: throw RuntimeException("FirstNodeAddError")
	}
}