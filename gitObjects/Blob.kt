package gitinternals.gitObjects

class Blob : ObjectsInterface {

    private val blobDataList = ObjectsInterface.dataList

    override val objectData: String = blobDataList
        .filter { it != blobDataList.first() } // Exclude the first item which is the blob type name and size
        .joinToString("\n")
}