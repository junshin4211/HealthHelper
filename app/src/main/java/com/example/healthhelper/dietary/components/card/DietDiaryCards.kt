package com.example.healthhelper.dietary.components.card

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R

import com.example.healthhelper.dietary.classhandler.ClassInspector
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel

const val TAG = "DietDiaryCards"

@Composable
fun <T : Any> DietDiaryCards(
    context: Context,
    cards: List<T>,
    modifier: Modifier = Modifier,
){
    var deleteSuccessfully by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    cards.forEachIndexed { index, card: T ->
        val cls = card.javaClass
        val classInspector = ClassInspector(cls)
        val fieldsName = classInspector.getFieldsName()
        val fieldsValue = classInspector.getFieldsValue(card)
        val zipped = fieldsName.zip(fieldsValue).toMap()
        Card(
            modifier = modifier,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.account_icon),
                    modifier = Modifier.size(40.dp),
                )
                Column {
                    zipped.forEach {
                        Text("${it.key}:${it.value}")
                    }
                }
                IconButton(
                    onClick = {
                        DiaryViewModel.diaries.removeAt(index)
                        toastMessage = if(deleteSuccessfully)
                            "Item deleted successfully."
                        else
                            "Item deleted failed."
                        Toast.makeText(context,toastMessage,Toast.LENGTH_LONG).show()
                    }
                ) {
                    Image(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(R.string.delete_an_item_icon),
                        modifier = Modifier.size(40.dp),
                    )
                }
            }
        }
    }
}