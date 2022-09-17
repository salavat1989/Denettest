package com.kadyrov.denettest.data.mapper

import com.kadyrov.denettest.data.database.model.NodeDb
import com.kadyrov.denettest.domain.entity.Node
import javax.inject.Inject

class NodeMapper @Inject constructor() {

	fun mapNodeToNodeDb(node: Node): NodeDb =
		NodeDb(
			id = node.id,
			parent_id = node.parent?.id,
			name = node.name ?: node.name()
		)

	fun mapNodeDbToNode(nodeDb: NodeDb): Node =
		Node(
			parent = nodeDb.parent?.let { parent ->
				mapNodeDbToNode(parent)
			},
			children = mapNodeDbListToNodeList(nodeDb.children),
			name = nodeDb.name,
			id = nodeDb.id,
		)

	private fun mapNodeDbListToNodeList(nodeDbList: List<NodeDb>): List<Node> =
		nodeDbList.map { mapNodeDbToNode(it) }

	fun mapNodeDbToNodeNullable(nodeDb: NodeDb?): Node? =
		nodeDb?.let {
			mapNodeDbToNode(it)
		}
}