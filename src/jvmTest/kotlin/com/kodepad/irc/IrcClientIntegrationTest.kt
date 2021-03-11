package com.kodepad.irc

import com.kodepad.irc.event.EventListener
import com.kodepad.irc.logging.Markers.TEST_FLOW
import com.kodepad.irc.message.Message
import com.kodepad.irc.message.client.sending.Notice
import com.kodepad.irc.message.client.sending.PrivMsg
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

        val rawMessageEventListener = object : EventListener<Message> {
            override fun onEvent(event: Message) {
                logger.debug(TEST_FLOW, "rawMessage: $event")
            }
        }

        val noticeEventListener = object : EventListener<Notice> {
            override fun onEvent(event: Notice) {
                logger.debug(TEST_FLOW, "notice: ${event.text}")
            }
        }

        val privMsgEventListener = object : EventListener<PrivMsg> {
            override fun onEvent(event: PrivMsg) {
                logger.debug(TEST_FLOW, "privmsg: ${event.text}")
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
            noticeEventListener = noticeEventListener,
            privMsgEventListener = privMsgEventListener,
            rawMessageEventListener = rawMessageEventListener
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

        val rawMessageEventListener = object : EventListener<Message> {
            override fun onEvent(event: Message) {
                logger.debug("rawMessage: $event")
            }
        }

        val noticeEventListener = object : EventListener<Notice> {
            override fun onEvent(event: Notice) {
                logger.debug("notice: ${event.text}")
            }
        }

        val privMsgEventListener = object : EventListener<PrivMsg> {
            override fun onEvent(event: PrivMsg) {
                logger.debug("privmsg: ${event.text}")
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
            noticeEventListener = noticeEventListener,
            privMsgEventListener = privMsgEventListener,
            rawMessageEventListener = rawMessageEventListener
        )

        runBlocking {
            network.joinChannel("#ircclienttest")
            network.sendMessage("#ircclienttest", "hello, world from IrcClient!")
        }

        network.close()
    }
}