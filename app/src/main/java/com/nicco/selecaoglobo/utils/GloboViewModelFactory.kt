package com.nicco.selecaoglobo.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicco.api.ApiImpl
import com.nicco.api.okhttp.GloboOkHttpImpl
import com.nicco.business.mapper.ProductMapperImpl
import com.nicco.business.usecase.UseCaseProducts
import com.nicco.business.usecase.UseCaseProductsImpl
import com.nicco.datasource.DatasourceImpl
import com.nicco.repository.RepositoryCacheImpl
import com.nicco.repository.RepositoryImpl
import com.nicco.selecaoglobo.GloboApp
import com.nicco.selecaoglobo.GloboApp.Companion.applicationContext
import com.nicco.selecaoglobo.viewmodel.GloboProductsViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient


class GloboViewModelFactory(
) :
    ViewModelProvider.Factory {

    lateinit var context: Context
    lateinit var globoProductsViewModel: GloboProductsViewModel

    fun setContextApp(contextApp: Context) {
        context = contextApp
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GloboProductsViewModel::class.java)) {
            if (!::globoProductsViewModel.isInitialized) {
                globoProductsViewModel =
                    GloboProductsViewModel(
                        Dispatchers.IO,
                        useCaseProductsImpl()
                    )
            }

            GloboApp.DI.saveViewModel(globoProductsViewModel)
            return globoProductsViewModel as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

    private fun useCaseProductsImpl(): UseCaseProductsImpl {
        return UseCaseProductsImpl(
            ProductMapperImpl(),
            repositoryImpl()
        )
    }

    private fun repositoryImpl(): RepositoryImpl {
        return RepositoryImpl(
            DatasourceImpl(
                apiImpl()
            ),
            RepositoryCacheImpl()
        )
    }

    private fun apiImpl(): ApiImpl {
        return ApiImpl(
            GloboOkHttpImpl(
                OkHttpClient()
            ),
            applicationContext()
        )
    }
}