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
import com.wh.wanandroid.adapter.HomeBannerAdapter
import com.wh.wanandroid.adapter.HomeListAdapter
import com.wh.wanandroid.adapter.HomoTopListAdapter
import com.wh.wanandroid.base.BaseFragment
import com.wh.wanandroid.databinding.FragmentHomeBinding
import com.wh.wanandroid.utils.viewBinding
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
    override fun getLayoutID() = R.layout.fragment_home

    override fun initView() {
        context?.let { context ->

            val mBannerAdapter = HomeBannerAdapter(this@HomeFragment.context)
            val mTopListAdapter = HomoTopListAdapter(context)
            val mItemListAdapter = HomeListAdapter(context).apply {
                addLoadStateListener(::loadStateListener)
            }

            viewBinding.homeList.apply {
                val config = ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
                adapter = ConcatAdapter(
                    config,
                    arrayListOf(mTopListAdapter, mItemListAdapter)
                )
                layoutManager = LinearLayoutManager(context)
            }

            viewBinding.homeSmartRefresh.apply {
                setEnableLoadMore(false)
                setOnRefreshListener {
                    viewModel.getBanner()
                    viewModel.getHomeTopList()
                    mItemListAdapter.refresh()
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getPagingData().collect { it ->
                        mItemListAdapter.submitData(it)
                    }
                }
            }

            viewModel.bannerLiveData.observe(viewLifecycleOwner) {
                mBannerAdapter.setDatas(it)
            }

            viewModel.topLiveData.observe(viewLifecycleOwner) {
                mTopListAdapter.submitList(it)
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