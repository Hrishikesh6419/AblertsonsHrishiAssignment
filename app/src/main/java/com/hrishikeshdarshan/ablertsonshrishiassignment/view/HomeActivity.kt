package com.hrishikeshdarshan.ablertsonshrishiassignment.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrishikeshdarshan.ablertsonshrishiassignment.databinding.ActivityHomeBinding
import com.hrishikeshdarshan.ablertsonshrishiassignment.home.HomeViewModel
import com.hrishikeshdarshan.albertsonsassignment.view.WordAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var wordAdapter: WordAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        registerListeners()
        registerObservers()
    }

    private fun bindView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wordAdapter = WordAdapter()
        with(binding.listMeanings) {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = wordAdapter
        }
    }

    private fun registerListeners() {
        binding.buttonSubmitAcronym.setOnClickListener {
            val enteredText =
                binding.editTextEnterAcronym.text?.toString() ?: return@setOnClickListener
            if (enteredText.isNotEmpty()) {
                viewModel.fetchLongForms(enteredText)
            } else {
                wordAdapter.update(emptyList())
                Toast.makeText(this@HomeActivity, ENTER_ACRONYM, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun registerObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.acromineEvent.observe(this@HomeActivity) {
                when (it) {
                    is HomeViewModel.AcromineEvent.Success -> {
                        binding.progressBar.visibility = View.GONE
                        wordAdapter.update(it.response)
                    }
                    is HomeViewModel.AcromineEvent.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        wordAdapter.update(emptyList())
                        Toast.makeText(this@HomeActivity, it.errorMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is HomeViewModel.AcromineEvent.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        wordAdapter.update(emptyList())
                    }
                }
            }
        }
    }

    companion object {
        const val ENTER_ACRONYM = "Please enter an acronym"
    }
}