package com.razzaghi.filimo.presentation.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.razzaghi.filimo.business.core.UIComponent
import com.razzaghi.filimo.business.domain.search.SearchData
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        modifier = Modifier
                            .fillMaxWidth(.2f), onClick = {
                            events(SearchEvent.Search)
                        }) {
                        Icon(
                            Icons.Default.Search,
                            null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

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
                    )

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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(.3f)
                    .height(150.dp)
            ) {
                AsyncImage(
                    item.cover,
                    null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Column(
                modifier = Modifier
                    .weight(.7f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    item.movieTitle,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    item.director,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    item.descr,
                    maxLines = 3,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
    HorizontalDivider()
}
