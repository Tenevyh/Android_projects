package com.tenevyh.android.scales4money.database.balance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tenevyh.android.scales4money.Balance
import com.tenevyh.android.scales4money.Limit

@Dao
interface BalanceDao {

    @Query("SELECT * FROM balances")
    fun getBalance(): LiveData<List<Balance>>

    @Insert
    fun addBalance(balance: Balance)
}