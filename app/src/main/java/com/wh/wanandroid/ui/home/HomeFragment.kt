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

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var viewModel: HomeViewModel

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)


    override fun initView() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.homeListContainer, ListFragment::class.java, null)
            ?.commit()
    }

    override fun initData() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getBanner()
        viewModel.bannerLiveData.observe(viewLifecycleOwner, Observer {
            binding.banner.setAdapter(HomeBannerAdapter(it))
        })
    }
}