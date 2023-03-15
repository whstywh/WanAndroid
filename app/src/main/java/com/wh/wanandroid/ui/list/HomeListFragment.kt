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

    override fun initView() {

    }

    override fun initData() {
    }

}