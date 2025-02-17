package es.infolojo.newkeepitdroid.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen() {
    Scaffold { innerPadding ->

        // TODO:search bar

        Column(
            modifier = Modifier.fillMaxWidth().padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // RecyclerView
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp,
                    top = 8.dp
                ),
                // contenido centrado
                horizontalAlignment = Alignment.CenterHorizontally,
                // esoacio entre items de 8dps
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(8) {
                    SearchItemNote()
                }
                // para hacer un clipToPadding false
                item {
                    Box(modifier = Modifier.size(80.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}
