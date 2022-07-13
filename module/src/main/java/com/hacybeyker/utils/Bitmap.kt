package com.hacybeyker.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import java.io.File
import java.io.FileOutputStream

object Bitmap {

    private const val FOLDER_MAIN = "files"
    private const val TYPE = "image/*"
    private const val QUALITY = 100

    fun convertImageViewToDrawable(view: ImageView): Drawable {
        return view.drawable
    }

    fun convertDrawableToBitmap(drawable: Drawable): Bitmap {
        return (drawable as BitmapDrawable).bitmap
    }

    fun convertViewToBitmap(view: View): Bitmap {
        // return Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        return view.drawToBitmap()
    }

    fun convertBitmapToFile(context: Context, bitmap: Bitmap): Uri {
        val folder = File(context.externalCacheDir, FOLDER_MAIN)
        if (!folder.exists() && !folder.mkdir()) {
            folder.mkdirs()
        }
        val file = File(folder, "${Time.generateTimeMillis()}.${PictureExtensions.JPG.extension}")
        file.createNewFile()
        val bytes = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY, bytes)
        bytes.flush()
        bytes.close()
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }
}
