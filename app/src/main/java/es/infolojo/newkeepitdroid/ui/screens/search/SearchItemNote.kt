package es.infolojo.newkeepitdroid.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
 * Row of the search screen
 */
@Composable
fun SearchItemNote() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            Modifier.weight(1f).padding(end = 24.dp),
        ) {
            Column {
                Text(
                    text = stringResource(R.string.title),
                    style = TextStyle(lineHeight = 1.5.em),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis, // idem a elipsis end en xml
                    maxLines = 1,
                )
                Text(
                    text = stringResource(R.string.note_content),
                    color = Color.Gray,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 16.sp,
                    style = TextStyle(lineHeight = 1.5.em)
                )
            }
        }
        Row {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = stringResource(R.string.go_to_detail),
                    tint = Color.Gray
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SearchItemNotePreview() {
    SearchItemNote()
}
