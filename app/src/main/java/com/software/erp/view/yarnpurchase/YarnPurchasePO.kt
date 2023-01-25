package com.software.erp.view.yarnpurchase

data class YarnPurchasePO(
    val invoiceNo: String,
    val date: String,
    val spinnerMill: String,
    val goodsDesc: String,
    val noOfBags: String,
    val qtyInKgs: String,
    val price: String,
    val gst: String,
    val value: String,
    val lotTrackName: String,
    val trackingID: String
)
