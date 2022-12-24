package gitinternals.gitObjects

import gitinternals.extra.Functions.formatTreeContent

class Tree : ObjectsInterface {

    private val treeDataList: List<String> = ObjectsInterface.dataList

    override val objectData: String
        get() = treeContent()

    private fun treeContent(): String {

        val tempTreeContentList = mutableListOf<String>()

        treeDataList.forEachIndexed loop@ { index, text ->
            when {
                index < 2 -> return@loop // object type and size e.g. tree 111
                index == 2 -> tempTreeContentList.add(formatTreeContent(treeDataList[index - 1], text.take(20)))
                else -> tempTreeContentList.add(formatTreeContent(treeDataList[index - 1].drop(20), text.take(20)))
            }
        }
        return tempTreeContentList.joinToString("\n")
    }
}