package com.example.myquizz

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class QuizState(
    val questions: List<String>,
    val answers: List<String>,
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val userAnswer: String = "",
    val resultText: String = ""
)

class QuizViewModel : ViewModel() {
    var quizState by mutableStateOf(
        QuizState(
            questions = listOf(
                "What is the capital of France?",
                "What is 2 + 2?",
                "What color is the sky on a clear day?"
            ),
            answers = listOf("Paris", "4", "Blue")
        )
    )
        private set

    fun updateAnswer(newAnswer: String) {
        quizState = quizState.copy(userAnswer = newAnswer)
    }

    fun checkAnswer() {
        val correctAnswer = quizState.answers[quizState.currentQuestionIndex]
        val result = if (quizState.userAnswer.equals(correctAnswer, ignoreCase = true)) {
            quizState = quizState.copy(score = quizState.score + 1)
            "Correct"
        } else {
            "Incorrect"
        }
        quizState = quizState.copy(resultText = result)
    }

    fun nextQuestion() {
        val nextIndex = quizState.currentQuestionIndex + 1
        if (nextIndex < quizState.questions.size) {
            quizState = quizState.copy(
                currentQuestionIndex = nextIndex,
                userAnswer = "",
                resultText = ""
            )
        } else {
            quizState = quizState.copy(
                currentQuestionIndex = 0,
                userAnswer = "",
                resultText = ""
            )
        }
    }
}