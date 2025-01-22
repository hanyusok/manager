package com.example.manager.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.manager.ui.ProductCreateScreen
import com.example.manager.ui.ProductDetailsScreen
import com.example.manager.ui.ProductListScreen
import androidx.navigation.compose.composable
//import com.example.manager.nav.AuthenticationDestination
import com.example.manager.nav.ProductCreateDestination
import com.example.manager.nav.ProductDetailsDestination
import com.example.manager.nav.ProductListDestination
//import com.example.manager.ui.SignInScreen

fun NavGraphBuilder.navRegistration(navController: NavController) {
    composable(ProductListDestination.route) {
        ProductListScreen(
            navController = navController
        )
    }

//    composable(AuthenticationDestination.route) {
//        SignInScreen(
//            navController = navController
//        )
//    }

//    composable(SignUpDestination.route) {
//        SignUpScreen(
//            navController = navController
//        )
//    }

    composable(ProductCreateDestination.route) {
        ProductCreateScreen(
            navController = navController
        )
    }

    composable(
        route = "${ProductDetailsDestination.route}/{${ProductDetailsDestination.productId}}",
        arguments = ProductDetailsDestination.arguments
    ) { navBackStackEntry ->
        val productId =
            navBackStackEntry.arguments?.getString(ProductDetailsDestination.productId)
        ProductDetailsScreen(
            productId = productId,
            navController = navController,
        )
    }

}