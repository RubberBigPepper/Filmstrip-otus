package com.akmanaev.filmstrip.ui

import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.akmanaev.filmstrip.databinding.FragmentFilmFramesBinding
import com.akmanaev.filmstrip.io.Mp3Player
import com.akmanaev.filmstrip.model.FilmFramesViewModel
import com.akmanaev.filmstrip.model.NetworkResultState
import com.akmanaev.filmstrip.model.OneFrame
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class FilmFramesFragment : Fragment() {

    companion object {
        fun newInstance() = FilmFramesFragment()
    }

    private var _binding: FragmentFilmFramesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<FilmFramesViewModel>()

    private lateinit var gestureDetector: GestureDetector
    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmFramesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel.frames.observe(viewLifecycleOwner) { frames ->
            fillImageSlider(frames)
        }
        val filmId = requireArguments().getString("filmId")!!
        viewModel.fetchData(filmId).observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is NetworkResultState.Success -> {
                    binding.loadingAnim.visibility = View.GONE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillImageSlider(frames: List<OneFrame>){
        val imageList = frames.map { SlideModel(it.imageUrl) }
        binding.imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE)
        binding.imageSlider.setItemChangeListener(object : ItemChangeListener {
            override fun onItemChanged(position: Int) {
                //play mp3 if it exists
                Mp3Player.playMP3(frames[position].mp3Url)
            }
        })
    }
}