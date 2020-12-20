package com.kodepad.irc.command

import com.kodepad.irc.command.clientMessages.channelOperations.ChannelOperations
import com.kodepad.irc.command.clientMessages.connectionMessages.ConnectionMessages
import com.kodepad.irc.command.clientMessages.miscellaneousMessages.MiscellaneousMessages
import com.kodepad.irc.command.clientMessages.optionalMessages.OptionalMessages
import com.kodepad.irc.command.clientMessages.sendingMessages.SendingMessages
import com.kodepad.irc.command.clientMessages.serverQueriesAndCommands.ServerQueriesAndCommands
import com.kodepad.irc.command.numerics.Numerics

object Constants {
    private val channelOperationsList = ChannelOperations.values().map { it.name }
    private val connectionMessagesList = ConnectionMessages.values().map { it.name }
    private val miscellaneousMessagesList = MiscellaneousMessages.values().map { it.name }
    private val optionalMessagesList = OptionalMessages.values().map { it.name }
    private val sendingMessagesList = SendingMessages.values().map { it.name }
    private val serverQueriesAndCommandsList = ServerQueriesAndCommands.values().map { it.name }

    private val numericsList = Numerics.values().map { it.name }

    val commandSet = hashSetOf<String>(
        *channelOperationsList.toTypedArray(),
        *connectionMessagesList.toTypedArray(),
        *miscellaneousMessagesList.toTypedArray(),
        *optionalMessagesList.toTypedArray(),
        *sendingMessagesList.toTypedArray(),
        *serverQueriesAndCommandsList.toTypedArray(),
        *numericsList.toTypedArray()
    )
}