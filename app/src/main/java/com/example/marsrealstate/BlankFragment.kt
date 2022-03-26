package com.example.marsrealstate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.marsrealstate.database.Item
import com.example.marsrealstate.databinding.FragmentBlankBinding
import com.example.marsrealstate.overview.OverviewViewModel
import com.example.marsrealstate.overview.dataviewModelFactory


class BlankFragment : Fragment() {
    private var _binding: FragmentBlankBinding? = null
    private val binding get() =  _binding!!
    private val navigationArg : BlankFragmentArgs by navArgs()

  private val viewModels: OverviewViewModel by activityViewModels {
      dataviewModelFactory(
          (activity?.application as InventoryApplication).database.itemDao()
      )
  }
    lateinit var item: Item

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_blank, container, false)

        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModels
        binding.fragment = this@BlankFragment

        var img: String = navigationArg.itemImage
        var price : String = navigationArg.itemPrice
        var type: String = navigationArg.itemtype
        var id : String = navigationArg.id


        //saves in ViewModel
        viewModels.photstring.value = img

        viewModels.currentPrice.value = price
        binding.type.setText(type)


        binding.type.setOnClickListener {
            viewModels.setPrice(price)
            viewModels.setType(type)
            viewModels.setid(id)
            viewModels.setImgUrl(img)

            additem()

        }

    }


    private fun additem(){

                viewModels.addNewItem(
                    viewModels.propertyId.value.toString(),
                    viewModels.type.value.toString(),
                    viewModels.price.value.toString(),
                    viewModels.imgUrl.value.toString()
                )
                val action = BlankFragmentDirections.actionBlankFragmentToBlankFragment2()
                findNavController().navigate(action)
            }

    }
