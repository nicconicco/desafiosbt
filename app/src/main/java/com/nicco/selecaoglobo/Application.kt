package com.nicco.selecaoglobo

import android.app.Application
import android.content.Context
import com.nicco.api.ApiImpl
import com.nicco.api.okhttp.GloboOkHttpImpl
import com.nicco.api.utils.Either
import com.nicco.business.mapper.ProductMapperImpl
import com.nicco.business.model.ProductModelUi
import com.nicco.business.usecase.UseCaseProductsImpl
import com.nicco.datasource.DatasourceImpl
import com.nicco.repository.RepositoryCache
import com.nicco.repository.RepositoryCacheImpl
import com.nicco.repository.RepositoryImpl
import com.nicco.selecaoglobo.utils.GloboViewModelFactory
import com.nicco.selecaoglobo.viewmodel.GloboProductsViewModel
import okhttp3.OkHttpClient

class GloboApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: GloboApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    object DI {
        private lateinit var di: GloboProductsViewModel
        fun saveViewModel(globoProductsViewModel: GloboProductsViewModel) {
            di = globoProductsViewModel
        }

        fun getDi(): GloboProductsViewModel {
            return di
        }
    }

    private lateinit var useCase: UseCaseProductsImpl
    lateinit var context: Context

    fun startApplication(app: Context) {
        context = app
    }

    fun initUseCase() {
        useCase = UseCaseProductsImpl(
            ProductMapperImpl(),
            RepositoryImpl(
                DatasourceImpl(
                    ApiImpl(
                        GloboOkHttpImpl(
                            OkHttpClient()
                        ),
                        applicationContext
                    )
                ),
                RepositoryCacheImpl()
            )
        )
    }

    suspend fun findListProduct(): Either<String, List<ProductModelUi>> {
        return useCase.findListProducts()
    }
}