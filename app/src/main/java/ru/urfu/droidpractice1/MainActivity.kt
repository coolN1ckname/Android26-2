package ru.urfu.droidpractice1

import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.urfu.droidpractice1.content.MainActivityScreen
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import android.content.Intent
import androidx.compose.ui.platform.LocalContext


const val BOTTOM_PADDING = 16
const val HEADER_FONT_SIZE = 20
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityScreen()
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ComposeArticleScreen(modifier: Modifier) {

    val context = LocalContext.current

    DroidPractice1Theme {
        Scaffold(
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = " ДАСТ 200",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = HEADER_FONT_SIZE.sp,
                    modifier = Modifier
                        .padding(bottom = BOTTOM_PADDING.dp)
                )

                //Лайки и дизы
                Button(
                    onClick = {
                        TODO()
                    },
                    modifier = Modifier

                ){
                    Text("")
                }

                GlideImage(
                    model = "https://cs10.pikabu.ru/post_img/big/2018/09/03/6/153596777311515846.jpg",
                    contentDescription = "Прикольно, что есть описание картинок",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = BOTTOM_PADDING.dp)
                )

                Text(
                    text = """
                        Это текст статьи.

                        Здесь может быть первый абзац, второй абзац
                        и другая информация.

                        Вся разметка этой статьи создаётся
                        непосредственно с помощью Jetpack Compose.
                    """.trimIndent(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(bottom = BOTTOM_PADDING.dp)
                )

                Button(
                    onClick = {
                        val intent = Intent(context, SecondActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.Gray
                    )
                ){
                    Text("абоба $")

                }
            }
        }
    }
}