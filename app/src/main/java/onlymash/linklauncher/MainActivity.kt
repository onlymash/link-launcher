package onlymash.linklauncher

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.widget.addTextChangedListener
import onlymash.linklauncher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.content.linkEdit.addTextChangedListener {
            binding.content.go.isEnabled = !it.isNullOrEmpty()
        }
        binding.content.go.setOnClickListener {
            val url = (binding.content.linkEdit.text?.trim() ?: "").toString()
            if (url.isNotEmpty()) {
                openUrl(url)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun openUrl(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        try {
            startActivity(intent)
            binding.content.log.text = "Success"
        } catch (e: ActivityNotFoundException) {
            binding.content.log.text = e.message
        }
    }
}