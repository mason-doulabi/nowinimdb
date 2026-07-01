package com.smmousavi.i_feature.profile.impl.xml

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import com.smmousavi.i_core.designsystem.R
import com.smmousavi.i_core.presentation.collectOnLifecycleStarted
import com.smmousavi.i_core.presentation.xml.HorizontalSpaceItemDecoration
import com.smmousavi.i_feature.profile.impl.ProfileScreenViewModel
import com.smmousavi.i_feature.profile.impl.databinding.FragmentProfileBinding
import com.smmousavi.i_feature.profile.impl.xml.rv.FavoriteMoviesListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    val binding get() = _binding!!

    private val viewModel: ProfileScreenViewModel by viewModels()
    private val favoriteMoviesAdapter by lazy {
        FavoriteMoviesListAdapter(
            onClick = {
                viewModel.setMovieAsFavorite(it.copy(favorite = it.favorite.not()))
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setupFavoriteAdapter()
        collectOnLifecycleStarted {
            viewModel.favorites.collect {
                favoriteMoviesAdapter.submitList(it)
            }
        }
        return binding.root
    }

    private fun setupFavoriteAdapter() = with(binding.rvFavoriteMovies) {
        adapter = favoriteMoviesAdapter
        setHasFixedSize(true)
        addItemDecoration(
            HorizontalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.small_space),
            ),
        )
        (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}