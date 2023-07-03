package com.wh.wanandroid.ui.home

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.wh.wanandroid.R
import com.wh.wanandroid.adapter.HomeBannerListAdapter
import com.wh.wanandroid.adapter.HomeListAdapter
import com.wh.wanandroid.adapter.HomoTopListAdapter
import com.wh.wanandroid.base.BaseFragment
import com.wh.wanandroid.databinding.FragmentHomeBinding
import com.wh.wanandroid.utils.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    private val viewBinding by viewBinding(FragmentHomeBinding::bind)

    override fun getLayoutID() = R.layout.fragment_home

    override fun initView() {
        context?.let { context ->

            val mBannerAdapter = HomeBannerListAdapter(context)
            val mTopListAdapter = HomoTopListAdapter(context)
            val mItemListAdapter = HomeListAdapter(context).apply {
                addLoadStateListener(::loadStateListener)
            }

            viewBinding.homeSmartRefresh.setEnableLoadMore(false)
            viewBinding.homeSmartRefresh.setOnRefreshListener {
                viewModel.getBanner()
                viewModel.getHomeTopList()
                mItemListAdapter.refresh()
            }

            viewBinding.homeList.apply {
                val config = ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
                adapter = ConcatAdapter(
                    config,
                    arrayListOf(mBannerAdapter, mTopListAdapter, mItemListAdapter)
                )
                layoutManager = LinearLayoutManager(context)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.bannerState.collectLatest {
                            mBannerAdapter.submitList(arrayListOf(it))
                        }
                    }

                    launch {
                        viewModel.topState.collectLatest {
                            mTopListAdapter.submitList(it)
                        }
                    }

                    launch {
                        viewModel.pager.collectLatest {
                            mItemListAdapter.submitData(it)
                        }
                    }
                }
            }
        }
    }

    override fun initData() {
    }

    private fun loadStateListener(it: CombinedLoadStates) {
        when (it.refresh) {
            is LoadState.Loading -> {
            }
            is LoadState.NotLoading -> {
                viewBinding.homeSmartRefresh.finishRefresh(true)
            }
            is LoadState.Error -> {
                viewBinding.homeSmartRefresh.finishRefresh(false)
            }
        }
    }
}