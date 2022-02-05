package com.bignerdranch.android.geomain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val Q_INDEX = "index"
private const val REQUEST_CODE_CHEAT= 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        val questionIndex = savedInstanceState?.getInt(Q_INDEX, 0) ?: 0
        quizViewModel.questionIndex = questionIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatButton = findViewById(R.id.cheat_button)

        if (quizViewModel.isCompleted()) {
            offButton()
        } else onButton()

        questionTextView.setOnClickListener { view: View ->
            if (quizViewModel.currentIndex == quizViewModel.getQuestionBank().size - 1) {
            } else {
                quizViewModel.currentIndex =
                    (quizViewModel.currentIndex + 1) % quizViewModel.getQuestionBank().size
                updateQuestion()
            }
        }

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            quizViewModel.completed()
            offButton()
            quizViewModel.questionIndex++
            showResult()
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            quizViewModel.completed()
            offButton()
            quizViewModel.questionIndex++
            showResult()
        }

        prevButton.setOnClickListener {
            if (quizViewModel.currentIndex == 0) {
            } else {
                quizViewModel.moveToPrev()
                updateQuestion()
            }
            if (quizViewModel.isCompleted()) {
                offButton()
            } else onButton()
        }

        nextButton.setOnClickListener {
            if (quizViewModel.currentIndex == quizViewModel.getQuestionBank().size - 1) {
            } else {
                quizViewModel.moveToNext()
                updateQuestion()
            }
            if (quizViewModel.isCompleted()) {
                offButton()
            } else onButton()
        }

        cheatButton.setOnClickListener{
            val answerIsTrue=quizViewModel.currentQuestionAnswer
            val intent= CheatActivity.newIntent(this,answerIsTrue)
            startActivityForResult(intent,REQUEST_CODE_CHEAT)
        }

        val questionTextResId =
            quizViewModel.getQuestionBank()[quizViewModel.currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause(){
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState:  Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        savedInstanceState.putInt(Q_INDEX, quizViewModel.questionIndex)
    }

    override fun onStop(){
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if (userAnswer == correctAnswer){
            quizViewModel.correctIndex++
        } else {
            quizViewModel.inCorrectIndex++
        }
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(){
        var result ="True: ${quizViewModel.correctIndex}, False: ${quizViewModel.inCorrectIndex}"
        if(quizViewModel.questionIndex== quizViewModel.getQuestionBank().size){
            Toast.makeText(this,result, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onButton(){
            trueButton.setEnabled(true)
            falseButton.setEnabled(true)
    }

    private fun offButton(){
            falseButton.setEnabled(false)
            trueButton.setEnabled(false)
    }
}