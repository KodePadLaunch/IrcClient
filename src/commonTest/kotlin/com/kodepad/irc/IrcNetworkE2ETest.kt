package com.kodepad.irc

import com.kodepad.irc.command.JoinCommand
import com.kodepad.irc.command.NickCommand
import com.kodepad.irc.command.PrivMsgCommand
import com.kodepad.irc.command.UserCommand
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.event.NoticeEvent
import com.kodepad.irc.event.PrivMsgEvent
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.logging.Markers.TEST_FLOW
import com.kodepad.kotlinx.coroutines.runBlockingTest
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Ignore
import kotlin.test.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.job

// todo: These tests are flaky, and not scalable to multiple developers
// try this with following servers freenode, unreal, ircu
class IrcNetworkE2ETest {
    companion object {
        private val logger = LoggerFactory.getLogger(IrcNetworkE2ETest::class)
    }

    @Ignore(/* timeout = INTEGRATION_TEST_TIMEOUT_IN_MILIS */)
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

        val noticeEventListener1 = object : EventListener<NoticeEvent> {
            override fun onEvent(event: NoticeEvent) {
                logger.debug(TEST_FLOW, "notice: ${event.text}")
            }
        }

        val privMsgEventListener1 = object : EventListener<PrivMsgEvent> {
            override fun onEvent(event: PrivMsgEvent) {
                logger.debug(TEST_FLOW, "privmsg: ${event.text}")
                if(testMessageString == event.text) {
                    testFlag = true
                    coroutineScope.cancel()
                }
            }
        }

        val network1 = IrcNetwork(
            hostname = "chat.freenode.net",
            port = "6665".toInt(),
        )

        network1.registerEventListener(NoticeEvent::class, noticeEventListener1)
        network1.registerEventListener(PrivMsgEvent::class, privMsgEventListener1)
        network1.registerEventListener(Message::class, rawMessageEventListener1)

        val network2 = IrcNetwork(
            hostname = "chat.freenode.net",
            port = "6665".toInt(),
        )

        val nickCommand1 = NickCommand(
            "testnickname1"
        )
        val userCommand1 = UserCommand(
            "testusername1",
            "IRC Client Test Host"
        )

        val nickCommand2 = NickCommand(
            "testnickname2",
        )
        val userCommand2 = UserCommand(
            "testusername2",
            "IRC Client Test Host"
        )

        runBlockingTest {

            network1.connectAndRegister(nickCommand1, userCommand1)
            network2.connectAndRegister(nickCommand2, userCommand2)

            network1.joinChannel(
                JoinCommand(
                    listOf("#ircclienttest")
                )
            )
            network2.joinChannel(
                JoinCommand(
                    listOf("#ircclienttest")
                )
            )
            network2.sendPrivMsg(
                PrivMsgCommand(
                    listOf("#ircclienttest"),
                    testMessageString
                )
            )

            coroutineScope.coroutineContext.job.join()

            network2.close()
            network1.close()

        }


        assertTrue(testFlag)
    }

    @Ignore
    fun `register and send message to channel and test fast close`() {
        logger.info("starting test")

        val rawMessageEventListener = object : EventListener<Message> {
            override fun onEvent(event: Message) {
                logger.debug("rawMessage: $event")
            }
        }

        val noticeEventListener = object : EventListener<NoticeEvent> {
            override fun onEvent(event: NoticeEvent) {
                logger.debug("notice: ${event.text}")
            }
        }

        val privMsgEventListener = object : EventListener<PrivMsgEvent> {
            override fun onEvent(event: PrivMsgEvent) {
                logger.debug("privmsg: ${event.text}")
            }

        }

        val network = IrcNetwork(
            hostname = "chat.freenode.net",
            port = "6665".toInt(),
        )
        network.registerEventListener(NoticeEvent::class, noticeEventListener)
        network.registerEventListener(PrivMsgEvent::class, privMsgEventListener)
        network.registerEventListener(Message::class, rawMessageEventListener)

        val nickCommand = NickCommand(
            nickname = "dummykodepadnick",
        )
        val userCommand = UserCommand(
            "ircclienttestuser",
            "IRC Client Test Host"
        )

        runBlockingTest {
            network.connectAndRegister(nickCommand, userCommand)

            network.joinChannel(
                JoinCommand(
                    listOf("#ircclienttest")
                )
            )
            network.sendPrivMsg(
                PrivMsgCommand(
                    listOf("#ircclienttest"),
                "hello, world from IrcClient!"
                )
            )

            network.close()
        }
    }
}