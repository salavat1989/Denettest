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
import com.kadyrov.denettest.extensions.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class NodeViewModel @Inject constructor(
	private val getRootNodeUseCase: GetRootNodeUseCase,
	private val addNodeUseCase: AddNodeUseCase,
	private val getNodeUseCase: GetNodeUseCase,
	private val deleteNodeByIdUseCase: DeleteNodeByIdUseCase,
) : ViewModel() {

	private val _currentNode = MutableLiveData<Node>()
	val currentNode: LiveData<Node> = _currentNode
	var node: Node? = null

	private val _loading = MutableLiveData<Boolean>()
	val loading: LiveData<Boolean> = _loading

	private val _errorMessage = SingleLiveEvent<Throwable>()
	val errorMessage: LiveData<Throwable> = _errorMessage

	private val handler = CoroutineExceptionHandler { _, throwable ->
		_errorMessage.value = throwable
		_loading.value = false
	}

	init {
		_loading.value = true
		viewModelScope.launch(handler) {
			val firstNode = getRootNodeUseCase()
			_currentNode.value = firstNode
			_loading.value = false
		}

	}

	fun checkParentNodeExist() = _currentNode.value?.parent != null

	fun openNodeById(id: Long) {
		_loading.value = true
		viewModelScope.launch(handler) {
			val node = getNodeUseCase(id)
			_currentNode.value = node
			_loading.value = false
		}
	}

	fun addChild() {
		viewModelScope.launch(handler) {
			_currentNode.value?.let { node ->
				addNodeUseCase(Node(node, emptyList()))
				_currentNode.value?.let {
					openNodeById(it.id)
				}
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

	fun deleteNodeById(id: Long) {
		_loading.value = true
		viewModelScope.launch(handler) {

			deleteNodeByIdUseCase(id)
			_currentNode.value?.let { openNodeById(it.id) }
			_loading.value = false
		}
	}
}

