# android compose material3
## color 
### color about text field
#### `OutlinedTextFieldDefaults.colors`
##### intro
> [!WARNING]
> Since I found `TextFieldDefaults.outlinedTextFieldcolors` method is depreciated in newer version of androidx.compose.material3 dependency. 
> 
> I use `OutlinedTextFieldDefaults.colors` method instead.

Jay30 found `TextFieldDefaults.outlinedTextFieldcolors` method at two `TextField` components in `LoginScreen.kt` file.

Due to depreciated method and it will throw compiler error before I modified(tested in my notebook with newer version of dependency),

Jay30 decide and finish to replace `TextFieldDefaults.outlinedTextFieldcolors` with `OutlinedTextFieldDefaults.colors` method (which is defined as getter of `outlinedTextFieldDefaultColors` in `DefaultColorViewModel.kt` file).

The `TextFieldDefaults.outlinedTextFieldcolors` method is commented.

##### reference 
Here is [`OutlinedTextFieldDefaults.colors` method in latest stable version of androidx.compose.material](https://developer.android.com/reference/kotlin/androidx/compose/material3/OutlinedTextFieldDefaults)

Here is [`TextFieldDefaults.outlinedTextFieldcolors` method (deprecated) androidx.compose.material](https://developer.android.com/reference/kotlin/androidx/compose/material/TextFieldDefaults)

## about drop down menu
### `DropdownMenuItem`
Due to different order of arguments in newer version of dependency in androidx.compose.material3.

Jay30 refactor the code about `DropdownMenuItem` component.