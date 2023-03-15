package com.wh.wanandroid.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.wh.wanandroid.adapter.HomeBannerAdapter
import com.wh.wanandroid.adapter.HomeListAdapter
import com.wh.wanandroid.base.BaseFragment
import com.wh.wanandroid.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun initView() {
        context?.let { context ->
            val mHomeListAdapter = HomeListAdapter(context)

            binding.homeList.apply {
                adapter = mHomeListAdapter
                layoutManager = LinearLayoutManager(context)
            }

            mHomeListAdapter.addLoadStateListener(::loadStateListener)

            binding.homeSmartRefresh.setEnableLoadMore(false)
            binding.homeSmartRefresh.setOnRefreshListener {
                viewModel.getBanner()
                mHomeListAdapter.refresh()
            }

            lifecycleScope.launch {
                viewModel.getPagingData().collect { it ->
                    mHomeListAdapter.submitData(it)
                }
            }
        }

        viewModel.bannerLiveData.observe(viewLifecycleOwner) {
            binding.banner.setAdapter(HomeBannerAdapter(it))
        }
    }

    override fun initData() {
    }

    private fun loadStateListener(it: CombinedLoadStates) {
        when (it.refresh) {
            is LoadState.Loading -> {
            }
            is LoadState.NotLoading -> {
                binding.homeSmartRefresh.finishRefresh(true)
            }
            is LoadState.Error -> {
                binding.homeSmartRefresh.finishRefresh(false)
            }
        }
    }
}