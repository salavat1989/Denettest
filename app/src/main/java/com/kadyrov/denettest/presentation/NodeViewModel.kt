package com.kadyrov.denettest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadyrov.denettest.domain.entity.Node
import com.kadyrov.denettest.domain.usecase.AddNodeUseCase
import com.kadyrov.denettest.domain.usecase.DeleteNodeByIdUseCase
import com.kadyrov.denettest.domain.usecase.GetNodeUseCase
import com.kadyrov.denettest.domain.usecase.GetRootNodeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class NodeViewModel @Inject constructor(
	private val getFirstNodeUseCase: GetRootNodeUseCase,
	private val addNodeUseCase: AddNodeUseCase,
	private val getNodeUseCase: GetNodeUseCase,
	private val deleteNodeByIdUseCase: DeleteNodeByIdUseCase,
) : ViewModel() {

	private val _currentNode = MutableLiveData<Node>()
	val currentNode: LiveData<Node> = _currentNode
	var node: Node? = null

	init {
		viewModelScope.launch {
			val firstNode = getFirstNodeUseCase()
			_currentNode.value = firstNode
		}

	}

	fun openNodeById(id: Long) {
		viewModelScope.launch {
			val node = getNodeUseCase(id)
			_currentNode.value = node
		}
	}

	fun addChild() {
		viewModelScope.launch {
			addNodeUseCase(Node(_currentNode.value, emptyList()))
			_currentNode.value?.let {
				openNodeById(it.id)
			}
		}
	}

	fun openParent() {
		_currentNode.value?.let {
			it.parent?.let { parent ->
				openNodeById(parent.id)
			}
		}
	}

	fun deleteNode() {
		viewModelScope.launch {
			_currentNode.value?.let {
				deleteNodeByIdUseCase(it.id)
				openParent()
			}
		}
	}
}

