package com.example.healthhelper.planpage.domain.model // 您的包名

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.healthhelper.R

// 圖表數據模型
data class ChartData(val value: Float, val color: Color)

// 圖表旁的圖例數據模型
data class MacroInfo(val name: String, val grams: Float, val color: Color)

// 假設您已經有這些枚舉
enum class DietPlanType(@StringRes val displayNameRes: Int) {
    HIGH_PROTEIN(R.string.highProtein),
    LOW_CARB_HYDRATE(R.string.lowCarbHydrate),
    KETONE(R.string.ketone),
    MEDITERRA(R.string.mediterra),
    CUSTOM(R.string.custom);

    companion object {
        fun fromResId(displayNameResId: Int): DietPlanType? {
            return entries.find { it.displayNameRes == displayNameResId }
        }
    }
}

enum class NutritionType(@StringRes val displayNameRes: Int) { // 為宏指令也添加顯示名稱資源
    CARBOHYDRATE(R.string.carb), // 需要在 strings.xml 中定義
    PROTEIN(R.string.protein),
    FAT(R.string.fat)
}

object CategoryID {
    private val cateMap = mapOf(
        R.string.highProtein to 1,
        R.string.lowCarbHydrate to 2,
        R.string.ketone to 3,
        R.string.mediterra to 4,
        R.string.custom to 5,
    )

    fun getCateId(cateName: Int):Int?{
        return cateMap[cateName]
    }
}

enum class DateRangeTitle(@StringRes val title: Int) {
    AWeek(title = R.string.AWeek),
    HalfMonth(title = R.string.halfMonth),
    AMonth(title = R.string.AMonth),
    ThreeMonth(title = R.string.threeMonth),
    SixMonth(title = R.string.sixMonth);
}

data class NutritionDetail(
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int
) {
    /**
     * 在Composable中獲取標題字符串。
     */
    @Composable
    fun getTitle(): String {
        return LocalContext.current.getString(titleResId)
    }

    /**
     * 在Composable中獲取描述字符串，並按換行符分割成列表。
     * 清理空白並過濾空行。
     */
    @Composable
    fun getDescriptionPoints(): List<String> {
        val fullDescription = LocalContext.current.getString(descriptionResId)
        return fullDescription
            .split('\n')
            .map { it.trim() }
            .filter { it.isNotBlank() }
    }
}

/**
 * 註冊和提供所有飲食計劃及其營養素詳細信息的對象。
 */
object DietPlanRegistry {
    private val planDetails: Map<DietPlanType, Map<NutritionType, NutritionDetail>> = mapOf(
        DietPlanType.KETONE to mapOf(
            NutritionType.CARBOHYDRATE to NutritionDetail(
                titleResId = R.string.ketone_carb_title,
                descriptionResId = R.string.ketone_carb_description
            ),
            NutritionType.PROTEIN to NutritionDetail(
                titleResId = R.string.ketone_pro_title,
                descriptionResId = R.string.ketone_pro_description
            ),
            NutritionType.FAT to NutritionDetail(
                titleResId = R.string.ketone_fat_title,
                descriptionResId = R.string.ketone_fat_description
            )
        ),
        DietPlanType.HIGH_PROTEIN to mapOf(
            NutritionType.CARBOHYDRATE to NutritionDetail(
                titleResId = R.string.highPro_carb_title,
                descriptionResId = R.string.highPro_carb_description
            ),
            NutritionType.PROTEIN to NutritionDetail(
                titleResId = R.string.highPro_pro_title,
                descriptionResId = R.string.highPro_pro_description
            ),
            NutritionType.FAT to NutritionDetail(
                titleResId = R.string.highPro_fat_title,
                descriptionResId = R.string.highPro_fat_description
            )
        ),
        DietPlanType.LOW_CARB_HYDRATE to mapOf(
            NutritionType.CARBOHYDRATE to NutritionDetail(
                titleResId = R.string.lowCarbHydrate_carb_title,
                descriptionResId = R.string.lowCarbHydrate_carb_description
            ),
            NutritionType.PROTEIN to NutritionDetail(
                titleResId = R.string.lowCarbHydrate_pro_title,
                descriptionResId = R.string.lowCarbHydrate_pro_description
            ),
            NutritionType.FAT to NutritionDetail(
                titleResId = R.string.lowCarbHydrate_fat_title,
                descriptionResId = R.string.lowCarbHydrate_fat_description
            )
        ),
        DietPlanType.MEDITERRA to mapOf(
            NutritionType.CARBOHYDRATE to NutritionDetail(
                titleResId = R.string.mediterra_carb_title,
                descriptionResId = R.string.mediterra_carb_description
            ),
            NutritionType.PROTEIN to NutritionDetail(
                titleResId = R.string.mediterra_pro_title,
                descriptionResId = R.string.mediterra_pro_description
            ),
            NutritionType.FAT to NutritionDetail(
                titleResId = R.string.meditera_fat_title,
                descriptionResId = R.string.mediterra_fat_description
            )
        )
    )

    /**
     * 根據飲食計劃類型和宏指令類型獲取 NutritionDetail。
     */
    fun getNutritionDetail(planType: DietPlanType, macroType: NutritionType): NutritionDetail? {
        return planDetails[planType]?.get(macroType)
    }

    /**
     * 獲取特定飲食計劃下的所有宏指令及其詳細信息。
     */
    fun getDetailsForPlan(planType: DietPlanType): Map<NutritionType, NutritionDetail>? {
        return planDetails[planType]
    }
}


