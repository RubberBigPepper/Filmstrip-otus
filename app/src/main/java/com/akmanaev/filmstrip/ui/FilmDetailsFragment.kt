package com.akmanaev.filmstrip.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akmanaev.filmstrip.databinding.FragmentListFilmsBinding

class FilmDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = FilmDetailsFragment()
    }

    private var _binding: FragmentListFilmsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
}