package com.razzaghi.filimo


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.razzaghi.filimo.business.datesource_test.network.data.SearchDataValid
import com.razzaghi.filimo.business.datesource_test.network.serializeSearchData
import com.razzaghi.filimo.presentation.ui.search.SearchScreen
import com.razzaghi.filimo.presentation.ui.search.view_model.SearchState
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Rule
import org.junit.Test

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class HeroListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val search = serializeSearchData(SearchDataValid.data)

    @Test
    fun joker_result_is_shown() {
        composeTestRule.setContent {
            MaterialTheme() {
                val state = remember{
                    SearchState(
                        search = search,
                        query = "Joker"
                    )
                }
                SearchScreen (
                    state = state,
                    events = {},
                    errors = emptyFlow()
                )
            }
        }
        composeTestRule.onNodeWithText("جوکر").assertIsDisplayed()
    }

}