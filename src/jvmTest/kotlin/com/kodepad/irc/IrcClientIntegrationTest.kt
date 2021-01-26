package com.kodepad.irc

import com.kodepad.irc.dto.Message
import com.kodepad.irc.network.NetworkEventListener
import com.kodepad.irc.vo.User
import kotlinx.coroutines.CoroutineScope
import org.slf4j.LoggerFactory
import java.lang.Thread.sleep
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test

// try this with following servers freenode, unreal, ircu
class IrcClientIntegrationTest {
    companion object {
        private val logger = LoggerFactory.getLogger(IrcClientIntegrationTest::class.java)
    }

    @Test
    fun `register and send message to channel`() {
        logger.info("starting test")

        val networkEventListener = object : NetworkEventListener {
            override fun onMessage(message: Message) {
                logger.info("message: {}", message)
            }
        }

        val ircClient: Client = IrcClient()

        val network = ircClient.joinNetwork(
            hostname = "chat.freenode.net",
            port = Integer.parseInt("6665"),
            user = User(
                nickname = "dummykodepadnick",
                username = "ircclienttestuser",
                realname = "IRC Client Test Host"
            ),
            networkEventListener
        )

        val channel = network.joinChannel("#ircclienttest")
        channel.sendMessage("hello, world from IrcClient!")

        sleep(30_000)

        channel.close()
        network.close()
    }
}