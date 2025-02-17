package es.infolojo.newkeepitdroid.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.infolojo.newkeepitdroid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            // appBar preparada para incluir títulos he iconos
            TopAppBar(
                // shadow similar a elevaion en xml
                modifier = Modifier.fillMaxWidth().shadow(2.dp),
                title = {
                    Text(text = stringResource(R.string.my_notes), fontWeight = FontWeight.SemiBold)
                },

                // aquí se incluye la parte de los iconos
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_your_note)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    // DropDown component
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painterResource(R.drawable.baseline_filter_list_24),
                            contentDescription = stringResource(R.string.filter_by)
                        )
                    }
                    DropdownMenu(
                        expanded = false,
                        onDismissRequest = { /*TODO*/ }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(R.string.creation_date_filter))
                            },
                            onClick = { /*TODO*/ }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(R.string.ascend_filter))
                            },
                            onClick = { /*TODO*/ }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(R.string.descend_filter))
                            },
                            onClick = { /*TODO*/ }
                        )
                    }
                }
            )
        },
        // boton flotante
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_add_circle_outline_24),
                    contentDescription = stringResource(R.string.add_a_new_note)
                )
            }
        },
        bottomBar = { /*here will be the add*/ }
    ) { innerPadding ->
        // Contenido de la pantalla con contenedor columna y el contenido ajustado al centro
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
                    ItemNote()
                }
                // para hacer un clipToPadding false
                item {
                    Box(modifier = Modifier.size(80.dp))
                }
            }

            // diálogo de alerta
            AnimatedVisibility(visible = false) {
                AlertDialog(
                    text = { Text(text = stringResource(R.string.add_a_new_note)) },
                    title = {
                        Text(
                            text = stringResource(R.string.add_note),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onDismissRequest = { /* TODO */ },
                    confirmButton = { /* TODO */ }
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}