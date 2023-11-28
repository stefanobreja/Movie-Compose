package com.obi.moviecompose.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obi.moviecompose.R

@Composable
fun SectionItem(
    @DrawableRes icon: Int,
    @StringRes textRes: Int,
    modifier: Modifier
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = icon), contentDescription = null, tint = Color.LightGray)
        Text(text = stringResource(id = textRes), fontSize = 24.sp, modifier = Modifier.padding(8.dp), color = Color.LightGray)
    }
}

@Preview(showBackground = true)
@Composable
fun SectionItemPreview() {
    SectionItem(R.drawable.ic_trending_24, R.string.app_name, Modifier.padding(0.dp))
}