package com.smmousavi.i_feature.search.impl.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.SimpleItemAnimator
import com.smmousavi.i_core.designsystem.R
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.presentation.UiState
import com.smmousavi.i_core.presentation.collectOnLifecycleStarted
import com.smmousavi.i_core.presentation.afterTextChanged
import com.smmousavi.i_core.presentation.xml.VerticalSpaceItemDecoration
import com.smmousavi.i_feature.search.impl.SearchScreenViewModel
import com.smmousavi.i_feature.search.impl.databinding.FragmentSearchBinding
import com.smmousavi.i_feature.search.impl.xml.rv.SearchListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchScreenViewModel by viewModels()

    private val searchListAdapter by lazy {
        SearchListAdapter(
            onClick = {},
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEditTextWatcher()
        setupRecyclerView()

        collectOnLifecycleStarted {
            viewModel.searchMovieState.collect {
                renderUiState(it)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupEditTextWatcher() {
        binding.edtSearchBox.afterTextChanged()
            .onEach { query -> viewModel.onQueryChange(query) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupRecyclerView() = with(binding.rvSearchResult) {
        adapter = searchListAdapter
        setHasFixedSize(true)
        addItemDecoration(
            VerticalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.small_space),
            ),
        )
        (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
    }

    private fun renderUiState(
        state: UiState<List<MovieModel>>,
    ) {
        when (state) {
            is UiState.Error -> {}
            UiState.Idle -> {}
            UiState.Loading -> {}
            is UiState.Success -> {
                searchListAdapter.submitList(state.data)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}