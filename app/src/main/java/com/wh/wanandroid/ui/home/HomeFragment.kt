package com.wh.wanandroid.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wh.wanandroid.R
import com.wh.wanandroid.adapter.HomeBannerAdapter
import com.wh.wanandroid.base.BaseFragment
import com.wh.wanandroid.databinding.FragmentHomeBinding
import com.wh.wanandroid.ui.list.HomeListFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)


    override fun initView() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.homeListContainer, HomeListFragment(), null)
            ?.commit()
    }

    override fun initData() {
        viewModel.getBanner()
        viewModel.bannerLiveData.observe(viewLifecycleOwner) {
            binding.banner.setAdapter(HomeBannerAdapter(it))
        }
    }
}