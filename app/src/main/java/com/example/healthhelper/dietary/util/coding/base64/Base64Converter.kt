package com.example.healthhelper.dietary.util.coding.base64

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

object Base64Converter {
    fun getBase64String(
        path:String
    ): String {
        val bitmap = BitmapFactory.decodeFile(path)

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun decodeBase64AndSetImage(
        completeImageData: String, imageView: ImageView
    ): ImageView{
        val imageDataBytes = completeImageData.substring(completeImageData.indexOf(",") + 1)
        val stream: InputStream =
            ByteArrayInputStream(Base64.decode(imageDataBytes.toByteArray(), Base64.DEFAULT))
        val bitmap = BitmapFactory.decodeStream(stream)
        imageView.setImageBitmap(bitmap)
        return imageView
    }
}