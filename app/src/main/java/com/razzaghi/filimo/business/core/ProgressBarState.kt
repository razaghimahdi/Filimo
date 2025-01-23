package com.razzaghi.filimo.business.core

sealed class ProgressBarState{

   data object Loading: ProgressBarState()

   data object Idle: ProgressBarState()

}

