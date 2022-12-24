package gitinternals.gitObjects

import gitinternals.extra.FileObject
import gitinternals.extra.Functions.toDataList

interface ObjectsInterface {

    val objectData: String

    companion object {

        // convert ByteArray to list of String by the extension fun toDataList()
        val dataList: List<String> = FileObject.input.toDataList()

        // get the object type which will be the first element in the dataList and exclude the object size
        val objType = dataList[0].filter { it.digitToIntOrNull() == null }.trim().uppercase()
    }
}