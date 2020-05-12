package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.BasketRepository
import com.almaz.mukatukha_drinks.core.model.*
import com.almaz.mukatukha_drinks.core.model.db.BasketAndProduct
import com.almaz.mukatukha_drinks.core.model.remote.OrderRemote
import com.almaz.mukatukha_drinks.data.MukatukhaAPI
import com.almaz.mukatukha_drinks.data.db.BasketDao
import io.reactivex.Single
import javax.inject.Inject

class BasketRepositoryImpl
@Inject constructor(
    private val basketDao: BasketDao,
    private val api: MukatukhaAPI
) : BasketRepository {

    override fun getBasketProductList(): Single<List<Basket>> {
        return basketDao.getItemsFromBasket().map {
            mapBasketAndProductToLocalBasket(it)
        }
    }

    override fun makeOrder(
        phoneNumber: String,
        paymentMethod: String,
        promocode: String?,
        basket: List<Basket>
    ): Single<Order> {
        return Single.fromObservable(
            api.makeOrder(
                OrderRequest(
                    phoneNumber = phoneNumber,
                    paymentMethod = paymentMethod,
                    products = mapBasketToProductAmountMap(basket),
                    totalCost = countTotalCost(basket),
                    ownerId = basket.first().ownerId,
                    promocode = promocode
                )
            )
        )
            .map {
                mapResponseOrderToLocal(it)
            }

    }

    override fun checkHasActiveOrder(user: User): Single<Boolean> {
        return api.checkHasActiveOrder(user)
    }

    override fun checkHasProductsInBasket(): Single<Boolean> {
        return basketDao.getItemsFromBasket()
            .map {
                it.isNotEmpty()
            }
    }

    private fun mapResponseOrderToLocal(order: OrderRemote): Order =
        Order(
            order.id,
            order.secretCode,
            order.time,
            order.cafe,
            order.products,
            order.totalCost
        )

    private fun mapBasketToProductAmountMap(basket: List<Basket>): Map<Product, Int> {
        val map = mutableMapOf<Product, Int>()
        for (item in basket) {
            map[item.product] = item.amount
        }
        return map
    }

    private fun countTotalCost(basket: List<Basket>): Double {
        var totalCost = 0.0
        for (item in basket) {
            totalCost += item.product.price * item.amount
        }
        return totalCost
    }

    private fun mapBasketAndProductToLocalBasket(basketAndProduct: List<BasketAndProduct>): List<Basket> {
        val list = mutableListOf<Basket>()
        for (item in basketAndProduct) {
            list.add(
                Basket(
                    item.basket.id,
                    item.basket.amount,
                    item.basket.ownerId,
                    Product(
                        item.product.id.toString(),
                        item.product.name,
                        item.product.price,
                        item.product.volume,
                        null,
                        null,
                        item.product.otherDetails
                    )
                )
            )
        }
        return list
    }
}
