package com.example.androidhealthmonitor.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.androidhealthmonitor.LoginActivity
import com.example.androidhealthmonitor.R
import com.example.androidhealthmonitor.util.SecureStore // ✅ Import SecureStore
import com.example.androidhealthmonitor.util.SessionManager
import de.hdodenhof.circleimageview.CircleImageView

class SettingsFragment : Fragment() {

    private lateinit var ivProfile: CircleImageView
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Launcher for picking an image from the gallery
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                ivProfile.setImageURI(imageUri)
            }
        }

        // Launcher for requesting storage permission
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                Toast.makeText(requireContext(), "Permission denied to read storage", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        ivProfile = view.findViewById<CircleImageView>(R.id.ivProfile)
        val btnChangePhoto = view.findViewById<Button>(R.id.btnChangePhoto)
        val etUsername = view.findViewById<EditText>(R.id.etUsername)
        val btnUpdateUsername = view.findViewById<Button>(R.id.btnUpdateUsername)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnUpdatePassword = view.findViewById<Button>(R.id.btnUpdatePassword)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // ✅ Find new API key views
        val etApiKey = view.findViewById<EditText>(R.id.etApiKey)
        val btnSaveApiKey = view.findViewById<Button>(R.id.btnSaveApiKey)

        val sessionManager = SessionManager(requireContext())

        btnChangePhoto.setOnClickListener {
            requestGalleryPermission()
        }

        // ✅ Add listener to save API key
        btnSaveApiKey.setOnClickListener {
            val apiKey = etApiKey.text.toString().trim()
            if (apiKey.isNotEmpty()) {
                SecureStore.saveApiKey(requireContext(), apiKey)
                Toast.makeText(requireContext(), "API Key Saved", Toast.LENGTH_SHORT).show()
                etApiKey.text.clear()
            } else {
                Toast.makeText(requireContext(), "Please enter an API Key", Toast.LENGTH_SHORT).show()
            }
        }

        btnUpdateUsername.setOnClickListener {
            val newUsername = etUsername.text.toString()
            if (newUsername.isNotEmpty()) {
                sessionManager.login(newUsername) // Updates the logged-in user's name
                Toast.makeText(requireContext(), "Username Updated", Toast.LENGTH_SHORT).show()
                etUsername.text.clear()
            } else {
                Toast.makeText(requireContext(), "Please enter a new username", Toast.LENGTH_SHORT).show()
            }
        }

        btnUpdatePassword.setOnClickListener {
            val newPassword = etPassword.text.toString()
            val currentUser = sessionManager.getLoggedInUser()
            if (newPassword.isNotEmpty() && currentUser != null) {
                sessionManager.saveUser(currentUser, newPassword) // Overwrites the old password
                Toast.makeText(requireContext(), "Password Updated", Toast.LENGTH_SHORT).show()
                etPassword.text.clear()
            } else {
                Toast.makeText(requireContext(), "Please enter a new password", Toast.LENGTH_SHORT).show()
            }
        }

        btnLogout.setOnClickListener {
            sessionManager.logout()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return view
    }

    private fun requestGalleryPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                openGallery()
            }
            else -> {
                permissionLauncher.launch(permission)
            }
        }
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        galleryLauncher.launch(gallery)
    }
}