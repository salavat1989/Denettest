package com.kadyrov.denettest.data.database.room

import androidx.room.*
import com.kadyrov.denettest.data.database.model.NodeDb

@Dao
interface NodeDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTreeNode(nodeDb: NodeDb)

	@Query("SELECT * FROM ${NodeDb.TABLE_NAME} WHERE ${NodeDb.ID} =:id")
	fun getTreeNodeById(id: Long): NodeDb

	@Query("SELECT * FROM ${NodeDb.TABLE_NAME} WHERE ${NodeDb.PARENT_ID} =:parent_id")
	fun getNodeListByParentId(parent_id: Long?): List<NodeDb>

	@Query("DELETE FROM ${NodeDb.TABLE_NAME} WHERE ${NodeDb.ID} =:id")
	fun deleteById(id: Long)

	@Transaction
	fun getFullNodeByPId(id: Long): NodeDb {
		val node = getTreeNodeById(id)
		node.children = getNodeListByParentId(id)
		node.parent_id?.let { parent_id ->
			node.parent = getTreeNodeById(parent_id)
		}
		return node
	}

	@Query("SELECT * FROM ${NodeDb.TABLE_NAME} WHERE ${NodeDb.PARENT_ID} IS NULL LIMIT 1")
	fun getRootNode(): NodeDb?

	@Transaction
	fun getFullRootNode(): NodeDb? {
		val node = getRootNode()
		return node?.let {
			getFullNodeByPId(it.id)
		}
	}
}