package com.test.stunzilla.ui.boading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.stunzilla.R
import com.test.stunzilla.base.BaseFragment
import com.test.stunzilla.databinding.FragmentBoarding1Binding
import com.test.stunzilla.utils.PreferenceManager
import org.koin.android.ext.android.inject

class Boarding1Fragment : BaseFragment<FragmentBoarding1Binding>() {

    private val preferenceManager: PreferenceManager by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentBoarding1Binding = FragmentBoarding1Binding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
        binding.buttonSkip.setOnClickListener{
            preferenceManager.setOnboardingCompleted(true)
            findNavController().navigate(R.id.action_boarding1Fragment_to_loginActivity)
        }

        binding.buttonNext.setOnClickListener{
            findNavController().navigate(R.id.action_boarding1Fragment_to_boarding2Fragment)
        }
    }

    override fun initAction() {
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

}