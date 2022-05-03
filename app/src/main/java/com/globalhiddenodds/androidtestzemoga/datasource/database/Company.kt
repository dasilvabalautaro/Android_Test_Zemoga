package com.globalhiddenodds.androidtestzemoga.datasource.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globalhiddenodds.androidtestzemoga.ui.data.CompanyView

@Entity(tableName = "company")
data class Company(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                   @ColumnInfo(name = "name")
                   val name: String,
                   @ColumnInfo(name = "catchPhrase")
                   val catchPhrase: String,
                   @ColumnInfo(name = "bs")
                   val bs: String )
fun Company.toCompanyView():
        CompanyView = CompanyView(
    id, name, catchPhrase, bs)