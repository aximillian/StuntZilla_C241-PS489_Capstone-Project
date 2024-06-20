package com.test.stunzilla.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.test.stunzilla.R
import com.test.stunzilla.base.BaseFragment
import com.test.stunzilla.databinding.FragmentSplashBinding
import com.test.stunzilla.utils.ConstVal
import com.test.stunzilla.utils.PreferenceManager
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
        lifecycleScope.launch {
            delay(ConstVal.SPLASH_SCREEN_DURATION.seconds)
            if (preferenceManager.isOnboardingCompleted()) {
                findNavController().navigate(R.id.action_splashFragment_to_loginActivity)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_boarding1Fragment)
            }

        }
    }

    override fun initProcess() {
    }


    override fun initObservers() {
    }

}