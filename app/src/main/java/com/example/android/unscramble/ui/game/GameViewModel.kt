package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var wordList: MutableList<String> = mutableListOf()
    private lateinit var currentWord:String
    private var _score = MutableLiveData(0)
    val score:LiveData<Int> get() = _score
    private var _currentWordCount = MutableLiveData(0)
    val currentWordCount:LiveData<Int> get() = _currentWordCount
    private var _currentScrambledWord:MutableLiveData<String> = MutableLiveData("")
    val currentScrambleWord: LiveData<String> get() = _currentScrambledWord

    init
    {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }
    /*
* Re-initializes the game data to restart the game.
*/
    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordList.clear()
        getNextWord()
    }
    private fun getNextWord()
    {
        currentWord = allWordsList.random()
        val temp = currentWord.toCharArray()
        temp.shuffle()
        while (String(temp) == currentWord)
        {
            temp.shuffle()
        }
        if(wordList.contains(currentWord))
        {
            getNextWord()
        }
        else
        {
            _currentScrambledWord.value = String(temp)
            wordList.add(currentWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
        }
    }
    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
    private fun increaseScore()
    {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }
    override fun onCleared()
    {
        super.onCleared()
        Log.d("GameFragment","GameViewModel Destroyed")
    }
}
