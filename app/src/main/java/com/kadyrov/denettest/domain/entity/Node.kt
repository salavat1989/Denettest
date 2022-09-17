package com.kadyrov.denettest.domain.entity

import com.kadyrov.denettest.utils.toByteArray
import java.io.Serializable
import kotlin.random.Random

data class Node(
	val parent: Node? = null,
	@Transient
	val children: List<Node>,

	@Transient
	val id: Long = DEFAULT_ID,

	@Transient
	var name: String? = null,

	) : Serializable {

	val data:Int = Random.nextInt(0, 1_000_000_000)

	fun name() = this.toByteArray()
		.takeLast(20)
		.map { String.format("%02X", it).lowercase() }
		.joinToString (separator = "")

	companion object{
		const val DEFAULT_ID = 0L
	}
}