package com.kodepad.irc

import com.kodepad.irc.TestConstants.INTEGRATION_TEST_TIMEOUT_IN_MILIS
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.logging.Markers.TEST_FLOW
import com.kodepad.irc.message.Message
import com.kodepad.irc.message.client.sending.Notice
import com.kodepad.irc.message.client.sending.PrivMsg
import com.kodepad.irc.vo.User
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.kotlinx.coroutines.runBlockingTest
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.job

// try this with following servers freenode, unreal, ircu
class IrcClientIntegrationTest {
    companion object {
        private val logger = LoggerFactory.getLogger(IrcClientIntegrationTest::class)
    }

    @Test(/* timeout = INTEGRATION_TEST_TIMEOUT_IN_MILIS */)
    fun `freenode join channel and send message test`() {
        logger.info("starting test")
        val coroutineScope = CoroutineScope(EmptyCoroutineContext)

        val testMessageString = "hello, world from IrcClient!"

        var testFlag = false
        val rawMessageEventListener1 = object : EventListener<Message> {
            override fun onEvent(event: Message) {
                logger.debug(TEST_FLOW, "rawMessage: $event")
            }
        }

        val noticeEventListener1 = object : EventListener<Notice> {
            override fun onEvent(event: Notice) {
                logger.debug(TEST_FLOW, "notice: ${event.text}")
            }
        }

        val privMsgEventListener1 = object : EventListener<PrivMsg> {
            override fun onEvent(event: PrivMsg) {
                logger.debug(TEST_FLOW, "privmsg: ${event.text}")
                if(testMessageString == event.text) {
                    testFlag = true
                    coroutineScope.cancel()
                }
            }
        }

        val ircClient: Client = IrcClient()

        val network1 = ircClient.joinNetwork(
            hostname = "chat.freenode.net",
            port = "6665".toInt(),
            user = User(
                nickname = "testnickname1",
                username = "testusername1",
                realname = "IRC Client Test Host"
            ),
            noticeEventListener = noticeEventListener1,
            privMsgEventListener = privMsgEventListener1,
            rawMessageEventListener = rawMessageEventListener1
        )

        val network2 = ircClient.joinNetwork(
            hostname = "chat.freenode.net",
            port = "6665".toInt(),
            user = User(
                nickname = "testnickname2",
                username = "testusername2",
                realname = "IRC Client Test Host"
            ),
        )

        runBlockingTest {
            network1.connectAndRegister()
            network2.connectAndRegister()

            network1.joinChannel("#ircclienttest")
            network2.joinChannel("#ircclienttest")
            network2.sendMessage("#ircclienttest", testMessageString)

            coroutineScope.coroutineContext.job.join()

            network2.close()
            network1.close()

        }


        assertTrue(testFlag)
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
            port = "6665".toInt(),
            user = User(
                nickname = "dummykodepadnick",
                username = "ircclienttestuser",
                realname = "IRC Client Test Host"
            ),
            noticeEventListener = noticeEventListener,
            privMsgEventListener = privMsgEventListener,
            rawMessageEventListener = rawMessageEventListener
        )

        runBlockingTest {
            network.connectAndRegister()

            network.joinChannel("#ircclienttest")
            network.sendMessage("#ircclienttest", "hello, world from IrcClient!")

            network.close()
        }
    }
}