package com.example.marsrealstate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.marsrealstate.databinding.FragmentBlank2Binding
import com.example.marsrealstate.overview.OverviewViewModel


class BlankFragment2 : Fragment() {

    private var _binding: FragmentBlank2Binding? = null
    private val binding get() = _binding!!

    private val viewModel: OverviewViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        _binding = FragmentBlank2Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

            Toast.makeText(activity, "Purchased Property ID: ${viewModel.propertyId.value} ", Toast.LENGTH_LONG).show()
                id.text = viewModel.propertyId.value
                type.text = viewModel.type.value
                totalprice.text = viewModel.price.value
                backtoList.setOnClickListener {
                    val action = BlankFragment2Directions.actionBlankFragment2ToOrderhistory()
                    findNavController().navigate(action)
                }

            }
    }


}