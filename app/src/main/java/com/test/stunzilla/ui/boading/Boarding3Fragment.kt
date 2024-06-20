package com.test.stunzilla.ui.boading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.stunzilla.R
import com.test.stunzilla.base.BaseFragment
import com.test.stunzilla.databinding.FragmentBoarding3Binding
import com.test.stunzilla.utils.PreferenceManager
import org.koin.android.ext.android.inject

class Boarding3Fragment : BaseFragment<FragmentBoarding3Binding>() {

    private val preferenceManager: PreferenceManager by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentBoarding3Binding = FragmentBoarding3Binding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
    }

    override fun initAction() {
    }

    override fun initProcess() {
        binding.backView.setOnClickListener{
            findNavController().navigate(R.id.action_boarding3Fragment_to_boarding2Fragment)
        }

        binding.buttonNext.setOnClickListener{
            preferenceManager.setOnboardingCompleted(true)
            findNavController().navigate(R.id.action_boarding3Fragment_to_loginActivity)
        }
    }

    override fun initObservers() {
    }

}