package com.obi.moviecompose.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obi.moviecompose.domain.models.Person

@Composable
fun PersonItemList(persons: List<Person>, modifier: Modifier = Modifier) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(persons.size) { index ->
                val person = persons[index]
                PersonItem(person.profilePath, person.name)
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PersonItemListPreview() {
    PersonItemList(
        listOf(
            Person.Cast("", "Person"),
            Person.Cast("", "Profesor McQucazoooooooo")
        ),
        Modifier.padding(0.dp)
    )
}