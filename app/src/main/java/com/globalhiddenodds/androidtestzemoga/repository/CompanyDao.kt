package com.globalhiddenodds.androidtestzemoga.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globalhiddenodds.androidtestzemoga.datasource.database.Company

// Pattern Observer
@Dao
interface CompanyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(company: Company)

    @Query("SELECT * FROM company WHERE id = :id")
    fun getCompany(id: Int): Company

    @Query("DELETE FROM company")
    suspend fun deleteTable()
}