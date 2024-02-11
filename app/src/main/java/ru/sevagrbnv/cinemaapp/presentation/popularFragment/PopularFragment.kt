package ru.sevagrbnv.cinemaapp.presentation.popularFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.sevagrbnv.cinemaapp.R
import ru.sevagrbnv.cinemaapp.data.remote.NetworkResult
import ru.sevagrbnv.cinemaapp.databinding.FragmentPopularBinding
import ru.sevagrbnv.cinemaapp.presentation.recyclerView.MovieAdapter

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private val viewModel by viewModels<PopularViewModel>()
    private lateinit var movieListAdapter: MovieAdapter

    private var openFavoriteFragmentListener: OpenFavoriteFragmentListener? = null
    private var openMovieFragmentListener: OpenMovieFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenFavoriteFragmentListener) {
            openFavoriteFragmentListener = context
        } else {
            throw RuntimeException("Activity must implement OpenFavoriteFragmentListener")
        }
        if (context is OpenMovieFragmentListener) {
            openMovieFragmentListener = context
        } else {
            throw RuntimeException("Activity must implement OpenMovieFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        openFavoriteFragmentListener = null
        openMovieFragmentListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewState()
        binding.bottomButton.setOnClickListener {
            openFavoriteFragmentListener?.openFavoriteFragment()
        }
        setItemLongClickListener()
    }

    private fun setRecyclerViewState() {
        movieListAdapter = MovieAdapter()
        viewModel.movieList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.isVisible = false
                    binding.recyclerView.isVisible = true
                    binding.helpTextView.text = resources.getText(R.string.not_found)
                    response.data?.let {
                        binding.helpTextView.isVisible = it.isEmpty()
                        movieListAdapter.submitList(it)
                    }
                }
                is NetworkResult.Error -> {
                    binding.progressBar.isVisible = false
                    binding.recyclerView.isVisible = false
                    binding.helpTextView.text = resources.getText(R.string.data_error)
                    binding.helpTextView.isVisible = true
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.recyclerView.isVisible = false
                    binding.helpTextView.isVisible = false
                }
            }
        }
        with(binding.recyclerView) {
            adapter = movieListAdapter
        }
        setItemClickListener()
    }

    private fun setItemClickListener() {
        movieListAdapter.onItemClickListener = {
            openMovieFragmentListener?.openMovieFragment(it.id)
        }
    }

    private fun setItemLongClickListener() {
        movieListAdapter.onItemLongClickListener = {
            viewModel.addToFavorites(it)
        }
    }

    interface OpenFavoriteFragmentListener {
        fun openFavoriteFragment()
    }

    interface OpenMovieFragmentListener {
        fun openMovieFragment(id: Long)
    }

    companion object {

        fun startPopularFragment(): PopularFragment {
            return PopularFragment()
        }
    }
}