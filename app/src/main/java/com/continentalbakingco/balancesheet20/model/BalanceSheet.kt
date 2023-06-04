package com.continentalbakingco.balancesheet20.model

import com.google.gson.annotations.SerializedName

data class BalanceSheet (
    @SerializedName("accountReceivables")
    val AccountReceivables: Double,
    @SerializedName("account_Id")
    val Account_Id: Int,
    @SerializedName("accumulatedCustomerBalance")
    val AccumulatedCustomerBalance: Double,
    @SerializedName("balanceSheetNumber")
    val BalanceSheetNumber: String,
    @SerializedName("cashChequeSales")
    val CashChequeSales: Double,
    @SerializedName("comments")
    val Comments: String,
    @SerializedName("created_At")
    val Created_At: String,
    @SerializedName("creditNotesBinTransfers")
    val CreditNotesBinTransfers: Double,
    @SerializedName("customerBalanceCollection")
    val CustomerBalanceCollection: Double,
    @SerializedName("deleted_At")
    val Deleted_At: String?,
    @SerializedName("easterAccumulatedCustomerBalance")
    val EasterAccumulatedCustomerBalance: Double,
    @SerializedName("easterCustomerBalanceCollection")
    val EasterCustomerBalanceCollection: Double,
    @SerializedName("easterNonARCreditSales")
    val EasterNonARCreditSales: Double,
    @SerializedName("easterRemainingCustomerBalance")
    val EasterRemainingCustomerBalance: Double,
    @SerializedName("excessCharges")
    val ExcessCharges: Double,
    @SerializedName("id")
    val Id: Int,
    @SerializedName("netOverageShortage")
    val NetOverageShortage: Double,
    @SerializedName("nonARCreditsales")
    val NonARCreditsales: Double,
    @SerializedName("otherMisc")
    val OtherMisc: Int,
    @SerializedName("pickupLoadValue")
    val PickupLoadValue: Double,
    @SerializedName("postDatedCheques")
    val PostDatedCheques: Double,
    @SerializedName("returnedCheques")
    val ReturnedCheques: Double,
    @SerializedName("returnsDamage")
    val ReturnsDamage: Double,
    @SerializedName("routeDiscount")
    val RouteDiscount: Double,
    @SerializedName("routeExpense")
    val RouteExpense: Double,
    @SerializedName("routeType_Id")
    val RouteType_Id: Int,
    @SerializedName("route_Id")
    val Route_Id: Int,
    @SerializedName("sampleRecoveryAccumulated")
    val SampleRecoveryAccumulated: Double,
    @SerializedName("samplesIssuedToday")
    val SamplesIssuedToday: Double,
    @SerializedName("sheet_Date")
    val Sheet_Date: String,
    @SerializedName("statementOpeningBalance")
    val StatementOpeningBalance: Double,
    @SerializedName("taxExemption")
    val TaxExemption: Double,
    @SerializedName("totalAccountedFor")
    val TotalAccountedFor: Double,
    @SerializedName("totalCustomerBalance")
    val TotalCustomerBalance: Double,
    @SerializedName("totalSamples")
    val TotalSamples: Double,
    @SerializedName("totalToAccountFor")
    val TotalToAccountFor: Double,
    @SerializedName("truckStock")
    val TruckStock: Double,
    @SerializedName("updated_At")
    val Updated_At: String?,
    @SerializedName("user_Id")
    val User_Id: Int
        )