package ru.sevagrbnv.cinemaapp.presentation.favoriteFragment

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
import ru.sevagrbnv.cinemaapp.databinding.FragmentFavoriteBinding
import ru.sevagrbnv.cinemaapp.presentation.popularFragment.PopularFragment
import ru.sevagrbnv.cinemaapp.presentation.recyclerView.MovieAdapter

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel>()
    private lateinit var movieListAdapter: MovieAdapter

    private var openPopularFragment: OpenPopularFragmentListener? = null
    private var openMovieFragmentListener: PopularFragment.OpenMovieFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenPopularFragmentListener) {
            openPopularFragment = context
        } else {
            throw RuntimeException("Activity must implement OpenPopularFragmentListener")
        }
        if (context is PopularFragment.OpenMovieFragmentListener) {
            openMovieFragmentListener = context
        } else {
            throw RuntimeException("Activity must implement OpenMovieFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        openPopularFragment = null
        openMovieFragmentListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewState()
        binding.bottomButton.setOnClickListener {
            openPopularFragment?.openPopularFragment()
        }

//        binding.searchButton.setOnClickListener {
//            val query = binding.edTextDesc.text.toString()
//            if (viewModel.checkCorrectInput(query)) {
//                viewModel.getListOfGifs(query)
//            }
//        }
    }

    private fun setRecyclerViewState() {
        movieListAdapter = MovieAdapter()
        viewModel.movieList.observe(viewLifecycleOwner) { data ->
            if (data.isNotEmpty()) {
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = true
                binding.helpTextView.isVisible = false
                movieListAdapter.submitList(data)
            } else if (data.isEmpty()) {
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = false
                binding.helpTextView.text = resources.getText(R.string.not_found)
                binding.helpTextView.isVisible = true
            } else if (data == null) {
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = false
                binding.helpTextView.text = resources.getText(R.string.data_error)
                binding.helpTextView.isVisible = true
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

    interface OpenPopularFragmentListener {
        fun openPopularFragment()
    }

    companion object {
        fun startFavoriteFragment(): FavoriteFragment {
            return FavoriteFragment()
        }
    }
}