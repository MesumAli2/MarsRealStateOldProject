package com.example.marsrealstate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marsrealstate.databinding.FragmentOrderhistoryBinding
import com.example.marsrealstate.overview.OverviewViewModel
import com.example.marsrealstate.overview.dataviewModelFactory


class Orderhistory : Fragment() {



    private var _binding : FragmentOrderhistoryBinding? = null
    private val binding get() =  _binding!!

    private val viewModel: OverviewViewModel by activityViewModels{
        dataviewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderhistoryBinding.inflate(inflater, container, false)
       return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adater = OrderhistoryAdapter{
            Toast.makeText(activity, "Property Type : ${it.itemType}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.adapter = adater
        viewModel.allItems.observe(viewLifecycleOwner){
            list -> adater.submitList(list)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }


}