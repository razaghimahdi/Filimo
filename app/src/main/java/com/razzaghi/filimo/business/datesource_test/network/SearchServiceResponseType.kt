package com.razzaghi.filimo.business.datesource_test.network

sealed class SearchServiceResponseType {

    object EmptyList : SearchServiceResponseType()

    object Error406 : SearchServiceResponseType()

    object GoodData : SearchServiceResponseType()

}
