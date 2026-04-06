package com.nutriscan.app.ui.scan

import android.Manifest; import android.content.pm.PackageManager
import android.os.Bundle; import android.view.*; import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*; import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment; import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nutriscan.app.data.model.FoodEntry
import com.nutriscan.app.databinding.FragmentScanBinding
import java.util.concurrent.Executors

class ScanFragment : Fragment() {
    private var _b: FragmentScanBinding? = null; private val b get() = _b!!
    private val executor = Executors.newSingleThreadExecutor()
    private var imageCapture: ImageCapture? = null

    private val permLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { ok ->
        if (ok) startCamera() else Toast.makeText(requireContext(), "Camera permission required", Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentScanBinding.inflate(i, c, false); return b.root
    }
    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)
        b.btnBack.setOnClickListener { findNavController().popBackStack() }
        b.btnCapture.setOnClickListener { captureImage() }
        b.btnConfirmScan.setOnClickListener { confirmSave() }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            startCamera() else permLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun startCamera() {
        val future = ProcessCameraProvider.getInstance(requireContext())
        future.addListener({
            val provider = future.get()
            val preview = Preview.Builder().build().also { it.setSurfaceProvider(b.cameraPreview.surfaceProvider) }
            imageCapture = ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build()
            try { provider.unbindAll(); provider.bindToLifecycle(viewLifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture) }
            catch (e: Exception) { Toast.makeText(requireContext(), "Camera error", Toast.LENGTH_SHORT).show() }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun captureImage() {
        b.scanOverlay.showScanning(true)
        b.root.postDelayed({ b.scanOverlay.showScanning(false); showResults() }, 1500)
    }

    private fun showResults() {
        val foods = listOf(
            FoodEntry(emoji="🍚", name="White Rice",      meta="Confidence: 97%", calories=206, progressPercent=50),
            FoodEntry(emoji="🍗", name="Grilled Chicken", meta="Confidence: 91%", calories=335, progressPercent=70))
        b.rvDetectedFoods.layoutManager = LinearLayoutManager(requireContext())
        b.rvDetectedFoods.adapter = DetectedFoodAdapter(foods)
        b.tvTotalKcal.text = "541 kcal"; b.tvTotalMacros.text = "P: 48g   C: 52g   F: 14g"
        b.resultPanel.visibility = View.VISIBLE
    }

    private fun confirmSave() {
        Toast.makeText(requireContext(), "Meal saved to log! ✓", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() { executor.shutdown(); super.onDestroyView(); _b = null }
}
