package gitinternals.data

import gitinternals.extra.Constants.CAT_FILE
import gitinternals.extra.Constants.LIST_BRANCHES

object UserInput {

    fun getUserInput(): Pair<String, String> {

        // git dir path
        val gitObjectsPath = println("Enter .git directory location:").run { readln().trim() }

        // git command
        return when (println("Enter command:").run { readln().trim() }) {
            LIST_BRANCHES -> Pair(gitObjectsPath, LIST_BRANCHES)
            CAT_FILE -> {
                val gitObjHash = println("Enter git object hash:").run { readln().trim() }
                Pair(
                    gitObjectsPath
                        .plus("/objects/${gitObjHash.take(2)}/${gitObjHash.drop(2)}"), CAT_FILE
                )

            }
            // return an empty String so the type cast in FileObject will be safe
            else -> Pair("", "")
        }
    }

}