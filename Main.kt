package gitinternals

import gitinternals.extra.FileObject
import gitinternals.gitObjects.ObjectsInterface
import gitinternals.extra.Constants.BLOB
import gitinternals.extra.Constants.COMMIT
import gitinternals.extra.Constants.TREE
import gitinternals.gitObjects.Blob
import gitinternals.gitObjects.Commit
import gitinternals.gitObjects.Tree

fun main() {

    when(val input = FileObject.input) {
        // safe cast to ByteArray
        is ByteArray -> {
            // print git object type name: blob, commit or tree.
            // formatted to: *BLOB*, *COMMIT* or *TREE*.
            println("*${ObjectsInterface.objType}*")
            // retrieve git object content
            when (ObjectsInterface.objType) {
                BLOB -> print(Blob().objectData)
                COMMIT -> print(Commit().objectData)
                TREE -> print(Tree().objectData)
            }
        }
        // safe cast to String
        is String -> println(input)
    }
}