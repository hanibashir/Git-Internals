package gitinternals.extra

import gitinternals.extra.Constants.TREE
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object Functions {

    fun formatStringForPrint(objStringMap: Map<String, MutableList<String>>?, commitMsg: String): String {
        val sb = StringBuilder()

        sb.append("tree: ${objStringMap?.get("tree")?.joinToString("")}\n")
        if (objStringMap?.get("parents") != null)
            sb.append("parents: ${objStringMap["parents"]?.joinToString(" | ")}\n")
        sb.append("author: ${formatAuthorString(objStringMap?.get("author"), "author")}\n")
        sb.append("committer: ${formatAuthorString(objStringMap?.get("committer"), "committer")}\n")
        sb.append("commit message:")
        sb.append(commitMsg)

        return sb.toString()
    }

    private fun formatAuthorString(strings: MutableList<String>?, authorType: String): String {
        val sb = StringBuilder()
        sb.append("${strings?.get(0)} ")
        sb.append("${strings?.get(1)} ")
        if (authorType == "author") {
            sb.append("original ")
            sb.append("timestamp: ")
        } else {
            sb.append("commit ")
            sb.append("timestamp: ")
        }

        sb.append("${formatDate(strings?.get(2), strings?.get(3))} ")

        return sb.toString()
            .replace("<", "")
            .replace(">", "")
            .trim()
    }

    private fun formatDate(seconds: String?, zone: String?): String {
        return if (seconds != null) {
            Instant.ofEpochSecond(seconds.toLong()).atZone(ZoneOffset.of(zone))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss xxx"))
        } else ""
    }

    fun Any.toDataList(type: String = ""): List<String> {
        val tempList = mutableListOf<String>()
        if (this is ByteArray) {
            tempList += when (type) {
                TREE -> this.map { Char(it.toUShort()) }
                    .joinToString("")
                    .split(0.toChar())

                else -> this.map { if (it.toInt() == 0) "\n" else it.toInt().toChar() }
                    .joinToString("")
                    .split("\n")
            }
        }
        return tempList
    }

    fun formatTreeContent(modeAndBlobName: String, hash: String): String {
        val listOfModeAndBlobName = modeAndBlobName.split(" ")
        val hashToHex = hash.map { it.code.toByte() }.joinToString("") { "%02x".format(it) }
        return "${listOfModeAndBlobName.first()} $hashToHex ${listOfModeAndBlobName.last()}"
    }

}