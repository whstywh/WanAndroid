package com.wh.wanandroid.ui.my

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.wh.wanandroid.R
import com.wh.wanandroid.base.BaseFragment
import com.wh.wanandroid.databinding.FragmentHomeBinding
import com.wh.wanandroid.databinding.FragmentMyBinding
import com.wh.wanandroid.ui.home.HomeViewModel
import com.wh.wanandroid.ui.login.LoginActivity
import com.wh.wanandroid.ui.video.VideoDetailActivity
import com.wh.wanandroid.utils.DataStoreUtils
import com.wh.wanandroid.utils.viewBinding
import kotlinx.coroutines.launch

/**
 * 2023/5/30
 * wh
 * desc：个人中心
 */
class MyFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMyBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.myAnchorImg?.setOnClickListener(this)
        _binding?.myLoginName?.setOnClickListener(this)
        _binding?.myVideo?.setOnClickListener(this)

        viewLifecycleOwner.lifecycleScope.launch {
            _binding?.myLoginName?.text = DataStoreUtils.getUsername(context)
        }
    }

    override fun onClick(v: View?) {
        if (v == _binding?.myLoginName || v == _binding?.myAnchorImg) {
            viewLifecycleOwner.lifecycleScope.launch {
                if (DataStoreUtils.isNoLogin(context)) {
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            }
        } else if (v == _binding?.myVideo) {//视频详情
            startActivity(Intent(context, VideoDetailActivity::class.java))
        }
    }

}