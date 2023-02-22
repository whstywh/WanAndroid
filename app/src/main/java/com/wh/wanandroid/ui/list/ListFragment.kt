package com.wh.wanandroid.ui.list

import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wh.wanandroid.base.BaseFragment
import com.wh.wanandroid.databinding.FragmentListBinding

class ListFragment : BaseFragment<FragmentListBinding>() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListBinding {
        return FragmentListBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }

    override fun initData() {
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

    }

}