package com.kadyrov.denettest.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kadyrov.denettest.R
import com.kadyrov.denettest.appComponent
import com.kadyrov.denettest.databinding.FragmentNodeBinding
import com.kadyrov.denettest.domain.entity.Node
import com.kadyrov.denettest.domain.entity.exception.RootNodeAddException
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

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setCustomBackPress()
	}

	private fun setCustomBackPress() {
		requireActivity().onBackPressedDispatcher.addCallback(this) {
			isEnabled = viewModel.checkParentNodeExist()
			if (isEnabled) {
				viewModel.openParent()
			} else {
				requireActivity().onBackPressedDispatcher.onBackPressed()
			}
		}
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
		val onLongClick: (Long) -> Unit = { id ->
			openDeleteConfirmationDialog(id)
		}
		nodeChildsAdapter = NodeChildAdapter(onClick, onLongClick)
		binding.rvChildNodes.adapter = nodeChildsAdapter
	}

	private fun initViewModelObserve() {
		viewModel.currentNode.observe(viewLifecycleOwner, ::showNode)

		viewModel.loading.observe(viewLifecycleOwner, ::setProgress)

		viewModel.errorMessage.observe(viewLifecycleOwner, ::showErrorMessage)
	}

	private fun initClickListeners() {
		binding.btnAddChild.setOnClickListener {
			viewModel.addChild()
		}
		binding.parentCard.setOnClickListener {
			viewModel.openParent()
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
			}
			?: let {
				binding.ivArrowRight.isVisible = false
				binding.tvParentNode.text = getString(R.string.root_node_label)
			}
	}

	private fun setProgress(progress: Boolean) {
		binding.mainProgress.isVisible = progress
		if (progress) {
			binding.mainLayout.alpha = PROGRESS_ALPHA
		} else {
			binding.mainLayout.alpha = 1f
		}
	}

	private fun showErrorMessage(throwable: Throwable) {

		when (throwable) {
			is RootNodeAddException -> showMessage(R.string.root_node_add_error)
			else -> showMessage(R.string.unknown_error)
		}
	}

	private fun showMessage(@StringRes strId: Int) {
		Snackbar
			.make(binding.root, strId, Snackbar.LENGTH_SHORT)
			.show()
	}

	private fun openDeleteConfirmationDialog(id: Long) {
		MaterialAlertDialogBuilder(requireContext())
			.setTitle(getString(R.string.delete_node))
			.setMessage(getString(R.string.delete_confirmation_message))
			.setNegativeButton(getString(R.string.cancel)) { _, _ ->
			}
			.setPositiveButton(getString(R.string.ok)) { _, _ ->
				viewModel.deleteNodeById(id)
			}
			.show()
	}

	override fun onDestroyView() {
		nodeChildsAdapter = null
		_binding = null
		super.onDestroyView()
	}

	companion object {
		const val PROGRESS_ALPHA = 0.5f
		fun newInstance() = NodeFragment()
	}
}
