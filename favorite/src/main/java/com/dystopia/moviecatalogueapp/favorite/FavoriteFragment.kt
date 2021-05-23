package com.dystopia.moviecatalogueapp.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.dystopia.moviecatalogueapp.core.ui.MovieAdapter
import com.dystopia.moviecatalogueapp.favorite.databinding.FragmentFavoriteBinding
import com.dystopia.moviecatalogueapp.ui.detail.DetailsActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        if (activity != null) {
            val movieAdapter = MovieAdapter()
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            favoriteViewModel.favoriteMovies.observe(viewLifecycleOwner, { movies ->
                movieAdapter.setData(movies)
            })

            with(binding.rvMovie) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

}