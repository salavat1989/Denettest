package com.kadyrov.denettest.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kadyrov.denettest.data.database.model.NodeDb

@Database(entities = [NodeDb::class], version = 1, exportSchema = false)
abstract class NodeDataBase : RoomDatabase() {

	abstract fun dao(): NodeDao
}