package es.infolojo.newkeepitdroid.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import es.infolojo.newkeepitdroid.R

/**
 * Row of the home screen
 */
@Composable
fun ItemNote() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // region cabecera
        Row(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.secondary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Título de la nota
            Text(
                text = stringResource(R.string.title),
                // mayor peso para que ocupe mas espacio
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis, // idem a elipsis end en xml
                maxLines = 1,
                color = MaterialTheme.colorScheme.background,
                style = TextStyle(lineHeight = 1.5.em)
            )

            // Menú de opciones
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert, contentDescription = stringResource(R.string.more_options),
                    tint = MaterialTheme.colorScheme.background
                )
            }
            DropdownMenu(
                expanded = false,
                onDismissRequest = { /*TODO*/ }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(R.string.delete_note))
                    },
                    onClick = { /*TODO*/ }
                )
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(R.string.edit_note))
                    },
                    onClick = { /*TODO*/ }
                )
            }
        }
        // endregion cabecera

        // region cuerpo
        // TODO CONTINUE HERE

        // endregion cuerpo
    }
}

@Preview
@Composable
fun ItemNotePreview() {
    ItemNote()
}
