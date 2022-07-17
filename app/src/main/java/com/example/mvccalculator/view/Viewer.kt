package com.example.mvccalculator.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.mvccalculator.R
import com.example.mvccalculator.controller.Controller
import com.google.gson.Gson


class Viewer : AppCompatActivity() {

    companion object {
        const val THEME_EDITOR_SAVE_KEY = "THEME_EDITOR_SAVE_KEY"
        const val THEME_SAVE_KEY = "THEME_SAVE_KEY"
        const val WORKING_TEXT_SAVE_KEY = "WORKING_TEXT_SAVE_KEY"
        const val RESULT_TEXT_SAVE_KEY = "RESULT_TEXT_SAVE_KEY"
    }

    private var controller: Controller

    private val workingsTV: TextView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.workingsTV)
    }

    private val resultsTV: TextView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.resultsTV)
    }

    init {
        println("    Start Viewer constructor().")
        controller = Controller(viewer = this)
        println("    I am Viewer object.")
        println("    End Viewer constructor().")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (getAppTheme()) {
            ThemeColors.BLACK -> setTheme(R.style.Theme_MVCCalculator_Dark)
            ThemeColors.BLUE -> setTheme(R.style.MVCCalculator_BlueTheme)
            ThemeColors.GREEN -> setTheme(R.style.MVCCalculator_GreenTheme)
            ThemeColors.YELLOW -> setTheme(R.style.MVCCalculator_YellowTheme)
            ThemeColors.PURPLE -> setTheme(R.style.MVCCalculator_PurpleTheme)
        }

        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            workingsTV.text = savedInstanceState.getString(WORKING_TEXT_SAVE_KEY)
            resultsTV.text = savedInstanceState.getString(RESULT_TEXT_SAVE_KEY)
        }

        findViewById<CardView>(R.id.theme_black).setOnClickListener {
            if (getAppTheme() == ThemeColors.BLACK) return@setOnClickListener
            saveNewTheme(themeColor = ThemeColors.BLACK)
            recreate()
        }

        findViewById<CardView>(R.id.theme_blue).setOnClickListener {
            if (getAppTheme() == ThemeColors.BLUE) return@setOnClickListener
            saveNewTheme(themeColor = ThemeColors.BLUE)
            recreate()
        }

        findViewById<CardView>(R.id.theme_green).setOnClickListener {
            if (getAppTheme() == ThemeColors.GREEN) return@setOnClickListener
            saveNewTheme(themeColor = ThemeColors.GREEN)
            recreate()
        }

        findViewById<CardView>(R.id.theme_purple).setOnClickListener {
            if (getAppTheme() == ThemeColors.PURPLE) return@setOnClickListener
            saveNewTheme(themeColor = ThemeColors.PURPLE)
            recreate()
        }
        findViewById<CardView>(R.id.theme_yellow).setOnClickListener {
            if (getAppTheme() == ThemeColors.YELLOW) return@setOnClickListener
            saveNewTheme(themeColor = ThemeColors.YELLOW)
            recreate()
        }

        for (i in 0..12) {
            val buttonOperandId = resources.getIdentifier("btn$i", "id", packageName)
            val buttonOperand = findViewById<View>(buttonOperandId) as Button
            buttonOperand.setOnClickListener(controller)
        }

        for (i in 0..3) {
            val buttonOperandId = resources.getIdentifier("btnOperation$i", "id", packageName)
            val buttonOperand = findViewById<View>(buttonOperandId) as Button
            buttonOperand.setOnClickListener(controller)
        }

        findViewById<Button>(R.id.allClearAction).setOnClickListener(controller)

        findViewById<Button>(R.id.backSpaceAction).setOnClickListener(controller)

        findViewById<Button>(R.id.equals).setOnClickListener(controller)
    }

    private fun saveNewTheme(themeColor: ThemeColors) =
        getSharedPreferences(THEME_EDITOR_SAVE_KEY, Context.MODE_PRIVATE)
            .edit()
            .putString(THEME_SAVE_KEY, Gson().toJson(themeColor))
            .apply()


    private fun getAppTheme(): ThemeColors = Gson().fromJson(
        getSharedPreferences(THEME_EDITOR_SAVE_KEY, Context.MODE_PRIVATE)
            .getString(THEME_SAVE_KEY, ThemeColors.BLACK.toString()), ThemeColors::class.java)


    fun workingTextUpdate(newText: String) = newText.also { text -> workingsTV.text = text }

    fun resultTextUpdate(newText: String) = newText.also { text -> resultsTV.text = text }

    fun clearAllText() {
        workingsTV.text = String()
        resultsTV.text = String()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(WORKING_TEXT_SAVE_KEY, workingsTV.text.toString())
        outState.putString(RESULT_TEXT_SAVE_KEY, resultsTV.text.toString())
        super.onSaveInstanceState(outState)
    }

}