package com.akmanaev.filmstrip.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.akmanaev.filmstrip.R
import com.akmanaev.filmstrip.databinding.FragmentListFilmsBinding
import com.akmanaev.filmstrip.expandablerecyclerview.OnItemClickListener
import com.akmanaev.filmstrip.expandablerecyclerview.ParentData
import com.akmanaev.filmstrip.expandablerecyclerview.RecycleAdapter
import com.akmanaev.filmstrip.model.NetworkResultState
import com.akmanaev.filmstrip.model.ListFilmsViewModel

class ListFilmsFragment : Fragment() {

    companion object {
        fun newInstance() = ListFilmsFragment()
    }

    private var _binding: FragmentListFilmsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ListFilmsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.expandableRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.fetchData().observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is NetworkResultState.Success -> {
                    binding.loadingAnim.visibility = View.GONE
                    binding.expandableRecyclerView.adapter = RecycleAdapter(resultState.data,
                        object : OnItemClickListener {
                            override fun onItemClicked(parentData: ParentData) {
                                showFilm(parentData.subList.first().id)
                            }
                        })
                }

                is NetworkResultState.Loading -> {
                    binding.loadingAnim.visibility = View.VISIBLE
                }

                is NetworkResultState.Error -> {
                    binding.loadingAnim.visibility = View.GONE
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

    private fun showFilm(filmId: String) {
        val bundle = Bundle()
        bundle.putString("filmId", filmId)
        findNavController().navigate(R.id.action_listFilmsFragment_to_filmFramesFragment, bundle)
    }

}