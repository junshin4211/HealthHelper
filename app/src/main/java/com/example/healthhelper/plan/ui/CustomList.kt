package com.example.healthhelper.plan.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.model.PlanProperty

class CustomList {
//    @Composable
//    fun BookLists(
//        plans: List<PlanProperty>,
//        innerPadding: PaddingValues,
//        onItemClick: (PlanProperty) -> Unit
//    ) {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//        ) {
//            items(plans) { book ->
//                // 用來建立Lists內容物
//                ListItem(
//                    modifier = Modifier.clickable {
//                        onItemClick(book)
//                    },
//                    overlineContent = { Text(text = book.id) },
//                    headlineContent = { Text(book.name) },
//                    supportingContent = { Text(book.price.toString()) },
//                    leadingContent = {
//                        Image(
//                            painter = painterResource(id = book.image),
//                            contentDescription = "book",
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    },
//                    trailingContent = {
//                        Icon(
//                            imageVector = Icons.Filled.Favorite,
//                            contentDescription = "shoppingCart",
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }
//                )
//                HorizontalDivider()
//            }
//        }
//    }
//
//    /**
//     * 載入測試需要資料
//     * @return 多本書資訊
//     */
//    fun fetchBooks(): List<PlanProperty> {
//        return listOf(
//            PlanProperty()
//        )
//    }
}