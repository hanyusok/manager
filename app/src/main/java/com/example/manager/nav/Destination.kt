package com.example.manager.nav

import androidx.navigation.NavType
import androidx.navigation.navArgument

object Destination {
    val route: String
    val title: String
}

object ProductListDestination : Destination {
    override val route = "product_list"
    override val title = "Product List"
}

object ProductDetailsDestination : Destination {
    override val route = "product_details"
    override val title = "Product Details"
    const val productId = "product_id"
    val arguments = listOf(navArgument(name = productId) {
        type = NavType.StringType
    })
    fun createRouteWithParam(productId: String) = "$route/${productId}"
}

object ProductCreateDestination : Destination {
    override val route = "product_create"
    override val title = "Create Product"
}