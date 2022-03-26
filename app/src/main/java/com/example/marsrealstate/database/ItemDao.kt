package com.example.marsrealstate.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * from item where id = :id")
    fun getItem(id:Int) : Flow<Item>

    @Query("SELECT * from item where id = :id")
    fun checkitem(id:Int) : Flow<Item>

    @Query("SELECT * from item ORDER by id DESC")
    fun getItems(): Flow<List<Item>>


}