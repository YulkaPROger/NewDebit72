package com.example.debit72.android.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.debit72.android.DebitApplication
import java.io.File
import java.io.FileOutputStream


enum class TypeFile {
    PICTURE,
    DOCUMENT
}

class FileUtils(private val typeFile: TypeFile) {
    private var context: Context? = null

    fun load(drawable: Bitmap, name: String): File? {
        val baseDirectory =
            when (typeFile) {
                TypeFile.PICTURE -> context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                TypeFile.DOCUMENT -> context?.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            }
                ?: return null

        val myDir = File(baseDirectory.path)
        if (!myDir.exists()) {
            myDir.mkdirs()
        }
        val file = File(myDir, name)
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        val outputStream = FileOutputStream(file)
        drawable.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
        outputStream.close()

        return file
    }


    fun share(drawable: Bitmap, activity: Activity, name: String = "QR.png"): Boolean {
        val notNullFile = load(drawable, name) ?: return false
        val fileUri = FileProvider
            .getUriForFile(activity, DebitApplication.FILE_PROVIDER, notNullFile)
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "*/*"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        shareIntent.putExtra(Intent.EXTRA_TEXT, "QR")
        activity.startActivity(Intent.createChooser(shareIntent, "QR"))
        return true
    }

    fun share(text: String, name: String, activity: Activity): Boolean {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        activity.startActivity(shareIntent)
        return true
    }

    companion object {
        fun with(context: Context, type: TypeFile = TypeFile.DOCUMENT): FileUtils {
            val fileUtils = FileUtils(type)
            fileUtils.context = context
            return fileUtils
        }
    }

}