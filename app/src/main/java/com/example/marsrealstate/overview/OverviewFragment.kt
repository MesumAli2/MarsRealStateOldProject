package com.example.marsrealstate.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.example.marsrealstate.InventoryApplication
import com.example.marsrealstate.NotifiyWorker
import com.example.marsrealstate.databinding.FragmentOverviewBinding
import java.util.concurrent.TimeUnit


class OverviewFragment : Fragment() {

private val viewModels: OverviewViewModel by activityViewModels {
    dataviewModelFactory(
        (activity?.application as InventoryApplication).database.itemDao()
    )
}

    private var _binding:  FragmentOverviewBinding? = null
    private val binding get() =  _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


    _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.viewModel = viewModels

        binding.lifecycleOwner = viewLifecycleOwner


         fun createInputDataForUri(): Data {
            val builder = Data.Builder()
            viewModels.photo.value?.let {
                builder.putString("ImagesReceived", it.size.toString())
            }
            return builder.build()
        }

        val constraint = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()


        fun applyWorker(){
            val NotifyRequest = PeriodicWorkRequest.Builder(NotifiyWorker::class.java, 13, TimeUnit.MINUTES)
                .setInputData(createInputDataForUri())
                .setConstraints(constraint)
                .addTag("OUTPUT")
                .build()

            WorkManager.getInstance(this.requireContext())
                .enqueueUniquePeriodicWork("OUTPUT", ExistingPeriodicWorkPolicy.REPLACE, NotifyRequest)
        }



        viewModels.photo.observe(viewLifecycleOwner){
            val recyclerView = binding.photosGrid
            val adapter = PhotoGridAdapter{
                // Toast.makeText(activity, "Price: ${it.price}", Toast.LENGTH_SHORT).show()
                viewModels.photstring.value = it.imgSrcUrl
                val action = OverviewFragmentDirections.actionOverviewFragment2ToBlankFragment(it.price, it.imgSrcUrl, it.type, it.id)
                this.findNavController().navigate(action)
                applyWorker()
            }
            adapter.submitList(it)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        }


        binding.backtoList2.setOnClickListener{
            val action  = OverviewFragmentDirections.actionOverviewFragment2ToOrderhistory()
            findNavController().navigate(action)
        }
    }
}