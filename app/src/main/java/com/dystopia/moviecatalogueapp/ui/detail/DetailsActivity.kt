package com.dystopia.moviecatalogueapp.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dystopia.moviecatalogueapp.R
import com.dystopia.moviecatalogueapp.core.data.Resource
import com.dystopia.moviecatalogueapp.core.data.source.remote.network.ApiInfo.BACKDROP_URL
import com.dystopia.moviecatalogueapp.core.data.source.remote.network.ApiInfo.POSTER_URL
import com.dystopia.moviecatalogueapp.core.domain.model.Movie
import com.dystopia.moviecatalogueapp.databinding.ActivityDetailsBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private val detailsViewModel: DetailsViewModel by viewModel()

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showLoading(true)

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        val movieDetails = intent.getParcelableExtra<Movie>(EXTRA_DATA)
        if (movieDetails != null) {
            val id = movieDetails.id.toString()
            detailsViewModel.setMovieDetails(id)
            detailsViewModel.getMovieDetails().observe(this, ::movieDetailsObserver)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun movieDetailsObserver(details: Resource<Movie>) {
        when (details) {
            is Resource.Error -> {
                showLoading(false)
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Resource.Loading -> showLoading(true)
            is Resource.Success -> {
                if (details.data != null) {
                    showLoading(false)
                    populateData(details.data!!)
                }
            }
        }
    }

    private fun populateData(data: Movie) {
        binding.toolbarLayout.title = data.title
        binding.tagline.text = data.tagline
        binding.details.text = resources.getString(
            R.string.year_genre_duration,
            data.releaseDate,
            data.runtime.toString()
        )
        binding.genres.text = data.genres
        binding.rating.text = data.voteAverage.toString()

        Glide.with(this)
            .load(POSTER_URL + data.posterPath)
            .transform(RoundedCorners(24))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.fgPoster)

        Glide.with(this)
            .asBitmap()
            .load(BACKDROP_URL + data.backdropPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.bgPoster.setImageBitmap(resource)
                    setColorByPalette(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}

            })

        binding.content.overview.text = data.overview

        showLoading(false)
        var statusFavorite = data.isFavorite
        setStatusFavorite(statusFavorite)
        binding.fab.setOnClickListener {
            statusFavorite = !statusFavorite
            detailsViewModel.setFavoriteMovie(data, statusFavorite)
            setStatusFavorite(statusFavorite)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.isGone = !state
        binding.content.nestedScroll.isInvisible = state
        binding.fab.isInvisible = state
        binding.appBar.isInvisible = state
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_24
                )
            )
        } else {
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_border_24
                )
            )
        }
    }

    private fun setColorByPalette(poster: Bitmap) {
        Palette.from(poster).generate { palette ->
            val defaultVal = resources.getColor(R.color.purple_200, theme)
            binding.toolbarLayout.setContentScrimColor(
                palette?.getMutedColor(defaultVal) ?: defaultVal
            )
            window.statusBarColor = palette?.getMutedColor(defaultVal) ?: defaultVal
        }
    }
}