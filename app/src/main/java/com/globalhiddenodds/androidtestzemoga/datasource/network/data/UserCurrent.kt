package com.globalhiddenodds.androidtestzemoga.datasource.network.data
import com.globalhiddenodds.androidtestzemoga.datasource.database.UserCurrent
import com.squareup.moshi.Json

data class UserCurrent(@field:Json(name = "id") val id: Int,
                       @field:Json(name = "name") val name: String,
                       @field:Json(name = "email") val email: String,
                       @field:Json(name = "website") val website: String,
                       @field:Json(name = "phone") val phone: String,
                       @field:Json(name = "company") val company: Company
){
    fun toUserCurrentDatabase(): UserCurrent {
        return UserCurrent(id, name, email, website, phone)
    }

    fun toCompanyDatabase(): com.globalhiddenodds
    .androidtestzemoga.datasource.database.Company{
        return com.globalhiddenodds.androidtestzemoga
            .datasource.database
            .Company(id, company.name, company.catchPhrase, company.bs)
    }
}