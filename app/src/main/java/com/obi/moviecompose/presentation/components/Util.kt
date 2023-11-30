package com.obi.moviecompose.presentation.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow

@Composable
internal fun LazyListState.OnHalfElementsReached(loadMore: () -> Unit) {
    val shouldShowMore = remember {
        derivedStateOf {
            layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount / 2
        }
    }
    LaunchedEffect(shouldShowMore) {
        snapshotFlow { shouldShowMore.value }.collect { showMore -> if (showMore) loadMore() }
    }
}