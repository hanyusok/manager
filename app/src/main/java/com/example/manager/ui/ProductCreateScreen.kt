package com.example.manager.ui

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.manager.R
import com.example.manager.vm.ProductCreateViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCreateScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProductCreateViewModel
){
    Scaffold(
    topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            colors = TopAppBarColors(
                titleContentColor =  MaterialTheme.colorScheme.primary,
                actionIconContentColor =  MaterialTheme.colorScheme.primary,
                navigationIconContentColor =  MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.primary,
                scrolledContainerColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Text(
                    text = stringResource(R.string.add_product_text_screen_title),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            },
        )
    }
    ) { padding ->
        val navigateProductCreateSuccess = viewModel.showSuccessMessage.collectAsState(initial = false).value
        val isLoading =
            viewModel.isLoading.collectAsState(initial = false).value
        if (isLoading == true) {
            LoadingScreen(message = "Adding Product",
                onCancelSelected = {
                    navController.navigateUp()
                })
        } else {
            SuccessScreen(
                message = "Product added",
                onMoreAction = {
//                    viewModel.onAddMoreProductSelected()
                },
                onNavigateBack = {
                    navController.navigateUp()
                })
        }

    }


}