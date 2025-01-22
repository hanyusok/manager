package com.example.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.manager.nav.ProductCreateDestination
import com.example.manager.nav.ProductDetailsDestination
import com.example.manager.nav.ProductListDestination
import com.example.manager.ui.ProductCreateScreen
import com.example.manager.ui.ProductDetailsScreen
import com.example.manager.ui.ProductListScreen
import com.example.manager.ui.theme.ManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var supabaseClient: SupabaseClient

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManagerTheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination
                Scaffold { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = ProductListDestination.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(ProductListDestination.route) {
                            ProductListScreen(
                                navController = navController
                            )
                        }

//                        composable(AuthenticationDestination.route) {
//                            SignInScreen(
//                                navController = navController
//                            )
//                        }

//                        composable(SignUpDestination.route) {
//                            SignUpScreen(
//                                navController = navController
//                            )
//                        }

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
                }
            }
        }
    }
}

