package com.example.debit72.android.widgets

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.OutlinedTextFieldDecorationBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.debit72.android.presenter.theme.DebitTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DebitTextFieldDense(
    text: TextFieldValue,
    onChange: (value: TextFieldValue) -> Unit,
    labelText: String,
    placeholderText: String = "",
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isReadOnly: Boolean = false,
    visualTransformation: VisualTransformation? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String = "",
    isClickable: Boolean = false,
    textAlign: TextAlign = TextAlign.Start,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = DebitTheme.colors.onSecondary,
        focusedLabelColor = DebitTheme.colors.onSecondary,
        unfocusedBorderColor = DebitTheme.colors.onSecondary,
        placeholderColor = DebitTheme.colors.onSecondary,
        disabledPlaceholderColor = DebitTheme.colors.onSurface,
        disabledTextColor = DebitTheme.colors.onSurface,
        errorBorderColor = DebitTheme.colors.error
    )
    BasicTextField(
        value = text,
        onValueChange = onChange,
        modifier = modifier
            .padding(top = 8.dp, bottom = 4.dp)
            .height(42.dp),
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = maxLines == 1,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        readOnly = isReadOnly,
        visualTransformation = visualTransformation ?: VisualTransformation.None,
        textStyle = DebitTheme.typography.bodyLarge16.copy(color = DebitTheme.colors.text)
    ) {
        OutlinedTextFieldDecorationBox(
            value = text.text,
            visualTransformation = visualTransformation ?: VisualTransformation.None,
            innerTextField = it,
            enabled = enabled,
            interactionSource = interactionSource,
            contentPadding =
            TextFieldDefaults.textFieldWithoutLabelPadding(
                start = 16.dp, end = 16.dp,
                top = (-8).dp,
                bottom = (-8).dp
            ),
            border = {
                TextFieldDefaults.BorderBox(
                    enabled = enabled,
                    isError = false,
                    colors = colors,
                    interactionSource = interactionSource,
                    shape = RoundedCornerShape(8.dp),
                )
            },
            label = {
                if (isError && errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = DebitTheme.colors.error,
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                } else
                    Text(
                        text = labelText,
                        fontSize = 12.sp,
                        color = DebitTheme.colors.onSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
            },
            placeholder = {
                Text(
                    text = placeholderText,
                    fontSize = 12.sp,
                    color = DebitTheme.colors.onPrimary,
                )
            },
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            isError = isError,
            singleLine = maxLines == 1,
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DebitTextFieldDense(
    text: String,
    onChange: (value: String) -> Unit,
    labelText: String,
    placeholderText: String = "",
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isReadOnly: Boolean = false,
    visualTransformation: VisualTransformation? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String = "",
    isClickable: Boolean = false,
    textAlign: TextAlign = TextAlign.Start,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = DebitTheme.colors.onSecondary,
        focusedLabelColor = DebitTheme.colors.onSecondary,
        unfocusedBorderColor = DebitTheme.colors.onSecondary,
        placeholderColor = DebitTheme.colors.onSecondary,
        disabledPlaceholderColor = DebitTheme.colors.onSurface,
        disabledTextColor = DebitTheme.colors.onSurface,
        errorBorderColor = DebitTheme.colors.error
    )
    BasicTextField(
        value = text,
        onValueChange = onChange,
        modifier = modifier
            .padding(top = 8.dp, bottom = 4.dp)
            .height(42.dp),
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = maxLines == 1,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        readOnly = isReadOnly,
        visualTransformation = visualTransformation ?: VisualTransformation.None,
        textStyle = DebitTheme.typography.bodyLarge16.copy(color = DebitTheme.colors.text)
    ) {
        OutlinedTextFieldDecorationBox(
            value = text,
            visualTransformation = visualTransformation ?: VisualTransformation.None,
            innerTextField = it,
            enabled = enabled,
            interactionSource = interactionSource,
            contentPadding =
            TextFieldDefaults.textFieldWithoutLabelPadding(
                start = 16.dp, end = 16.dp,
                top = (-8).dp,
                bottom = (-8).dp
            ),
            border = {
                TextFieldDefaults.BorderBox(
                    enabled = enabled,
                    isError = false,
                    colors = colors,
                    interactionSource = interactionSource,
                    shape = RoundedCornerShape(8.dp),
                )
            },
            label = {
                if (isError && errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = DebitTheme.colors.error,
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                } else
                    Text(
                        text = labelText,
                        fontSize = 12.sp,
                        color = DebitTheme.colors.onSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
            },
            placeholder = {
                Text(
                    text = placeholderText,
                    fontSize = 12.sp,
                    color = DebitTheme.colors.onPrimary,
                )
            },
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            isError = isError,
            singleLine = maxLines == 1,
        )

    }
}
