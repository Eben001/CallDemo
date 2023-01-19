package com.ebenezer.gana.calldemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.ebenezer.gana.calldemo.databinding.FragmentFirstBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirstFragment : Fragment(R.layout.fragment_first) {

    lateinit var binding:FragmentFirstBinding
    val DIAL_REQUEST_CODE = 12

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.bind(
            super.onCreateView(inflater, container, savedInstanceState)!!
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            checkPermission()

            TODO("""
                Create an abstract class to implement the checking of permission and making calls
                to avoid duplicated logic on both FirstFragment and SecondFragment 
                or some sort of ways to avoid the code duplication(checking for permission and making the 
                        call) on both fragments

            """.trimIndent())

        }

    }
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.CALL_PHONE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    DIAL_REQUEST_CODE
                )
            }
        } else {
            // Permission has already been granted
            makeCall("+31231412151")
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == DIAL_REQUEST_CODE) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                makeCall("+31231412151")
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }


    }


    private fun makeCall(phone: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
        startActivity(intent)

        //launch a coroutine to delay the navigation of secondFragment
        lifecycleScope.launch{
            delay(1000)
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }


}