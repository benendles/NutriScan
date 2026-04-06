package com.nutriscan.app.ui.voice

import android.Manifest; import android.content.Intent; import android.content.pm.PackageManager
import android.os.Bundle; import android.speech.*; import android.view.*; import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment; import androidx.navigation.fragment.findNavController
import com.nutriscan.app.R; import com.nutriscan.app.databinding.FragmentVoiceBinding
import java.util.Locale

class VoiceFragment : Fragment() {
    private var _b: FragmentVoiceBinding? = null; private val b get() = _b!!
    private var sr: SpeechRecognizer? = null

    private val permLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { ok ->
        if (ok) startListening() else Toast.makeText(requireContext(), "Mic permission required", Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentVoiceBinding.inflate(i, c, false); return b.root
    }
    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)
        b.btnBack.setOnClickListener { findNavController().popBackStack() }
        b.btnMic.setOnClickListener { requestMic() }
        b.btnConfirmVoice.setOnClickListener { confirmSave() }
        if (SpeechRecognizer.isRecognitionAvailable(requireContext())) setupSR()
        else Toast.makeText(requireContext(), "Speech recognition unavailable", Toast.LENGTH_LONG).show()
    }

    private fun setupSR() {
        sr = SpeechRecognizer.createSpeechRecognizer(requireContext())
        sr?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(p: Bundle?) { b.tvVoiceStatus.text = "Listening…"; b.waveformView.startAnimation(); b.btnMic.setBackgroundResource(R.drawable.bg_mic_listening) }
            override fun onResults(res: Bundle?) { onResult(res?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull() ?: "") }
            override fun onError(e: Int) { b.tvVoiceStatus.text = "Tap to speak"; b.waveformView.stopAnimation(); b.btnMic.setBackgroundResource(R.drawable.bg_mic_btn) }
            override fun onRmsChanged(rms: Float) { b.waveformView.updateAmplitude(rms) }
            override fun onBeginningOfSpeech() {}; override fun onBufferReceived(buf: ByteArray?) {}
            override fun onEndOfSpeech() {}; override fun onPartialResults(p: Bundle?) {}; override fun onEvent(t: Int, p: Bundle?) {}
        })
    }

    private fun requestMic() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
            startListening() else permLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    private fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }
        sr?.startListening(intent)
    }

    private fun onResult(text: String) {
        b.waveformView.stopAnimation(); b.btnMic.setBackgroundResource(R.drawable.bg_mic_btn)
        b.tvVoiceStatus.text = "Got it! ✓"; b.tvTranscript.text = "\"$text\""
        b.parsedFoodsGroup.visibility = View.VISIBLE
        b.tvParsedFood1.text = "🍚 White Rice — 1 cup       206 kcal"
        b.tvParsedFood2.text = "🍗 Grilled Chicken — 200g   335 kcal"
    }

    private fun confirmSave() { Toast.makeText(requireContext(), "Meal saved to log! ✓", Toast.LENGTH_SHORT).show(); findNavController().popBackStack() }

    override fun onDestroyView() { sr?.destroy(); super.onDestroyView(); _b = null }
}
