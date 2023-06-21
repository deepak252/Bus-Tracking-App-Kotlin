package com.example.bustrackingapp.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bustrackingapp.ui.theme.Blue500
import com.example.bustrackingapp.ui.theme.Blue700
import com.example.bustrackingapp.ui.theme.Gray400
import com.example.bustrackingapp.ui.theme.NavyBlue200
import com.example.bustrackingapp.ui.theme.NavyBlue500
import com.example.bustrackingapp.ui.theme.Red500


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: ()->String,
    onValueChange: (String) -> Unit,
    onClearFocus: () -> Unit,
    hintText : String = "",
    labelText : String?=null,
    maxLines : Int =1,
    keyboardType : KeyboardType= KeyboardType.Text,
    imeAction : ImeAction = ImeAction.Done,
    singleLine: Boolean = true,
    leadingIcon : ImageVector?=null,
    trailingIcon : ImageVector?=null,
    onTrailingIconClick : (()->Unit)?=null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    radius : Int = 8,
    errorMessage : (()->String?)?=null,
    prefix: @Composable (() -> Unit)? = null,
    textSize: TextUnit = 16.sp,
    color: Color = MaterialTheme.colorScheme.secondaryContainer,
    width: Float = 1f,
    contentPadding: PaddingValues = PaddingValues(
        start = 14.dp, top = 14.dp, end = 14.dp, bottom = 10.dp,
    ),
){

    Column() {
        OutlinedTextField(
            value(),
            onValueChange,
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(radius.dp),
            maxLines=maxLines,
            singleLine = singleLine,
            placeholder = {
                Text(
                    hintText
                )
            },
            prefix = prefix,

            label = {
                if(labelText!=null){
                    Text(
                        labelText
                    )
                }
            },
            leadingIcon = if(leadingIcon!=null){
                {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 4.dp),
                        tint = NavyBlue200
                    )
                }
            }else {null},
            trailingIcon = {
                if(trailingIcon!=null){
                    IconButton(
                        onClick = {
                            if(onTrailingIconClick!=null) onTrailingIconClick()
                        }
                    ) {
                        Icon(
                            imageVector = trailingIcon,
                            contentDescription = null
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Blue700,
                selectionColors = TextSelectionColors(
                    backgroundColor = Blue500,
                    handleColor = Blue700
                ),
                focusedBorderColor = Blue700,
                unfocusedBorderColor = NavyBlue500,
                focusedTrailingIconColor = Blue700,
                focusedLabelColor = Blue500,
                unfocusedLabelColor = Gray400,
                focusedPlaceholderColor = Gray400,
                unfocusedPlaceholderColor = Gray400,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onClearFocus()
                }
            ),
            visualTransformation = visualTransformation
        )
        if(errorMessage!=null && errorMessage()!=null){
            Text(
                errorMessage()!!,
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 6.dp
                ),
                style = MaterialTheme.typography.labelSmall,
                color = Red500
            )
        }
    }

}


