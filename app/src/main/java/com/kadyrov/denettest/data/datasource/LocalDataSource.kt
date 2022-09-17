package com.kadyrov.denettest.data.datasource

import com.kadyrov.denettest.data.database.model.NodeDb

interface LocalDataSource {

	suspend fun addNode(node: NodeDb)

	suspend fun getFirstNode(): NodeDb?

	suspend fun getNode(id: Long): NodeDb

	suspend fun deleteNode(id: Long)
}