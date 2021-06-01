package com.nicco.selecaoglobo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicco.business.model.ProductModelUi
import com.nicco.selecaoglobo.R
import com.nicco.selecaoglobo.ui.adapter.Interaction
import com.nicco.selecaoglobo.ui.adapter.ListGloboAdapter
import com.nicco.selecaoglobo.utils.GloboViewModelFactory
import com.nicco.selecaoglobo.utils.Status
import com.nicco.selecaoglobo.viewmodel.GloboProductsViewModel

class BffActivity : AppCompatActivity() {

    private val recycler by lazy { this.findViewById<RecyclerView>(R.id.recycler) }
    private val progressCircular by lazy { this.findViewById<ProgressBar>(R.id.progress_circular) }

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
        setContentView(R.layout.activity_bff)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.products.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    recycler.layoutManager = LinearLayoutManager(this)
                    listGloboAdapter.submitList(it.data)
                    recycler.adapter = listGloboAdapter

                    progressCircular.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressCircular.visibility = View.GONE
                }
                Status.LOADING -> {
                    progressCircular.visibility = View.VISIBLE
                }
            }
        })
        viewModel.loadBffProducts()
    }
}