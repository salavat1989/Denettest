package com.kadyrov.denettest.data

import com.kadyrov.denettest.data.datasource.LocalDataSource
import com.kadyrov.denettest.data.mapper.NodeMapper
import com.kadyrov.denettest.di.annotation.IoDispatcherQualifier
import com.kadyrov.denettest.domain.DeNetRepository
import com.kadyrov.denettest.domain.entity.Node
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeNetRepositoryImpl @Inject constructor(
	private val mapper: NodeMapper,
	private val localDataSource: LocalDataSource,
	@IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
) : DeNetRepository {

	override suspend fun addNode(node: Node) {
		localDataSource.addNode(mapper.mapNodeToNodeDb(node))
	}

	override suspend fun getRootNode(): Node? = withContext(ioDispatcher) {
		mapper.mapNodeDbToNodeNullable(
			localDataSource.getFirstNode()
		)
	}

	override suspend fun getNode(id: Long): Node = withContext(ioDispatcher) {
		mapper.mapNodeDbToNode(
			localDataSource.getNode(id)
		)
	}

	override suspend fun deleteNode(id: Long) = withContext(ioDispatcher) {
		localDataSource.deleteNode(id)
	}
}