package com.test.stunzilla.ui.boading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.stunzilla.R
import com.test.stunzilla.base.BaseFragment
import com.test.stunzilla.databinding.FragmentBoarding2Binding
import com.test.stunzilla.utils.PreferenceManager
import org.koin.android.ext.android.inject

class Boarding2Fragment : BaseFragment<FragmentBoarding2Binding>() {

    private val preferenceManager: PreferenceManager by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentBoarding2Binding = FragmentBoarding2Binding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
    }

    override fun initAction() {
        binding.backView.setOnClickListener{
            findNavController().navigate(R.id.action_boarding2Fragment_to_boarding1Fragment)
        }

        binding.buttonSkip.setOnClickListener{
            preferenceManager.setOnboardingCompleted(true)
            findNavController().navigate(R.id.action_boarding2Fragment_to_loginActivity)
        }

        binding.buttonNext.setOnClickListener{
            findNavController().navigate(R.id.action_boarding2Fragment_to_boarding3Fragment)
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

}