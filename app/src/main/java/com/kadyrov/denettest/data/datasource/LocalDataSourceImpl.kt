package com.kadyrov.denettest.data.datasource

import com.kadyrov.denettest.data.database.model.NodeDb
import com.kadyrov.denettest.data.database.room.NodeDao
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
	private val dao: NodeDao,
) : LocalDataSource {

	override suspend fun addNode(node: NodeDb) = dao.insertTreeNode(node)

	override suspend fun getFirstNode(): NodeDb? = dao.getFullRootNode()

	override suspend fun getNode(id: Long): NodeDb = dao.getFullNodeByPId(id)

	override suspend fun deleteNode(id: Long) = dao.deleteById(id)
}