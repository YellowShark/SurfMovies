package ru.yellowshark.favoritemovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.favoritemovies.R
import ru.yellowshark.favoritemovies.utils.hideKeyboard

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard(currentFocus?: View(this))
        return super.dispatchTouchEvent(ev)
    }
}