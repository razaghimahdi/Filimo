package com.razzaghi.filimo.business.datesource_test.network

sealed class SearchServiceResponseType {

    object EmptyList : SearchServiceResponseType()

    object GoodData : SearchServiceResponseType()

}
