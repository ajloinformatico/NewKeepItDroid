package es.infolojo.newkeepitdroid.ui.screens.search

import android.util.Log
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
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import es.infolojo.newkeepitdroid.R
import es.infolojo.newkeepitdroid.ui.activities.main.events.MainEvents
import es.infolojo.newkeepitdroid.ui.screens.commons.RegularAlertDialogComponent
import es.infolojo.newkeepitdroid.ui.theme.ThemeHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel? = hiltViewModel(),
    isPreview: Boolean = false,
    navHostController: NavHostController? = null,
    mainEvents: (MainEvents) -> Unit = {}
) {
    // init viewModel
    viewModel?.init(
        mainEvents = mainEvents,
        navController = navHostController
    )

    // observe notes state
    val notes = viewModel?.notes?.collectAsState(initial = emptyList())
    val backgroundColor = ThemeHelper.getSurfaceBackGroundColor()

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        colors = SearchBarDefaults.colors(
            containerColor = backgroundColor,
            dividerColor = Color.LightGray, // opcional
            inputFieldColors = TextFieldDefaults.colors(
                focusedContainerColor = backgroundColor,
                unfocusedContainerColor = backgroundColor,
            )
        ),
        query = viewModel?.searchText?.value.orEmpty(),
        onQueryChange = {
            viewModel?.updateSearchText(it)
            Log.d("TonyTest", "onQuerySearch $it")
        },
        onSearch = {
            viewModel?.updateSearchText(it)
            Log.d("TonyTest", "oSearch")
        },
        active = true,
        onActiveChange = { /*no-op*/ },
        placeholder = {
            Text(text = stringResource(R.string.search))
        },
        leadingIcon = {
            IconButton(
                onClick = {
                    navHostController?.popBackStack()
                }
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(
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
            // in preview mode only load 8 items.
            items(notes?.takeIf { !isPreview }?.value?.size ?: 8) { index ->
                notes?.value?.getOrNull(index)?.let {
                    if (!isPreview) {
                        SearchItemNote(it, viewModel::manageHomeScreenGridEvents)
                    } else {
                        SearchItemNote()
                    }
                }
            }
            // para hacer un clipToPadding false
            item {
                Box(modifier = Modifier.size(80.dp))
            }
        }

        // Dialog alert to remove a note
        RegularAlertDialogComponent(
            title = "${viewModel?.noteToRemove?.title.orEmpty()} ${stringResource(R.string.will_be_removed).lowercase()}",
            text = "${stringResource(R.string.shure_to_remove)} ${viewModel?.noteToRemove?.title.orEmpty()} ?",
            onConfirm = {
                viewModel?.removeSelectedNote()
            },
            onDismiss = {
                viewModel?.restartRemoveState()
            },
            enabled = viewModel?.showAlertToRemove?.value == true
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}
