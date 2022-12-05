package com.tregub.acromine.screen

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tregub.acromine.R
import com.tregub.acromine.screen.data.AcronymDefinitionViewState
import com.tregub.acromine.screen.data.MainViewState
import com.tregub.acromine.ui.theme.AcromineTheme
import com.tregub.acromine.ui.theme.defaultIndentDp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AcromineTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val viewState: MainViewState by viewModel.viewState.collectAsState()
                    AcronymsSearch(
                        viewState = viewState,
                        onAcronymChange = viewModel::getDefinitions,
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.NEXUS_5,
    widthDp = 400,
    heightDp = 800,
)
@Composable
private fun Preview(): Unit =
    AcromineTheme {
        AcronymsSearch(
            viewState = MainViewState.Error(message = "Test error"),
            onAcronymChange = {},
        )
    }

@Composable
private fun AcronymsSearch(
    viewState: MainViewState,
    onAcronymChange: (acronym: String) -> Unit,
): Unit =
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        InputAcronymSection(
            viewState = viewState,
            onAcronymChange = onAcronymChange,
        )
        DefinitionsSection(viewState = viewState)
    }

@Composable
private fun InputAcronymSection(
    viewState: MainViewState,
    onAcronymChange: (acronym: String) -> Unit,
) {
    var name: String by remember { mutableStateOf("") }
    val error: String = if (viewState is MainViewState.Error) viewState.message else ""
    val keyboard: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    val onValueChange: (String) -> Unit = remember {
        { value ->
            val result: String = value.trim()
            if (name != result) {
                onAcronymChange(result)
            }
            name = value
        }
    }
    val keyboardOptions: KeyboardOptions = remember {
        KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        )
    }
    val keyboardActions: KeyboardActions = remember {
        KeyboardActions(onDone = { keyboard?.hide() })
    }
    OutlinedTextField(
        value = name,
        onValueChange = onValueChange,
        isError = error.isNotBlank(),
        singleLine = true,
        trailingIcon = null,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = Modifier
            .padding(
                start = defaultIndentDp,
                top = defaultIndentDp,
                end = defaultIndentDp,
            )
            .fillMaxWidth()
    )
    ErrorHint(error)
}

@Composable
private fun ErrorHint(
    error: String,
): Unit =
    Crossfade(targetState = error) { e ->
        if (e.isNotBlank()) {
            Text(
                text = e,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = defaultIndentDp,
                        end = defaultIndentDp,
                    )
            )
        }
    }

@Composable
private fun DefinitionsSection(
    viewState: MainViewState,
): Unit =
    Box(modifier = Modifier.fillMaxSize()) {
        var items: List<AcronymDefinitionViewState> by remember { mutableStateOf(emptyList()) }
        LazyColumn(
            contentPadding = PaddingValues(top = defaultIndentDp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) { item ->
                DefinitionItemSection(definition = item)
            }
        }
        Crossfade(
            targetState = viewState,
            modifier = Modifier
                .size(72.dp)
                .align(Alignment.TopCenter)
                .padding(defaultIndentDp)
        ) { state ->
            when (state) {
                MainViewState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                is MainViewState.Success -> {
                    items = state.data
                }
                is MainViewState.Error -> {
                    items = emptyList()
                }
            }
        }
    }

@Composable
private fun DefinitionItemSection(
    definition: AcronymDefinitionViewState,
): Unit =
    Card(
        elevation = 16.dp,
        modifier = Modifier
            .padding(
                start = defaultIndentDp,
                end = defaultIndentDp,
                bottom = defaultIndentDp,
            )
            .fillMaxWidth()
            .aspectRatio(5 / 2F)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(all = defaultIndentDp)
        ) {
            Text(
                text = definition.name,
                style = MaterialTheme.typography.h1,
            )
            Text(
                text = stringResource(
                    id = R.string.main_acronym_definition_details_format,
                    definition.frequency,
                    definition.year,
                    definition.variationsCount,
                ),
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(
                        start = defaultIndentDp * 2,
                        top = defaultIndentDp,
                    )
                    .fillMaxWidth()
            )
        }
    }
