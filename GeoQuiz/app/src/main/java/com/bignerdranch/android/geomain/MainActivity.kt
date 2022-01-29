package com.bignerdranch.android.geomain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia,true),
        Question(R.string.question_oceans,true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa,false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia,true)
    )

    private var currentIndex = 0
    private var questionIndex = 0
    var correctIndex = 0
    var inCorrectIndex = 0
    var result ="True: $correctIndex, False: $inCorrectIndex"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

            trueButton.setOnClickListener { view: View ->
                checkAnswer(true)
                trueButton.setEnabled(false)
                falseButton.setEnabled(false)
                questionIndex++
                showResult()
            }

            questionTextView.setOnClickListener { view: View ->
                if (currentIndex == questionBank.size - 1) {
                } else {
                    currentIndex = (currentIndex + 1) % questionBank.size
                    updateQuestion()
                }
            }

            falseButton.setOnClickListener { view: View ->
                checkAnswer(false)
                falseButton.setEnabled(false)
                trueButton.setEnabled(false)
                questionIndex++
                showResult()
            }

            prevButton.setOnClickListener {
                if (currentIndex == 0) {
                } else {
                    currentIndex = (currentIndex - 1) % questionBank.size
                    updateQuestion()
                }
                if (questionIndex>currentIndex) {
                    falseButton.setEnabled(false)
                    trueButton.setEnabled(false)
                }
            }

            nextButton.setOnClickListener {
                if (currentIndex == questionBank.size - 1) {
                } else {
                    currentIndex = (currentIndex + 1) % questionBank.size
                    updateQuestion()
                }
                if (questionIndex<=currentIndex) {
                    trueButton.setEnabled(true)
                    falseButton.setEnabled(true)
                }
        }

            val questionTextResId = questionBank[currentIndex].textResId
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

    override fun onStop(){
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            correctIndex++
            R.string.correct_toast
        } else {
            inCorrectIndex++
            R.string.incorrect_toast
        }

        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(){
        if(questionIndex==questionBank.size){
            Toast.makeText(this,result, Toast.LENGTH_SHORT).show()
        }
    }
}