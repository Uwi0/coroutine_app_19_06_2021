package com.kakapo.coroutineapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.kakapo.coroutineapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootLayout: ConstraintLayout = findViewById(R.id.rootLayout)
        val title: TextView = findViewById(R.id.title)
        val taps: TextView = findViewById(R.id.taps)
        val spinner: ProgressBar = findViewById(R.id.spinner)

        val database = getDatabase(this)
        val repository = TitleRepository(getNetworkService(), database.titleDao)
        //TODO lek error ndek kene pisan '<'
        val viewModel = ViewModelProvider(
            this,
            MainViewModel.FACTORY(repository)
        ).get(MainViewModel::class.java)

        rootLayout.setOnClickListener {
            viewModel.onMainViewClicked()
        }

        viewModel.title.observe(this){value ->
            value.let{
                title.text = it
            }
        }

        viewModel.taps.observe(this){value ->
            taps.text = value
        }

        viewModel.spinner.observe(this){value ->
            value.let { show->
                spinner.visibility = if (show) View.VISIBLE else View.GONE
            }
        }

        viewModel.snackbar.observe(this){text ->
            text?.let {
                Snackbar.make(rootLayout, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        }
    }
}