package gitinternals.extra

import gitinternals.data.UserInput
import gitinternals.extra.Constants.CAT_FILE
import gitinternals.extra.Constants.LIST_BRANCHES
import java.io.File
import java.io.FileInputStream
import java.util.zip.InflaterInputStream

object FileObject {

    // if it's not a ByteArray is guaranteed to be of type String. (it's safe cast I think :))
    val input: Any
        get() {
            return if (userInput is ByteArray) userInput
            else userInput as String
        }

    // get user input and assign it to [input] variable above
    private val userInput = userInput()

    // invoke UserInput.getUserInput() fun
    private fun userInput(): Any {
        val (filePath, command) = UserInput.getUserInput()
        return inputCommand(filePath, command)
    }

    // input command
    private fun inputCommand(filePath: String, command: String): Any {
        return when (command) {
            CAT_FILE -> catFile(filePath)
            LIST_BRANCHES -> listBranchesCommand(filePath)
            else -> ""
        }
    }

    // cat-file command
    private fun catFile(filePath: String): ByteArray {
        val file = File(filePath)
        return InflaterInputStream(FileInputStream(file)).use { it.readBytes() }
    }

    // list-branches command
    private fun listBranchesCommand(filePath: String): String {

        val tempList = mutableListOf<String>()

        // get the name of current branch
        val headFile = File(filePath.plus("/HEAD"))
        val currentBranch = headFile.readText().substringAfter("refs/heads/")

        val headsDir = File(filePath.plus("/refs/heads"))

        // list of files inside /refs/heads directory in .git directory
        headsDir.listFiles()?.forEach {
            if (it.name == currentBranch.replace("\n", ""))
                tempList.add("* ${it.name}")
            else
                tempList.add("  ${it.name}")
        }

        // sort the list of the files names alphabetically before return it
        return tempList.sorted().joinToString("\n")
    }
}