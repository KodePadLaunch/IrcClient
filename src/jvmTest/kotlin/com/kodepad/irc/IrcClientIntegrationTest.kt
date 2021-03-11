package com.kodepad.irc

import com.kodepad.irc.event.MessageEventListener
import com.kodepad.irc.message.Message
import com.kodepad.irc.vo.User
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.lang.Thread.sleep
import kotlin.test.Test

// try this with following servers freenode, unreal, ircu
class IrcClientIntegrationTest {
    companion object {
        private val logger = LoggerFactory.getLogger(IrcClientIntegrationTest::class.java)
    }

    @Test
    fun `register and send message to channel`() {
        logger.info("starting test")

        val messageEventListener = object : MessageEventListener {
            override fun onEvent(event: Message) {
                logger.debug("message: $event")
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
            messageEventListener = messageEventListener
        )

        runBlocking {
            network.joinChannel("#ircclienttest")
            network.sendMessage("#ircclienttest", "hello, world from IrcClient!")
        }

        sleep(30_000)

        network.close()
    }

    @Test
    fun `register and send message to channel and test fast close`() {
        logger.info("starting test")

        val messageEventListener = object : MessageEventListener {
            override fun onEvent(event: Message) {
                logger.debug("message: $event")
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
            messageEventListener = messageEventListener
        )

        runBlocking {
            network.joinChannel("#ircclienttest")
            network.sendMessage("#ircclienttest", "hello, world from IrcClient!")
        }

        network.close()
    }
}