package com.banana.purecity.api

import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi

class TdTest {
    private val handler = Client.ResultHandler { response ->
        when (response.constructor) {
            TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR -> {
                println("TDLib loaded and wait")
            }

            TdApi.Error.CONSTRUCTOR -> {
                val error = response as TdApi.Error
                println("TDLib: ${error.code} ${error.message}")
            }

            else -> {
                println("Response: $response")
            }
        }
    }

    fun test() {
        try {
            System.loadLibrary("tdjni")
            println("✅ libtdjni.so loaded")

            val client = Client.create(handler, null, null)
            client.send(TdApi.GetAuthorizationState(), handler)
        } catch (e: UnsatisfiedLinkError) {
            println("❌ Native lib loading fault: ${e.message}")
        } catch (e: Exception) {
            println("❌ TDLib: ${e.message}")
        }
    }
}