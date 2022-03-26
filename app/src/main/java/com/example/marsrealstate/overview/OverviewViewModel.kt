package com.example.marsrealstate.overview

import androidx.lifecycle.*
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.marsrealstate.NotifiyWorker
import com.example.marsrealstate.database.Item
import com.example.marsrealstate.database.ItemDao
import com.example.marsrealstate.network.MarsApi
import com.example.marsrealstate.network.MarsPhoto
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel(private val itemmDao: ItemDao) : ViewModel() {


    private val _propertyId = MutableLiveData<String>("")
    val propertyId:LiveData<String> = _propertyId

    private val _type = MutableLiveData<String>("")
    val type: LiveData<String> =_type

    private val _price = MutableLiveData<String>("")
    val price: LiveData<String> =_price


    private val _imgUrl = MutableLiveData<String>("")
    val imgUrl: LiveData<String> = _imgUrl

    val photstring = MutableLiveData("")
    val currentPrice = MutableLiveData<String>("")

    fun setid(id: String){
        _propertyId.value = id
    }
    fun setType(type: String){
        _type.value = type
    }
    fun setPrice(price: String){
        _price.value = price
    }
    fun setImgUrl(img: String){
        _imgUrl.value = img
    }


    //current network status
    private val _statues = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus> = _statues


    //Stores Mars Item List
    private val _photo = MutableLiveData<List<MarsPhoto>>()
    val photo: LiveData<List<MarsPhoto>> = _photo



    init {
        getMarsPhoto()
    }


    //Mars api call to retrofit
    private fun getMarsPhoto(){
        viewModelScope.launch {
            _statues.value = MarsApiStatus.LOADING
            try {

                _photo.value = MarsApi.retrofitService.getPhotos()
                _statues.value = MarsApiStatus.DONE
            }catch (e: Exception){
                _statues.value = MarsApiStatus.ERROR
                _photo.value = listOf()
            }


        }
    }


    //Insert mars Item into Room database
    private fun inserttem(item: Item){
        viewModelScope.launch {
            itemmDao.insert(item)
        }
    }

    //format item entry
    private fun getNewItemEntry(itemId: String,itemtype: String, itemPrice: String, Img: String): Item {
        return Item(
         id = itemId.toInt(),
            itemType = itemtype,
            itemPrice = itemPrice.toDouble(),
            imgUrl =  Img
        )
    }

    //UI addItem function
    fun addNewItem(itemId: String, itemtype: String, itemPrice: String, ImgUrl: String) {
        val newItem = getNewItemEntry(itemId, itemtype, itemPrice, ImgUrl)
        inserttem(newItem)
    }


    //Retrieve all items database
    val allItems: LiveData<List<Item>> = itemmDao.getItems().asLiveData()




}

class dataviewModelFactory(private val itemmDao: ItemDao): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return OverviewViewModel(itemmDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}