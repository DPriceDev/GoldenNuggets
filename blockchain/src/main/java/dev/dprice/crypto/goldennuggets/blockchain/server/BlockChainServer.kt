package dev.dprice.crypto.goldennuggets.blockchain.server

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.cio.*
import javax.inject.Inject
import io.ktor.server.engine.*
import org.slf4j.event.Level

interface BlockChainServer {
    fun startServer(port: Int = 8080)
    fun stopServer()
}

class BlockChainServerImpl @Inject constructor(

) : BlockChainServer {
    private var server: ApplicationEngine? = null

    override fun startServer(port: Int) {
        server?.let { stopServer() }

        server = embeddedServer(CIO, port = port) {
            install(CallLogging) {
                level = Level.INFO
            }
            install(Routing)
            install(ContentNegotiation) {
                json()
            }

            routing {
                // todo: /nodes/add -> Add new node to node list

                // todo: /nodes -> return current list of nodes

                // todo: /chain -> return blockchain

                // todo: chain/check -> checks if recieved block chain is valid against ours

                // todo: transaction/add -> Add a transcation from another user

                get("/ping") {
                    this.call.respond(mapOf("hello" to "world"))
                }
            }
        }.start(wait = false)
    }

    override fun stopServer() {
        server?.stop(0, 0)
        server = null
    }
}