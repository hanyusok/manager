package com.example.manager.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.manager.ui.ProductCreateScreen
import com.example.manager.ui.ProductDetailsScreen
import com.example.manager.ui.ProductListScreen
import androidx.navigation.compose.composable
//import com.example.manager.nav.AuthenticationDestination
import com.example.manager.vm.ProductCreateViewModel
import com.example.manager.vm.ProductListViewModel
import com.example.manager.repo.ProductRepositoryImpl
//import com.example.manager.ui.SignInScreen

fun NavGraphBuilder.navRegistration(navController: NavController, viewModel: ProductListViewModel) {
    composable(ProductListDestination.route) {
        ProductListScreen(
            navController = navController,
            viewModel = viewModel
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
            navController = navController,
            viewModel = ProductCreateViewModel(ProductRepositoryImpl())
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