package kg.damir.corotinestart

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import kg.damir.corotinestart.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        with(binding) {
            progress.isVisible = true
            buttonLoad.isEnabled = false
            loadCity { city ->
                tvLocation.text = city
                loadTemperature(city) {
                    tvTemperature.text = it.toString()
                    progress.isVisible = false
                    buttonLoad.isEnabled = true
                }

            }

        }
    }


    private fun loadCity(callBack: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
            callBack.invoke("Moscow")
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun loadTemperature(city: String, callBack: (Int) -> Unit) {
        thread {
            Toast.makeText(this, getString(R.string.loading_temperature_toast, city), Toast.LENGTH_SHORT)
                .show()
            Thread.sleep(5000)
            callBack.invoke(17)
        }

    }
}