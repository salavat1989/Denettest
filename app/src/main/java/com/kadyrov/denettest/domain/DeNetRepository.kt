package com.kadyrov.denettest.domain

import com.kadyrov.denettest.domain.entity.Node

interface DeNetRepository{

	suspend fun addNode(node:Node)

	suspend fun getRootNode(): Node?

	suspend fun getNode(id:Long): Node

	suspend fun deleteNode(id:Long)

}