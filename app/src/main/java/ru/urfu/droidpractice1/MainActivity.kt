package ru.urfu.droidpractice1

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.urfu.droidpractice1.content.MainActivityScreen
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import androidx.core.content.ContextCompat

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
fun ComposeArticleScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val articlePreferences = context.getSharedPreferences(
        "article_preferences",
        Context.MODE_PRIVATE
    )

    val settingsPreferences = context.getSharedPreferences(
        "settings",
        Context.MODE_PRIVATE
    )

    var likes by remember {
        mutableIntStateOf(
            articlePreferences.getInt("likes", 0)
        )
    }

    var dislikes by remember {
        mutableIntStateOf(
            articlePreferences.getInt("dislikes", 0)
        )
    }

    // 0 = нет оценки, 1 = лайк, -1 = дизлайк
    var userVote by remember {
        mutableIntStateOf(
            articlePreferences.getInt("userVote", 0)
        )
    }

    var isRead by remember {
        mutableStateOf(
            settingsPreferences.getBoolean("isRead", false)
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == RESULT_OK) {
            isRead = result.data?.getBooleanExtra(
                "isRead",
                false
            ) ?: false
        }
    }

    val articleText = """
                    Стоковые изображения - это готовые фотографии и иллюстрации, которые можно использовать в проектах, презентациях и публикациях.
                    
                    Они помогают быстро подобрать подходящий визуальный материал без необходимости самостоятельно организовывать съёмку.
                    
                    Это невероятно удобно, к тому же среди них можно найти довольно много, так называемых, скрытых гемов, что на наш взгляд, повышает их значимость.
                    
                    Зачастую на них присутствуют вотермарки, но это довольно малая цена за их использование
                """.trimIndent()

    DroidPractice1Theme {

        Scaffold { innerPadding ->

            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Стоковые изображения",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = HEADER_FONT_SIZE.sp,
                    modifier = Modifier.padding(
                        bottom = BOTTOM_PADDING.dp
                    )
                )
                // Лайки, дизлайки и поделиться статьёй
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Кнопка лайка
                    Button(
                        onClick = {
                            when (userVote) {
                                0 -> {
                                    likes++
                                    userVote = 1
                                }

                                1 -> {
                                    likes--
                                    userVote = 0
                                }

                                -1 -> {
                                    dislikes--
                                    likes++
                                    userVote = 1
                                }
                            }

                            articlePreferences
                                .edit()
                                .putInt("likes", likes)
                                .putInt("dislikes", dislikes)
                                .putInt("userVote", userVote)
                                .apply()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (userVote == 1) {
                                Color.Green
                            } else {
                                Color.Gray
                            },
                            contentColor = Color.Black
                        )
                    ) {
                        Text("👍 $likes")
                    }

                    // Небольшой отступ
                    Spacer(
                        modifier = Modifier.padding(6.dp)
                    )

                    // Кнопка дизлайка
                    Button(
                        onClick = {
                            when (userVote) {
                                0 -> {
                                    dislikes++
                                    userVote = -1
                                }

                                1 -> {
                                    likes--
                                    dislikes++
                                    userVote = -1
                                }

                                -1 -> {
                                    dislikes--
                                    userVote = 0
                                }
                            }

                            articlePreferences
                                .edit()
                                .putInt("likes", likes)
                                .putInt("dislikes", dislikes)
                                .putInt("userVote", userVote)
                                .apply()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (userVote == -1) {
                                Color.Red
                            } else {
                                Color.Gray
                            },
                            contentColor = Color.Black
                        )
                    ) {
                        Text("👎 $dislikes")
                    }

                    // Занимает всё свободное место
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )

                    // Поделиться
                    Button(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    articleText
                                )
                                putExtra(
                                    Intent.EXTRA_TITLE,
                                    "Стоковые изображения"
                                )
                            }

                            val chooser = Intent.createChooser(
                                shareIntent,
                                "Поделиться статьёй"
                            )

                            context.startActivity(chooser)
                        }
                    ) {
                        Text("Поделиться")
                    }
                }

                GlideImage(
                    model = "https://cs10.pikabu.ru/post_img/big/2018/09/03/6/153596777311515846.jpg",
                    contentDescription = "Прикольно, что есть описание картинок",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = BOTTOM_PADDING.dp
                        )
                )

                Text(
                    text = articleText,

                    style = MaterialTheme.typography.bodyLarge,

                    modifier = Modifier.padding(
                        bottom = BOTTOM_PADDING.dp
                    )
                )

                Button(
                    onClick = {

                        val intent = Intent(
                            context,
                            SecondActivity::class.java
                        )

                        launcher.launch(intent)
                    },

                    modifier = Modifier.fillMaxWidth(),

                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (isRead) {
                                Color.Green
                            } else {
                                Color.Gray
                            },
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        if (isRead) {
                            "Следующая статья на Вьюхе (прочитано)"
                        } else {
                            "Следующая статья на Вьюхе"
                        }
                    )
                }
            }
        }
    }
}