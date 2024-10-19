# android compose material3
## color 
### color about text field
#### `OutlinedTextFieldDefaults.colors`
##### intro
> [!WARNING]
> Since I found `TextFieldDefaults.outlinedTextFieldcolors` method is depreciated in newer version of androidx.compose.material3 dependency. 
> 
> I use `OutlinedTextFieldDefaults.colors` method instead.

I found `TextFieldDefaults.outlinedTextFieldcolors` method at two `TextField` components in `LoginScreen.kt` file.

Due to depreciated method and it will throw compiler error before I modified(tested in my notebook with newer version of dependency),

I decide and finish to replace `TextFieldDefaults.outlinedTextFieldcolors` with `OutlinedTextFieldDefaults.colors` method.

The `TextFieldDefaults.outlinedTextFieldcolors` method is commented.

##### reference 
Here is [`OutlinedTextFieldDefaults.colors` method in latest stable version of androidx.compose.material](https://developer.android.com/reference/kotlin/androidx/compose/material3/OutlinedTextFieldDefaults)

Here is [`TextFieldDefaults.outlinedTextFieldcolors` method (deprecated) androidx.compose.material](https://developer.android.com/reference/kotlin/androidx/compose/material/TextFieldDefaults)

