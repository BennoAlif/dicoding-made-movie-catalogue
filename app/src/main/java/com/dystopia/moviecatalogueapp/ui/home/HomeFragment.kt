package com.dystopia.moviecatalogueapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dystopia.moviecatalogueapp.core.data.Resource
import com.dystopia.moviecatalogueapp.core.ui.MovieAdapter
import com.dystopia.moviecatalogueapp.databinding.FragmentHomeBinding
import com.dystopia.moviecatalogueapp.ui.detail.DetailsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val movieAdapter = MovieAdapter()
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            homeViewModel.movies.observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    when (movies) {
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            movieAdapter.setData(movies.data)
                        }
                    }
                }
            })

            with(binding.rvMovie) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}