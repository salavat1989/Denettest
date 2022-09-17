package com.kadyrov.denettest.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kadyrov.denettest.R
import com.kadyrov.denettest.appComponent
import com.kadyrov.denettest.databinding.FragmentNodeBinding
import com.kadyrov.denettest.domain.entity.Node
import com.kadyrov.denettest.presentation.NodeChildAdapter
import com.kadyrov.denettest.presentation.NodeViewModel
import com.kadyrov.denettest.presentation.ViewModelFactory
import javax.inject.Inject

class NodeFragment : Fragment() {

	@Inject
	lateinit var viewModelFactory: ViewModelFactory

	private val viewModel: NodeViewModel by viewModels(factoryProducer = { viewModelFactory })

	private var _binding: FragmentNodeBinding? = null
	private val binding
		get() = _binding ?: throw RuntimeException("FragmentNodeBinding is null")

	private var nodeChildsAdapter: NodeChildAdapter? = null

	override fun onAttach(context: Context) {
		context.appComponent.getUiComponentFactory().create().inject(this)
		super.onAttach(context)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentNodeBinding.inflate(layoutInflater)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initRVAdapter()
		initViewModelObserve()
		initClickListeners()

	}

	private fun initRVAdapter() {
		val onClick: (Long) -> Unit = { id ->
			viewModel.openNodeById(id)
		}
		nodeChildsAdapter = NodeChildAdapter(onClick)
		binding.rvChildNodes.adapter = nodeChildsAdapter
	}

	private fun initViewModelObserve() {
		viewModel.currentNode.observe(viewLifecycleOwner, ::showNode)
	}

	private fun initClickListeners() {
		binding.btnAddChild.setOnClickListener {
			viewModel.addChild()
		}
		binding.ivArrowRight.setOnClickListener {
			viewModel.openParent()
		}
		binding.btnDeleteNode.setOnClickListener {
			viewModel.deleteNode()
		}
	}

	private fun showNode(node: Node) {

		binding.tvNode.text = String.format(getString(R.string.node_name_format), node.name)
		nodeChildsAdapter?.submitList(node.children.toList())
		node.parent
			?.let {
				binding.ivArrowRight.isVisible = true
				binding.tvParentNode.text =
					String.format(getString(R.string.node_name_format), it.name)
				binding.btnDeleteNode.isEnabled = true
			}
			?: let {
				binding.ivArrowRight.isVisible = false
				binding.tvParentNode.text = getString(R.string.root_node_label)
				binding.btnDeleteNode.isEnabled = false
			}
	}


	override fun onDestroyView() {
		nodeChildsAdapter = null
		_binding = null
		super.onDestroyView()
	}

	companion object {
		fun newInstance() = NodeFragment()
	}
}