package com.example.ex02

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ex02.ButtonNumber
import com.example.ex02.ButtonOperation
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
fun Values (modifier: Modifier = Modifier, input: String, result: String)
{
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment =  Alignment.End,
        verticalArrangement = Arrangement.Top
    )
    {
        Text(text = result, color = Color.White, fontSize = 80.sp)
        Text(text = input, color = Color.White,fontSize = 60.sp)

        Spacer(Modifier.height(100.dp))
    }
}

@Composable
fun Buttons(modifier: Modifier = Modifier, onNumberClick: (String) -> Unit)
{
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start,

        )
    {
        Row(Modifier.fillMaxWidth())
        {
            ButtonNumber("7", onClick = onNumberClick)
            ButtonNumber("8", onClick = onNumberClick)
            ButtonNumber("9", onClick = onNumberClick)
            ButtonOperation("C", onNumberClick)
            ButtonOperation("AC", onClick = onNumberClick)
        }
        Row()
        {
            ButtonNumber("4", onClick = onNumberClick)
            ButtonNumber("5", onClick = onNumberClick)
            ButtonNumber("6", onClick = onNumberClick)
            ButtonOperation("+", onClick = onNumberClick)
            ButtonOperation("-", onClick = onNumberClick)
        }
        Row()
        {
            ButtonNumber("1", onClick = onNumberClick)
            ButtonNumber("2", onClick = onNumberClick)
            ButtonNumber("3", onClick = onNumberClick)
            ButtonOperation("x", onClick = onNumberClick)
            ButtonOperation("/", onClick = onNumberClick)
        }
        Row()
        {
            ButtonNumber("0", onClick = onNumberClick)
            ButtonNumber(".", onClick = onNumberClick)
            ButtonNumber("00", onClick = onNumberClick)

            Button(
                modifier = Modifier.weight(2f).height(80.dp),
                onClick = { onNumberClick("=") },
                colors = OperationButtonStyle())
            { Text("=") }
        }
    }
}

@Composable
fun RowScope.ButtonNumber (text: String, onClick: (String) -> Unit)
{
    Button( modifier = Modifier.weight(1f)
        .height(80.dp)
        .aspectRatio(1f),
        onClick = { onClick(text) },
        colors =  NumberButtonStyle()
    )
    { Text(text) }
}

@Composable
fun RowScope.ButtonOperation (text: String, onClick: (String) -> Unit)
{
    Button( modifier = Modifier.weight(1f)
        .height(80.dp)
        .aspectRatio(1f),
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