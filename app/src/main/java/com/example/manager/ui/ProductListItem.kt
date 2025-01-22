package com.example.manager.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.manager.model.Product
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import coil.compose.rememberImagePainter
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProductListItem(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Row(
            modifier = modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                contentDescription = null,
                painter = rememberAsyncImagePainter(product.image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .size(64.dp)
            )
            Text(
                text = product.name,
                modifier = modifier.weight(1.0f)
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}