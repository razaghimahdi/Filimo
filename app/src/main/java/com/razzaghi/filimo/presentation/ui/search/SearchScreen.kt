package com.razzaghi.filimo.presentation.ui.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.razzaghi.filimo.R
import com.razzaghi.filimo.business.core.UIComponent
import com.razzaghi.filimo.business.domain.search.SearchData
import com.razzaghi.filimo.presentation.components.DefaultButton
import com.razzaghi.filimo.presentation.components.DefaultScreenUI
import com.razzaghi.filimo.presentation.ui.search.view_model.SearchEvent
import com.razzaghi.filimo.presentation.ui.search.view_model.SearchState
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchScreen(
    state: SearchState,
    errors: Flow<UIComponent>,
    events: (SearchEvent) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current


    DefaultScreenUI(
        errors = errors,
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(SearchEvent.OnRetryNetwork) },
    ) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = state.query,
                        onValueChange = {
                            events(SearchEvent.OnUpdateQuery(it))
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search,
                            keyboardType = KeyboardType.Text,
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                events(SearchEvent.Search)
                                keyboardController?.hide()
                            },
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(.8f)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    DefaultButton(
                        modifier = Modifier.weight(.2f),
                        progressBarState = state.progressBarState,
                        text = stringResource(
                            R.string.search
                        )
                    ) {
                        events(SearchEvent.Search)
                    }
                }
            }

            items(state.search.searchData) {
                SearchBox(item = it)
            }
        }


    }
}

@Composable
private fun SearchBox(item: SearchData) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp)){
        Text(item.movieTitle)
    }
}
