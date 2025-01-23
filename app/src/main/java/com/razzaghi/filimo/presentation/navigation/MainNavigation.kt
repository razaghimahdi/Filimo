package com.razzaghi.filimo.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainNavigation {


    @Serializable
    data object Search : MainNavigation

}