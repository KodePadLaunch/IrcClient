package com.kodepad.irc.connection

import com.kodepad.irc.exception.EndOfStreamException
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.net.Socket
import java.nio.charset.Charset
import kotlin.text.StringBuilder

class Connection(private val javaSocket: Socket) {
    constructor(hostname: String, port: Int): this(Socket(hostname, port))

    companion object {
        const val MSG_SEPARATOR = "\r\n"
    }

    private val inputStream = javaSocket.getInputStream()
    private val outputStream = javaSocket.getOutputStream()

    // todo: This doesn't support unicode code points with more than 4 hex digits, change implementation to support that
    private val inputStreamReader = inputStream.reader(Charset.forName("UTF-8"))
    private val outputStreamWriter = outputStream.writer(Charset.forName("UTF-8"))

    private val codePointBuffer = ArrayList<Int>(512)

    private fun isEndOfLine(): Boolean {
        val size = codePointBuffer.size
        return if ((size >= 2)
            && (codePointBuffer[size-2] == '\r'.toInt())
            && (codePointBuffer[size-1] == '\n'.toInt())
        ) {
            codePointBuffer.removeLast()
            codePointBuffer.removeLast()
            true
        } else {
            false
        }
    }

    private fun getMessageString(): String {
        val message = StringBuilder(codePointBuffer.size)
        codePointBuffer.map { codePoint -> message.appendCodePoint(codePoint) }
        return message.toString()
    }

    val message = flow<String> {
        while (true) {
            val codePoint = inputStreamReader.read()
            if(codePoint != -1) {
                codePointBuffer.add(codePoint)
                if(isEndOfLine()) {
                    emit(getMessageString())
                }
            }
            else {
                throw EndOfStreamException("End of the stream has been reached")
            }
        }
    }

    // todo: Improve exception handling
    fun sendMessage(msg: String): Boolean {
        try {
//            javaSocketOutput.print("${msg}${MSG_SEPARATOR}")
//            javaSocketOutput.flush()
        } catch (exception: Exception) {
            return false
        }

        return true
    }
}
