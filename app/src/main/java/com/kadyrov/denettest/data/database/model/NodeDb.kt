package com.kadyrov.denettest.data.database.model

import androidx.room.*

@Entity(tableName = NodeDb.TABLE_NAME,
	foreignKeys = [
		ForeignKey(
			entity = NodeDb::class,
			parentColumns = [NodeDb.ID],
			childColumns = [NodeDb.PARENT_ID],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class NodeDb(

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = ID)
	val id: Long,

	@ColumnInfo(name = PARENT_ID, index = true)
	val parent_id: Long?,

	val name: String,

	) {

	@Ignore
	var parent: NodeDb? = null

	@Ignore
	var children: List<NodeDb> = emptyList()

	companion object {
		const val TABLE_NAME = "node"
		const val ID = "id"
		const val PARENT_ID = "parent_id"
	}
}