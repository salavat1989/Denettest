package com.kadyrov.denettest.domain.usecase

import com.kadyrov.denettest.domain.DeNetRepository
import com.kadyrov.denettest.domain.entity.Node
import javax.inject.Inject

class GetNodeUseCase @Inject constructor(
	private val repository: DeNetRepository,
) {

	suspend operator fun invoke(id: Long): Node = repository.getNode(id)
}