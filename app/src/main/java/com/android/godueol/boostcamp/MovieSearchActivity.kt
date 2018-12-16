package com.android.godueol.boostcamp

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.godueol.boostcamp.databinding.ActivityMovieSearchBinding
import com.android.godueol.boostcamp.utlis.BaseActivity

class MovieSearchActivity : BaseActivity() {

    lateinit var binding: ActivityMovieSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_search)
        binding.vm = MovieSearchViewModel(rxBinder)
    }
}