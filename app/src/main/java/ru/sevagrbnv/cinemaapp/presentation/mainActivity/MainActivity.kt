package ru.sevagrbnv.cinemaapp.presentation.mainActivity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import dagger.hilt.android.AndroidEntryPoint
import ru.sevagrbnv.cinemaapp.R
import ru.sevagrbnv.cinemaapp.presentation.favoriteFragment.FavoriteFragment
import ru.sevagrbnv.cinemaapp.presentation.movieFragment.MovieFragment
import ru.sevagrbnv.cinemaapp.presentation.popularFragment.PopularFragment


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    FavoriteFragment.OpenPopularFragmentListener,
    PopularFragment.OpenFavoriteFragmentListener,
    PopularFragment.OpenMovieFragmentListener,
    MovieFragment.OpenPrevFragment {

    private var cinemaContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cinemaContainer = findViewById(R.id.cinema_container)
        if (savedInstanceState == null)
            setMainFragmentContainer(PopularFragment())
    }


    private fun setMainFragmentContainer(mainFragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_container)

        if (currentFragment != null && currentFragment::class == mainFragment::class) {
            return
        }

        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, mainFragment)

        if (currentFragment != null) {
            transaction.addToBackStack("fragmentName")
        }

        transaction.commit()
    }

    private fun isOnePaneMode(): Boolean {
        return cinemaContainer == null
    }

    override fun openFavoriteFragment() {
        setMainFragmentContainer(FavoriteFragment.startFavoriteFragment())
    }

    override fun openPopularFragment() {
        setMainFragmentContainer(PopularFragment.startPopularFragment())
    }

    override fun openMovieFragment(id: Long) {
        val transaction = supportFragmentManager.beginTransaction()
        if (isOnePaneMode()) {
            transaction.replace(R.id.main_container, MovieFragment.startMovieFragment(id))
        } else {
            val container = findViewById<FragmentContainerView>(R.id.cinema_container)
            val text = findViewById<TextView>(R.id.textSecondFragmentIsEmpty)
            container.isVisible = true
            text.isVisible = false

            try {
                transaction.replace(R.id.cinema_container, MovieFragment.startMovieFragment(id))
            } catch (e: Exception) {
                container.isVisible = false
                text.text = getString(R.string.data_error)
                text.isVisible = true
            }
        }
        transaction.addToBackStack(null).commit()
    }

    override fun back() {
        supportFragmentManager.popBackStack()
    }
}