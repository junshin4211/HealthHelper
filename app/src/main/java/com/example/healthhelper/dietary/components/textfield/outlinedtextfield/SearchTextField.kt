package com.example.healthhelper.dietary.components.textfield.outlinedtextfield

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.dialog.alertdialog.MyAlertDialog
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.extension.stringext.mightBeValidInQueryLanguage
import com.example.healthhelper.dietary.util.querylanguage.QueryLanguageHandler

@Composable
fun SearchTextField(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    label: @Composable ()->Unit,
) {
    var supportingTextColor1 by remember { mutableStateOf(Color.Gray) }
    var supportingTextColor2 by remember { mutableStateOf(Color.Gray) }
    var queryText by remember { mutableStateOf("") }
    var shouldShowMyAlertDialog by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier,
        value = queryText,
        onValueChange = {
            supportingTextColor2 = if(it.isNotEmpty()) Color.Gray else Color.Red

            if(it.mightBeValidInQueryLanguage()){
                queryText = it
                supportingTextColor1 = Color.Gray
            }else{
                supportingTextColor1 = Color.Red
            }
        },
        label = label,
        supportingText = {
            Column {
                Text(
                    text = "The text in text field must obey the syntax of query language.\n i.e. it only can consist of digit, letter, `_` , `:`, and `&` .\n And it must start with letter.",
                    color = supportingTextColor1,
                )

                Text(
                    text = "The text in text field can not be empty.",
                    color = supportingTextColor2
                )
            }
        },
        leadingIcon = {
            IconButton(
                onClick = {
                    if(!QueryLanguageHandler.isValidStatement(queryText)){
                        shouldShowMyAlertDialog = true
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_icon),
                )
            }
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    navController.navigate(DietDiaryScreenEnum.SearchHintFrame.name)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = stringResource(R.string.info_icon),
                )
            }
        }
    )

    if(shouldShowMyAlertDialog){
        MyAlertDialog.popUp(
            onDismissRequest = {shouldShowMyAlertDialog = false},
            onConfirmation =  {shouldShowMyAlertDialog = false},
            dialogTitle = "Error",
            dialogText = "The input text for text field with label `Query` is not valid in query language."
        )
    }
}