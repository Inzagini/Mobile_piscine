package com.example.ex02

import android.R.attr.onClick
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.ex02.ui.theme.DarkNavyBlue
import com.example.ex02.ui.theme.Ex02Theme
import com.example.ex02.ui.theme.LightNavyBlue
import com.example.ex02.ui.theme.Pink80


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ex02Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


data class CalState (
    var inputStr: String = "",
    var result: String = "0"
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(modifier: Modifier = Modifier)
{
    var state by remember {mutableStateOf(CalState())}

    Scaffold (
        containerColor = DarkNavyBlue,
        topBar = { TopAppBar() }
    )
    {
        innerPadding ->

            Column (modifier = Modifier.fillMaxSize().padding( top =innerPadding.calculateTopPadding()),
                horizontalAlignment = Alignment.End)
            {

                Values(
                    modifier = modifier,
                    input = state.inputStr,
                    result = state.result
                )


                Buttons(
                    modifier = Modifier.padding(innerPadding),
                    onNumberClick = { value -> state = onNumberClick(state, value) }
                )
            }

    }
}

fun onNumberClick (state: CalState, value: String) : CalState
{
    val opListString =listOf<String>("+", "-", "x", "/")
    val opListChar=listOf<Char>('+', '-', 'x', '/')

    return when (value) {
        "AC" -> { state.copy(
            inputStr = "",
            result = "0"
        )}
        "C" -> { state.copy(inputStr = state.inputStr.dropLast(1))}
        "=" -> {
            state.copy(
                result = calculateResult(state.inputStr),
                inputStr = ""
            )
        }
        else -> {

            if (value == "."){

                if (state.inputStr.isEmpty()){
                    Log.d("PARS", "append first 0")
                    state.inputStr = "0"
                }
                else if (state.inputStr.last() == '.'){
                    Log.d("PARS", "Drop one additional .")
                    state.inputStr = state.inputStr.dropLast(1)
                }

            }

            if (value in opListString && state.inputStr.isNotEmpty() &&
                state.inputStr.last() in listOf('+', '-', 'x', '/')){

                if (value == "-" && state.inputStr.last() in listOf('/', 'x')){

                }
                else if (value in opListString && state.inputStr.getOrNull(state.inputStr.length - 2) in listOf('x', '/'))
                {
                    state.inputStr = state.inputStr.dropLast(2)
                }
                else{
                    state.inputStr = state.inputStr.dropLast(1)
                }

            }

            if (value != "-" && value in opListString && state.inputStr.isEmpty()){
                return state
            }

            state.copy(inputStr = state.inputStr + value)
        }
    }
}

fun calculateResult(inputStr: String): String
{
    var stack = ArrayDeque<Int>()

    return try {
        Log.d("EXPR", inputStr)

        var tokens = tokenize(inputStr)
        var parsedTokens = ShuntingYardAlgo(tokens)

        var res = evaluate(parsedTokens).toString()
        if (res.endsWith(".0") && res.length >= 2){
            res = res.dropLast(2)
        }

        res
    }
    catch (e: Exception){
        "Error"
    }
}

fun evaluate(tokens: List<String>): Double
{
    var stack = ArrayDeque<Double>()

    for (x in tokens)
    {
        if (x.toDoubleOrNull() != null){
            val value = x.toDoubleOrNull()
            if (value != null)
                stack.add(value)
        }
        else {
            val right = stack.removeLast()
            val left = stack.removeLast()

            var res = 0.0

            if (x == "+")
                res = left + right
            else if (x == "-")
                res = left - right
            else if (x == "*")
                res = left * right
            else if (x == "/")
            {
                if (right == 0.0)
                    throw IllegalArgumentException()
                res = left / right
            }


            stack.add(res)
        }
    }
    if (stack.isNotEmpty())
        return stack.last()
    return 0.0
}

//Reorder of the tokens
fun ShuntingYardAlgo(tokens: List<String>): List<String>
{
    var parsedTokens = mutableListOf<String>()
    var stackOp = ArrayDeque<String>()

    for (x in tokens)
    {
        if (x in listOf("+", "-", "*", "/")){

            while (stackOp.isNotEmpty() &&
                precedense(x) >= precedense(stackOp.last()))
            {
                parsedTokens.add(stackOp.removeLast())
            }

            stackOp.add(x)
        }
        else
            parsedTokens.add(x)
    }

    while(stackOp.isNotEmpty()){
        parsedTokens.add(stackOp.removeLast())
    }

    return parsedTokens
}

//Without support of "()"
fun precedense(op: String): Int{
    return when (op){
        "+", "-" -> 2
        "*", "/" -> 3
        else -> 0
    }
}

fun tokenize(input: String): List<String>
{
    var tokenList = mutableListOf<String>()
    var minus = false
    var number = ""

    for (c in input)
    {
        if (c.isDigit())
            number += c
        else if (c == '.')
            number += c
        else if (c in listOf('x', '/', '+', '-')){
            if(c == '-' && number.isEmpty()){
                minus = true
            }
            else if (number.isNotEmpty()){

                if (minus)
                    number = "-" + number

                tokenList.add(number)
                number = ""

                tokenList.add( if (c == 'x') "*" else c.toString())
            }
        }
    }

    if (number.isNotEmpty()){
        if (minus)
            number = "-" + number
        tokenList.add(number)
    }

    return tokenList
}

