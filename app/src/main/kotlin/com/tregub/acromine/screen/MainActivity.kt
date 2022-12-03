package com.tregub.acromine.screen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tregub.acromine.databinding.ActivityMainBinding
import com.tregub.acromine.screen.data.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

/**
 * The root class that represents the owner of the entire user interface.
 *  Done without fragments to make it easier to implement.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val adapter: DefinitionsAdapter by lazy { DefinitionsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.viewState
                .filter { state -> state is MainViewState.Success }
                .map { state -> (state as MainViewState.Success).data }
                .collect(adapter::submitList)
        }
        ActivityMainBinding.inflate(layoutInflater)
            .also { binding ->
                binding.lifecycleOwner = this
                binding.viewModel = viewModel
                binding.adapter = adapter
                binding.layoutManager =
                    LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL,
                        false,
                    )
            }
            .root
            .let(::setContentView)
    }
}
