package es.infolojo.newkeepitdroid.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.infolojo.newkeepitdroid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel? = hiltViewModel(),
    isPreview: Boolean = false
) {
    var searchActive by rememberSaveable { mutableStateOf(true) }
    // TODO:search bar
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = stringResource(R.string.search),
        onQueryChange = {
            // TODO: search
        },
        onSearch = {
            // TODO SEARCH
        },
        active = searchActive,
        onActiveChange = {
            searchActive = it
        },
        placeholder = {
            Text(text = stringResource(R.string.search))
        },
        leadingIcon = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_arrow)
                )
            }
        }
    ) {
        // RecyclerView
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp,
                top = 16.dp
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

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}
