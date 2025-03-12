package com.akmanaev.filmstrip.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.akmanaev.filmstrip.convertor.DateConverter
import com.akmanaev.filmstrip.databinding.FragmentFilmDetailsBinding
import com.akmanaev.filmstrip.model.FilmDetailsViewModel
import com.akmanaev.filmstrip.model.NetworkResultState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = FilmDetailsFragment()
    }

    private var _binding: FragmentFilmDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<FilmDetailsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val filmId = requireArguments().getString("filmId")!!
        viewModel.fetchData(filmId).observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is NetworkResultState.Success -> {
                    binding.loadingAnim.visibility = View.GONE
                    resultState.data?.let {
                        binding.textTitle.text = it.title
                        binding.textYear.text = it.year
                        binding.textStudio.text = it.studio
                        binding.textCategory.text = it.categories
                        val converter = DateConverter()
                        binding.textAddedDate.text = converter.write(it.adding)
                        Glide.with(requireContext())
                            .load(it.imageUrl)
                            .into(binding.imageTitle)
                    }
                }

                is NetworkResultState.Loading -> {
                    binding.loadingAnim.visibility = View.VISIBLE
                }

                is NetworkResultState.Error -> {
                    // Handle error API calls
                    // Example: Show error message to the user
                    binding.loadingAnim.visibility = View.GONE
                }
            }
        }
        return root
    }
}