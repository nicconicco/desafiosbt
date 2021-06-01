package com.nicco.selecaoglobo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicco.api.utils.Either
import com.nicco.business.usecase.UseCaseProducts
import com.nicco.business.model.ProductModelUi
import com.nicco.selecaoglobo.utils.Resource
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class GloboProductsViewModel constructor(
    private val io: CoroutineContext,
    private val useCase: UseCaseProducts
) : ViewModel() {

    private val _products = MutableLiveData<Resource<List<ProductModelUi>>>()
    val products: LiveData<Resource<List<ProductModelUi>>> = _products

    fun loadProducts(forceUpdate: Boolean = false) {
        viewModelScope.launch(io) {
            _products.postValue(Resource.loading(null))
            try {
                when (val result = useCase.findListProducts(forceUpdate)) {
                    is Either.Right -> {
                        _products.postValue(Resource.success(result.b))
                    }
                    is Either.Left -> {
                        _products.postValue(Resource.error(result.a, null))
                    }
                }
            } catch (e: Exception) {
                _products.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun reloadListToExpensiveProducts() {
        viewModelScope.launch(io) {
            _products.postValue(Resource.loading(null))
            try {
                when (val result = useCase.reloadListToExpensiveProducts()) {
                    is Either.Right -> {
                        _products.postValue(Resource.success(result.b))
                    }
                    is Either.Left -> {
                        _products.postValue(Resource.error(result.a, null))
                    }
                }
            } catch (e: Exception) {
                _products.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun reloadListToCheaperProducts() {
        viewModelScope.launch(io) {
            _products.postValue(Resource.loading(null))
            try {
                when (val result = useCase.reloadListToCheaperProducts()) {
                    is Either.Right -> {
                        _products.postValue(Resource.success(result.b))
                    }
                    is Either.Left -> {
                        _products.postValue(Resource.error(result.a, null))
                    }
                }
            } catch (e: Exception) {
                _products.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun loadBffProducts() {
        viewModelScope.launch(io) {
            _products.postValue(Resource.loading(null))
            try {
                when (val result = useCase.loadBffProducts()) {
                    is Either.Right -> {
                        _products.postValue(Resource.success(result.b))
                    }
                    is Either.Left -> {
                        _products.postValue(Resource.error(result.a, null))
                    }
                }
            } catch (e: Exception) {
                _products.postValue(Resource.error(e.toString(), null))
            }
        }
    }
}