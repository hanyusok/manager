package com.example.manager.ui

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.manager.R
import com.example.manager.vm.ProductListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
private fun ProductCreateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen (
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProductListViewModel
) {
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.getProducts()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.getProducts() }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        scrolledContainerColor = MaterialTheme.colorScheme.primary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    title = {
                        Text(
                            text = stringResource(id = R.string.product_list_text_screen_title),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            },
            floatingActionButton = {
                ProductCreateButton(
                    onClick = {
                        navController.navigate("product_create")
                    }
                )
            }
        ) { innerPadding ->
            Column(modifier = modifier.padding(paddingValues = innerPadding)) {
               val productList = viewModel.productList.collectAsState(initial = listOf()).value
               if (!productList.isNullOrEmpty()) {
                   LazyColumn(
                       modifier = Modifier.fillMaxSize(),
                       contentPadding = PaddingValues(5.dp)
                   ) {
                       itemsIndexed(
                           items = productList,
                           key = { _, product -> product.name }) { _, item ->
                           val state = rememberSwipeToDismissBoxState(
                               confirmValueChange = {
                                   if (it == SwipeToDismissBoxValue.Settled) {
                                       viewModel.removeItem(item)
                                   }
                                   true
                               }
                           )
                           SwipeToDismissBox(
                               state = state,
                               backgroundContent = {
                                   val color by animateColorAsState(
                                       targetValue = when (state.dismissDirection) {
                                           SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                           SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.primary
                                           SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.primary.copy(
                                               alpha = 0.2f
                                           )
                                           null -> Color.Transparent
                                       }
                                   )
                                   Box(
                                       modifier = modifier
                                           .fillMaxSize()
                                           .background(color = color)
                                           .padding(16.dp),
                                   ) {
                                       Icon(
                                           imageVector = Icons.Filled.Delete,
                                           contentDescription = null,
                                           tint = MaterialTheme.colorScheme.primary,
                                           modifier = modifier.align(Alignment.CenterEnd)
                                       )
                                   }
                               },
                               content = {
                                   ProductListItem(
                                       product = item,
                                       modifier = modifier,
                                       onClick = {
                                           navController.navigate("product_details/${item.id}")
                                       }
                                   )
                               },
                               enableDismissFromEndToStart = true
                           )
                       }
                   }
               } else {
                   Text(
                       text = "No products found",
                       modifier = modifier.align(Alignment.CenterHorizontally)
                   )
               }

            }

        }
    }
}