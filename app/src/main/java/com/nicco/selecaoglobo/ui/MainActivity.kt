package com.nicco.selecaoglobo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicco.api.ApiImpl
import com.nicco.api.okhttp.GloboOkHttpImpl
import com.nicco.business.usecase.UseCaseProductsImpl
import com.nicco.datasource.DatasourceImpl
import com.nicco.business.mapper.ProductMapperImpl
import com.nicco.business.model.ProductModelUi
import com.nicco.repository.RepositoryImpl
import com.nicco.selecaoglobo.R
import com.nicco.selecaoglobo.ui.adapter.Interaction
import com.nicco.selecaoglobo.ui.adapter.ListGloboAdapter
import com.nicco.selecaoglobo.utils.GloboViewModelFactory
import com.nicco.selecaoglobo.utils.Status
import com.nicco.selecaoglobo.viewmodel.GloboProductsViewModel
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    private val recycler by lazy { this.findViewById<RecyclerView>(R.id.recycler) }
    private val progressCircular by lazy { this.findViewById<ProgressBar>(R.id.progress_circular) }
    private val listAllProducts by lazy { this.findViewById<Button>(R.id.listAllProducts) }
    private val listProductExpensive by lazy { this.findViewById<Button>(R.id.listProductExpensive) }
    private val listProductCheaper by lazy { this.findViewById<Button>(R.id.listProductCheaper) }
    private val forceUpdate by lazy { this.findViewById<Button>(R.id.forceUpdate) }
    private val showButtons by lazy { this.findViewById<Button>(R.id.showButtons) }
    private val showBff by lazy { this.findViewById<Button>(R.id.showBff) }
    private var stateViewButtons = false

    val viewModel: GloboProductsViewModel by viewModels {
        GloboViewModelFactory()
    }

    private val listGloboAdapter by lazy {
        ListGloboAdapter(object :
            Interaction {
            override fun onClick(item: ProductModelUi) {

            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupButtonListener()
    }

    private fun setupButtonListener() {
        showButtons.setOnClickListener {
            setVisibilityButtons()
        }

        listAllProducts.setOnClickListener {
            viewModel.loadProducts()
        }
        listProductExpensive.setOnClickListener {
            viewModel.reloadListToExpensiveProducts()
        }

        listProductCheaper.setOnClickListener {
            viewModel.reloadListToCheaperProducts()
        }
        forceUpdate.setOnClickListener {
            viewModel.loadProducts(true)
        }
        showBff.setOnClickListener {
            val intent = Intent(this, BffActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setVisibilityButtons() {
        stateViewButtons = !stateViewButtons

        if (stateViewButtons) {
            listAllProducts.visibility = GONE
            listProductExpensive.visibility = GONE
            listProductCheaper.visibility = GONE
            forceUpdate.visibility = GONE
            showBff.visibility = GONE
        } else {
            listAllProducts.visibility = VISIBLE
            listProductExpensive.visibility = VISIBLE
            listProductCheaper.visibility = VISIBLE
            forceUpdate.visibility = VISIBLE
            showBff.visibility = VISIBLE
        }
    }

    private fun setupViewModel() {
        viewModel.products.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    recycler.layoutManager = LinearLayoutManager(this)
                    listGloboAdapter.submitList(it.data)
                    recycler.adapter = listGloboAdapter

                    progressCircular.visibility = GONE
                }
                Status.ERROR -> {
                    progressCircular.visibility = GONE
                }
                Status.LOADING -> {
                    progressCircular.visibility = VISIBLE
                }
            }
        })
        viewModel.loadProducts(true)
    }
}