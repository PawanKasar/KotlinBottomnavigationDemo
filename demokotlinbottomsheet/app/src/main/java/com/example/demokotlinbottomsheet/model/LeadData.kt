package com.example.demokotlinbottomsheet.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LeadData(
    @SerializedName("name")
    @Expose
    var name: String?,

    @SerializedName("contact_number")
    @Expose
    var contact_number: String?,

    @SerializedName("project_name")
    @Expose
    var project_name: String?,

    @SerializedName("flat_details")
    @Expose
    var flat_details: String?,

    @SerializedName("property_cost")
    @Expose
    var property_cost: String?,

    @SerializedName("loan_requirement")
    @Expose
    var loan_requirement: String?,

    @SerializedName("state")
    @Expose
    var state: String?,

    @SerializedName("city")
    @Expose
    var city: String?
)
