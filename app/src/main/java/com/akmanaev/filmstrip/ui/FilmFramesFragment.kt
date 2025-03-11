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
import com.akmanaev.filmstrip.model.FilmFramesViewModel
import com.akmanaev.filmstrip.model.NetworkResultState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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

    private val gestureListener = object :GestureDetector.OnGestureListener{
        override fun onDown(p0: MotionEvent): Boolean {
            return true
        }

        override fun onShowPress(p0: MotionEvent) {
        }

        override fun onSingleTapUp(p0: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
            return true
        }

        override fun onLongPress(p0: MotionEvent) {
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            try {
                val diffY = e2.y - e1?.y!!
                val diffX = e2.x - e1?.x!!
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                        if (diffX > 0) {
                            viewModel.prevFrame()
                        }
                        else {
                            viewModel.nextFrame()
                        }
                    }
                }
            }
            catch (exception: Exception) {
                exception.printStackTrace()
            }
            return true
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
        gestureDetector = GestureDetector(requireContext(), gestureListener)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmFramesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        root.setOnTouchListener { view, motionEvent ->
            gestureDetector.onTouchEvent(motionEvent)
        }
        viewModel.curFrame.observe(viewLifecycleOwner) { frame ->
            Glide.with(this)
                .load(frame.imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .fitCenter()
                .into(binding.imageViewFrame)
        }
        val filmId = requireArguments().getString("filmId")!!
        viewModel.fetchData(filmId).observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is NetworkResultState.Success -> {

                }

                is NetworkResultState.Loading -> {
                    // Handle UI when API calls is loading
                }

                is NetworkResultState.Error -> {
                    // Handle error API calls
                    // Example: Show error message to the user
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}