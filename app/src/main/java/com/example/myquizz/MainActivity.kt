package com.example.myquizz


import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myquizz.ui.theme.MyQuizzTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyQuizzTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
                }
            }
        }
    }

@Composable
fun App() {
    val navController = rememberNavController()
    val quizViewModel: QuizViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            Intro(
                onNextScreen = { navController.navigate("question") },
                question = "Start with the first question!! Good luck!!",
                title = "Intro",
                modifier = Modifier.padding(8.dp),
            )
        }
        composable(route = "question") {
            QuizScreen(
                quizViewModel = quizViewModel,
                onNextScreen = { navController.navigate("result") }
            )
        }
        composable(route = "result") {
            ResultScreen(score = quizViewModel.quizState.score)
        }
    }
}

@Composable
fun Intro(
    question: String,
    title: String,
    modifier: Modifier = Modifier,
    onNextScreen: () -> Unit
) {
    Surface(color = Color.Cyan) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier.padding(16.dp),
        ) {
            Text(
                text = title,
                fontSize = 70.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .align(alignment = Alignment.CenterHorizontally),
            )
            Text(
                text = question,
                fontSize = 40.sp,
                lineHeight = 50.sp,
                textAlign = TextAlign.Left
            )

            Button(onClick = onNextScreen) {
                Text(
                    text = stringResource(id = R.string.button_label),
                )
            }
        }
    }
}

@Composable
fun QuizScreen(
    quizViewModel: QuizViewModel,
    onNextScreen: () -> Unit,
) {
    val quizState = quizViewModel.quizState

    Surface(color = Color.Cyan) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Quiz",
                fontSize = 70.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .align(alignment = Alignment.CenterHorizontally),
            )
            Text(
                text = quizState.questions[quizState.currentQuestionIndex],
                fontSize = 40.sp,
                lineHeight = 50.sp,
                textAlign = TextAlign.Left
            )
            TextField(
                value = quizState.userAnswer,
                onValueChange = { newValue ->
                    quizViewModel.updateAnswer(newValue)
                },
            )

            Text(
                text = quizState.resultText,
                fontSize = 30.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                color = if (quizState.resultText == "Correct") Color.Green else Color.Red
            )

            Button(
                onClick = {
                    quizViewModel.checkAnswer()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = stringResource(id = R.string.button_label),
                )
            }
            Button(
                onClick = {
                    quizViewModel.nextQuestion()
                    if (quizState.currentQuestionIndex + 1 == quizState.questions.size) {
                        onNextScreen()
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
            ) {
                Text(text = "Next Question")
            }
        }
    }
}

@Composable
fun ResultScreen(score: Int) {
    Surface(color = Color.Cyan) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Quiz Completed!",
                fontSize = 60.sp,
                lineHeight = 60.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Your score is $score",
                fontSize = 40.sp,
                lineHeight = 60.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyQuizzTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "intro") {
            composable(route = "intro") {
                Intro(
                    onNextScreen = { navController.navigate("question") },
                    question = "Start with the first question!! Good luck!!",
                    title = "Intro",
                    modifier = Modifier.padding(8.dp),
                )
            }
        }
    }
}