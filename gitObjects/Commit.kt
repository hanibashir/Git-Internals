package gitinternals.gitObjects

import gitinternals.extra.Functions.formatStringForPrint

class Commit : ObjectsInterface {

    private val commitDataList: List<String> = ObjectsInterface.dataList

    override val objectData: String
        get() = getCommitContent()

    private fun getCommitContent(): String {

        val objStringMap = commitDataList.associateBy(
            {
                val tempList = it.split(" ")
                if (it.contains("parent")) "parents"
                else tempList.first()
            }, {
                val tempList = it.split(" ")
                if (it.contains("parent")) getListOfParents()
                else tempList.filter { string -> string != tempList.first() }.toMutableList()
            })

        return formatStringForPrint(objStringMap, getCommitMessages())
    }

    private fun getListOfParents(): MutableList<String> {
        val listOfParents = mutableListOf<String>()

        // get commit parents
        commitDataList.forEach {
            if (it.contains("parent")) listOfParents += it.substring(7)
        }

        return listOfParents
    }

    private fun getCommitMessages(): String {
        var commitMsg = ""
        for (i in 0 until commitDataList.lastIndex) {
            if (commitDataList[i] == "") {
                for (x in i until commitDataList.lastIndex) {
                    commitMsg += "${commitDataList[x]}\n"
                }
            }
        }
        return commitMsg
    }
}