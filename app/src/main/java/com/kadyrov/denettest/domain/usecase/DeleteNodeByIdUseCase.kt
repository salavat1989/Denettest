package com.kadyrov.denettest.domain.usecase

import com.kadyrov.denettest.domain.DeNetRepository
import javax.inject.Inject

class DeleteNodeByIdUseCase @Inject constructor(
	private val repository: DeNetRepository,
) {

	suspend operator fun invoke(id: Long) = repository.deleteNode(id)
}