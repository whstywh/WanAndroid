package com.wh.wanandroid.ui.list

import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.wh.wanandroid.adapter.HomeListAdapter
import com.wh.wanandroid.base.BaseFragment
import com.wh.wanandroid.databinding.FragmentHomeListBinding
import kotlinx.coroutines.launch

class HomeListFragment : BaseFragment<FragmentHomeListBinding>() {

    private val viewModel: HomeListViewModel by lazy { ViewModelProvider(this)[HomeListViewModel::class.java] }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeListBinding {
        return FragmentHomeListBinding.inflate(inflater, container, false)
    }

    private var mHomeListAdapter: HomeListAdapter? = null

    override fun initView() {

        context?.let {
            mHomeListAdapter = HomeListAdapter(it)
            binding.homeList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mHomeListAdapter
            }
        }

        binding.homeRefreshLayout.setOnRefreshListener {
            mHomeListAdapter?.refresh()
        }

        lifecycleScope.launch {
            viewModel.getPagingData().collect { pagingData ->
                mHomeListAdapter?.submitData(pagingData)
            }
        }

        mHomeListAdapter?.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                }
                is LoadState.NotLoading -> {
                    binding.homeRefreshLayout.finishRefresh(true)
                }
                is LoadState.Error -> {
                    binding.homeRefreshLayout.finishRefresh(false)
                }
            }
        }

        mHomeListAdapter?.refresh()

    }

    override fun initData() {

    }

}