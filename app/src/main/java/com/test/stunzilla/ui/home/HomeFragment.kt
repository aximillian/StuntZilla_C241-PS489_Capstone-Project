package com.test.stunzilla.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ekn.gruzer.gaugelibrary.ArcGauge
import com.test.stunzilla.databinding.FragmentHomeBinding
import com.test.stunzilla.ui.auth.login.LoginActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var _binding: FragmentHomeBinding? = null
    private lateinit var arcGauge: ArcGauge
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//
//        binding.btnLogin.setOnClickListener {
//            val intent = Intent(requireContext(), LoginActivity::class.java)
//            startActivity(intent)
//        }
//        dashboardGauge()
        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.btnLogout.setOnClickListener {
            signOut()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //    private fun dashboardGauge() {
//        val range = Range()
//        range.color = Color.parseColor("#FF8F1F")
//        range.from = 0.0
//        range.to = 100.0
//
//        arcGauge.minValue= 10.0
//        arcGauge.maxValue = 150.0
//        arcGauge.value = 35.0
//
//        arcGauge.addRange(range)
//        arcGauge.isUseRangeBGColor= true
//        arcGauge.valueColor = Color.BLUE
//
//        arcGauge.setFormatter(ValueFormatter {
//            it.toInt().toString()
//        })
//    }
    private fun signOut() {

        lifecycleScope.launch {

            val credentialManager = CredentialManager.create(requireContext())
            auth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

    }
}