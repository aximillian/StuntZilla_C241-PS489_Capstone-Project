package com.example.stunzilla.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.stunzilla.R
import com.example.stunzilla.base.BaseFragment
import com.example.stunzilla.databinding.FragmentSplashBinding
import com.example.stunzilla.utils.ConstVal
import com.example.stunzilla.utils.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.time.Duration.Companion.seconds

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val preferenceManager: PreferenceManager by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
    }

    override fun initAction() {
    }

    override fun initProcess() {
        lifecycleScope.launch {
            delay(ConstVal.SPLASH_SCREEN_DURATION.seconds)
            if (preferenceManager.getToken != "") {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            } else if (preferenceManager.getOnboardingScreen) {
                findNavController().navigate(R.id.action_splashFragment_to_loginActivity)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_boardingFragment)
            }
        }
    }


    override fun initObservers() {
    }

}