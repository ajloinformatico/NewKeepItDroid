package es.infolojo.newkeepitdroid.ui.screens.add

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.infolojo.newkeepitdroid.R

private const val CLASS_NAME = "AddScreen"

@Composable
fun AddScreen() {
    // estructura / esqueleto
    Scaffold(
        // dentro monta su topbar
        topBar = {
            // fila que ocupa todo el ancho
            Row(modifier = Modifier.fillMaxWidth()) {
                // Icono pata volver atrás
                IconButton(
                    onClick = { Log.d(CLASS_NAME, "Back button clicked") }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_arrow),
                    )
                }

                // Espacio entre iconos con peso 1 para que ocupe el espacio disponible
                Spacer(modifier = Modifier.weight(1f))
                // Icono de guardar
                IconButton(
                    onClick = { Log.d(CLASS_NAME, "save button clicked") }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_save_24),
                        contentDescription = stringResource(R.string.save_icon)
                    )
                }
            }
        }
    ) { paddingValues ->
        // Scaffold (esqueleto) nos devuelve siempre los paddings para ajustar al tamaño disponible y no al edge to edge
        // Arranca el contenido con una columna quew ocupa el ancho disponible menos paddinbgs de Scaffold
        Column(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
            // Otra columna con un margen de 16 dp y espacio entre elementos de 8 dp
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // region bloqueCalendario
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    // todate text color with buildAnnotatedString
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append("MARCH")
                        }
                        append("25")
                    }
                )
                Text(text = "2025 WEDNESDAY")
                // endregion bloqueCalendario
            }

            HorizontalDivider(modifier = Modifier.height(2.dp))

            // region nota
            // La nota va en su propia columna para tener mas márgenes
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                // para incluir un campo de texto editable hemos de usar un TextField
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = true,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                    value = "",
                    onValueChange = { /* TODO */ },
                    placeholder = {
                        Text(
                            text = "Note title",
                            color = Color.Gray
                        )
                    }
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = true,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    value = "",
                    onValueChange = { /* TODO */ },
                    placeholder = {
                        Text(
                            text = "Note title",
                            color = Color.Gray,
                            fontWeight = FontWeight.Light
                        )
                    }
                )
            }
            // endregion nota
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    AddScreen()
}
