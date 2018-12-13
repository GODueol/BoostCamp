package com.android.godueol.boostcamp
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.android.godueol.boostcamp.databinding.ActivityMovieSearchBinding
import com.android.godueol.boostcamp.utlis.BaseActivity


class MovieSearchActivity : BaseActivity() {


    lateinit var binding : ActivityMovieSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_movie_search)
        binding.vm = MovieSearchViewModel(rxBinder)


        /*binding.searchEtx.setOnKeyListener { v, keyCode, event ->
            Log.d("test",keyCode.toString()+"/"+event.action)
            //Enter key Action
            if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                Log.d("test","test")
                return@setOnKeyListener true
            }
            false
        }
*/
       /* binding.searchEtx.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            Log.d("test",actionId.toString()+"/"+event.action)
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                }
                else ->
                    // 기본 엔터키 동작
                    return@OnEditorActionListener false
            }// 검색 동작
            true
        })
*/
    }
}
