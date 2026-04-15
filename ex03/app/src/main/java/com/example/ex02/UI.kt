package com.example.ex02

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ex02.ButtonNumber
import com.example.ex02.ButtonOperation
import com.example.ex02.ui.theme.Ex02Theme
import com.example.ex02.ui.theme.LightNavyBlue
import com.example.ex02.ui.theme.Pink80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar()
{
    CenterAlignedTopAppBar (
        title = { Text(text = "Calculator", textAlign = TextAlign.Center) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LightNavyBlue,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun ColumnScope.Values (modifier: Modifier, input: String, result: String)
{
        var fontSizeResult = (LocalConfiguration.current.screenHeightDp * 0.1).sp
        var fontSizeInput = (LocalConfiguration.current.screenHeightDp * 0.05).sp

        Text(
            modifier = modifier.weight(1.5f),
            text = result,
            color = Color.White,
            fontSize = fontSizeResult,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.weight(1.5f),
            text = input,
            color = Color.White,
            fontSize = fontSizeInput,
            maxLines = 1,
            overflow = TextOverflow.StartEllipsis
        )

}

@Composable
fun ColumnScope.Buttons(modifier: Modifier, onNumberClick: (String) -> Unit)
{
        Row(modifier = Modifier.weight(1f))
        {
            ButtonNumber(modifier = Modifier.weight(1f), "7", onClick = onNumberClick)
            ButtonNumber(modifier = Modifier.weight(1f),"8", onClick = onNumberClick)
            ButtonNumber(modifier = Modifier.weight(1f),"9", onClick = onNumberClick)
            ButtonOperation(modifier = Modifier.weight(1f),"C", onNumberClick)
            ButtonOperation(modifier = Modifier.weight(1f),"AC", onClick = onNumberClick)
        }
        Row(modifier = Modifier.weight(1f),)
        {
            ButtonNumber(modifier = Modifier.weight(1f),"4", onClick = onNumberClick)
            ButtonNumber(modifier = Modifier.weight(1f),"5", onClick = onNumberClick)
            ButtonNumber(modifier = Modifier.weight(1f),"6", onClick = onNumberClick)
            ButtonOperation(modifier = Modifier.weight(1f),"+", onClick = onNumberClick)
            ButtonOperation(modifier = Modifier.weight(1f),"-", onClick = onNumberClick)
        }
        Row(modifier = Modifier.weight(1f))
        {
            ButtonNumber(modifier = Modifier.weight(1f),"1", onClick = onNumberClick)
            ButtonNumber(modifier = Modifier.weight(1f),"2", onClick = onNumberClick)
            ButtonNumber(modifier = Modifier.weight(1f),"3", onClick = onNumberClick)
            ButtonOperation(modifier = Modifier.weight(1f),"x", onClick = onNumberClick)
            ButtonOperation(modifier = Modifier.weight(1f),"/", onClick = onNumberClick)
        }
        Row(modifier = Modifier.weight(1f))
        {
            ButtonNumber(modifier = Modifier.weight(1f),"0", onClick = onNumberClick)
            ButtonNumber(modifier = Modifier.weight(1f),".", onClick = onNumberClick)
            ButtonNumber(modifier = Modifier.weight(1f),"00", onClick = onNumberClick)

            Button(
                modifier = Modifier.weight(2f).fillMaxHeight(),
                onClick = { onNumberClick("=") },
                colors = OperationButtonStyle())
            { Text("=") }
        }
}

@Composable
fun RowScope.ButtonNumber (modifier: Modifier, text: String, onClick: (String) -> Unit)
{

    Button( modifier = modifier
        .fillMaxHeight(),
        onClick = { onClick(text) },
        colors =  NumberButtonStyle()
    )
    { Text(text) }
}

@Composable
fun RowScope.ButtonOperation (modifier: Modifier, text: String, onClick: (String) -> Unit)
{
    Button( modifier = modifier
        .fillMaxHeight(),
        onClick = { onClick(text) },
        colors =  OperationButtonStyle()
    )
    { Text(text) }
}

fun NumberButtonStyle(): ButtonColors
{
    return ButtonColors (
        containerColor = LightNavyBlue,
        contentColor = Color.White,
        disabledContentColor = Color.Red,
        disabledContainerColor = Color.Red,
    )
}

fun OperationButtonStyle(): ButtonColors
{
    return ButtonColors (
        containerColor = Pink80,
        contentColor = Color.Black,
        disabledContentColor = Color.Red,
        disabledContainerColor = Color.Red,
    )
}

@Preview(name= "Horizontal", showBackground = true, widthDp = 915, heightDp = 412)
@Composable
fun HorizontalPreview() {
    Ex02Theme {
        App()
    }
}

@Preview(name= "Vertical", showBackground =  true, widthDp = 412, heightDp = 915)
@Composable
fun VerticalPreview() {
    Ex02Theme {
        App()
    }
}