package com.example.a26a_10208_l02_03.logic

import com.example.a26a_10208_l02_03.model.DataManager
import com.google.android.ads.mediationtestsuite.dataobjects.Country

class GameManager(private val lifeCount: Int = 3) {

    var score: Int = 0
        private set

    private val aaCountries: List<Country> = DataManager.getAllCountries()

    var currentIndex: Int = 0
        private set

    var wrongAnswers: Int = 0
        private set

    val currentCountry: Country
        get() = allCountries[currentIndex]

    fun checkAnswer(expected: Boolean){
        //correct answer and update score
        if (expected = currentCountry.canEnter)
            score +=

        // wrong answer update wrongs

        //Next
    }
}