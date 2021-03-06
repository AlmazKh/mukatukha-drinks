package com.almaz.mukatukha_drinks.core.interfaces

import com.almaz.mukatukha_drinks.core.model.Basket
import com.almaz.mukatukha_drinks.core.model.BasketOrderInfo
import com.almaz.mukatukha_drinks.core.model.Order
import com.almaz.mukatukha_drinks.core.model.User
import com.almaz.mukatukha_drinks.utils.BasketState
import io.reactivex.Single

interface BasketRepository {
    fun getBasketProductList(): Single<BasketOrderInfo>
    fun makeOrder(
        phoneNumber: String,
        paymentMethod: String,
        promocode: String?,
        basket: List<Basket>,
        owner: User
    ): Single<Order>
    fun checkHasActiveOrder(user: User) :Single<Boolean>
    fun checkHasProductsInBasket() : Single<Boolean>
}
