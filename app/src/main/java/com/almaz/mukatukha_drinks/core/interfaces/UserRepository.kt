package com.almaz.mukatukha_drinks.core.interfaces

import com.almaz.mukatukha_drinks.core.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface UserRepository {
    fun checkAuthUser(): Single<Boolean>
    fun loginWithGoogle(acct: GoogleSignInAccount): Completable
    fun loginWithPhone(
        storedVerificationId: String,
        verificationCode: String,
        userName: String,
        phone: String
    ): Completable

    fun sendVerificationCode(phoneNumber: String): Maybe<String>
    fun logout(): Completable
    fun getCurrentUser(): Single<User>
    fun getUserById(userId: Long): Single<User>
}