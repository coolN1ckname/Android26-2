package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences("settings", MODE_PRIVATE)

        // Восстанавленниие сохранённого результата
        binding.readSwitch.isChecked =
            preferences.getBoolean("isRead", false)

        binding.backButton.setOnClickListener {
            finish()
        }

        Glide.with(this)
            .load("https://www.cossa.ru/upload/iblock/d55/tyz.jpg")
            .into(binding.articleImage)

        binding.readSwitch.setOnCheckedChangeListener { _, isChecked ->

            // Сохраниение результата
            preferences.edit()
                .putBoolean("isRead", isChecked)
                .apply()

            // Передача результата обратно
            val resultIntent = Intent()
            resultIntent.putExtra("isRead", isChecked)

            setResult(RESULT_OK, resultIntent)
        }
    }
}