package ru.sevagrbnv.cinemaapp.presentation.movieFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import ru.sevagrbnv.cinemaapp.R
import ru.sevagrbnv.cinemaapp.databinding.FragmentMovieBinding

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel>()

    private var openPrevFragment: OpenPrevFragment? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenPrevFragment) {
            openPrevFragment = context
        } else {
            throw RuntimeException("Activity must implement OpenPrevFragment")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val appCompatActivity = activity as? AppCompatActivity
        appCompatActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity?.supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back)

        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovie(parseParams()).observe(viewLifecycleOwner) {
            val item = it[0]
            Picasso.get()
                .load(item.posterUrl)
                .into(binding.imagePoster)
            binding.textName.text = item.name
            binding.textDescription.text = item.description
            binding.textGenre.text = item.genre
            binding.textCountry.text = item.country
            binding.backButton.setOnClickListener {
                openPrevFragment?.back()
            }
        }
    }

    interface OpenPrevFragment {
        fun back()
    }


    private fun parseParams(): Long {
        val args = requireArguments()
        if (!args.containsKey(ID_KEY))
            throw RuntimeException("Params of screen mode not found")
        return args.getLong(ID_KEY)
    }

    companion object {

        private const val ID_KEY = "ID_KEY"

        fun startMovieFragment(id: Long): MovieFragment {
            return MovieFragment().apply {
                arguments = Bundle().apply {
                    putLong(ID_KEY, id)
                }
            }
        }
    }
}
