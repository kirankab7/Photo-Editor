package com.grafocrate.photoeditor


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.grafocrate.photoeditor.databinding.FragmentSaveBinding

class SaveFragment : Fragment() {

    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonShareImage.setOnClickListener {
            // Handle Share Image button click
        }

        binding.buttonShareApps.setOnClickListener {
            // Handle Share Apps button click
        }

        binding.buttonOtherApps.setOnClickListener {
            // Handle Other Apps button click
        }

        binding.buttonRateApp.setOnClickListener {
            // Handle Rate the App button click
        }

        binding.buttonHome.setOnClickListener {
            findNavController().navigate(R.id.action_saveFragment_to_homeFragment)
        }

        binding.buttonAds.setOnClickListener {
            // Handle Ads button click
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
