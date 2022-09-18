package com.kadyrov.denettest.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kadyrov.denettest.R
import com.kadyrov.denettest.databinding.ItemChildNodeBinding
import com.kadyrov.denettest.domain.entity.Node

class NodeChildAdapter(
	private val onItemClick: ((Long) -> Unit)? = null,
	private val onLongItemClick: ((Long) -> Unit)? = null,
) : ListAdapter<Node, NodeChildAdapter.LoanViewHolder>(LoanDiff) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {

		val binding = ItemChildNodeBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)
		return LoanViewHolder(binding)
	}

	override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
		val loan = getItem(position)
		holder.bind(loan)
	}

	inner class LoanViewHolder(private val binding: ItemChildNodeBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(node: Node) {

			binding.tvChildNode.text = String.format(
				binding.root.context.getString(R.string.node_name_format),
				node.name
			)
			binding.root.setOnClickListener {
				onItemClick?.invoke(node.id)
			}

			binding.root.setOnLongClickListener {
				onLongItemClick?.invoke(node.id)
				true
			}
		}

	}

	object LoanDiff : DiffUtil.ItemCallback<Node>() {

		override fun areItemsTheSame(oldItem: Node, newItem: Node): Boolean =
			oldItem.id == newItem.id

		override fun areContentsTheSame(oldItem: Node, newItem: Node): Boolean = oldItem == newItem
	}
}