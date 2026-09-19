package ru.urfu.droidpractice1

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

        binding.backButton.setOnClickListener {
            finish()
        }

        Glide.with(this)
            .load("https://www.cossa.ru/upload/iblock/d55/tyz.jpg")
            .into(binding.articleImage)
    }
}