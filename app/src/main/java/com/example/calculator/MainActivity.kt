package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculator()
        }
    }
}

@Composable
fun Calculator() {
    val prev = rememberSaveable { mutableStateOf("") }
    val op = rememberSaveable { mutableStateOf("") }
    val num = rememberSaveable { mutableStateOf("0") }
    val isNew = rememberSaveable { mutableStateOf(true) }

    fun press(btn: String) {
        if (btn == "C") {
            prev.value = ""
            op.value = ""
            num.value = "0"
            isNew.value = true
        } else if (btn == "+" || btn == "-" || btn == "*" || btn == "/") {
            if (!isNew.value) {
                if (prev.value == "") {
                    prev.value = num.value + " " + btn
                } else {
                    prev.value = prev.value + " " + num.value + " " + btn
                }
                isNew.value = true
            }
            op.value = btn
        } else if (btn == "=") {
            if (prev.value != "" && op.value != "") {
                val full = prev.value + " " + num.value
                num.value = calc(full)
                prev.value = ""
                op.value = ""
                isNew.value = true
            }
        } else if (btn == ".") {
            if (!num.value.contains(".")) {
                num.value = num.value + "."
                isNew.value = false
            }
        } else {
            if (isNew.value || num.value == "0") {
                num.value = btn
                isNew.value = false
            } else {
                num.value = num.value + btn
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(
                text = prev.value,
                fontSize = 18.sp,
                color = Color.Gray
            )
            Text(
                text = op.value,
                fontSize = 24.sp
            )
            Text(
                text = num.value,
                fontSize = 36.sp
            )
        }

        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Button(onClick = { press("7") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "7", fontSize = 20.sp)
                }
                Button(onClick = { press("8") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "8", fontSize = 20.sp)
                }
                Button(onClick = { press("9") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "9", fontSize = 20.sp)
                }
                Button(onClick = { press("/") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "/", fontSize = 20.sp)
                }
            }

            Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Button(onClick = { press("4") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "4", fontSize = 20.sp)
                }
                Button(onClick = { press("5") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "5", fontSize = 20.sp)
                }
                Button(onClick = { press("6") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "6", fontSize = 20.sp)
                }
                Button(onClick = { press("*") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "*", fontSize = 20.sp)
                }
            }

            Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Button(onClick = { press("1") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "1", fontSize = 20.sp)
                }
                Button(onClick = { press("2") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "2", fontSize = 20.sp)
                }
                Button(onClick = { press("3") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "3", fontSize = 20.sp)
                }
                Button(onClick = { press("-") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "-", fontSize = 20.sp)
                }
            }

            Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Button(onClick = { press("C") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "C", fontSize = 20.sp)
                }
                Button(onClick = { press("0") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "0", fontSize = 20.sp)
                }
                Button(onClick = { press(".") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = ".", fontSize = 20.sp)
                }
                Button(onClick = { press("+") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "+", fontSize = 20.sp)
                }
            }

            Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Button(onClick = { press("=") }, modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp)) {
                    Text(text = "=", fontSize = 20.sp)
                }
            }
        }
    }
}

fun calc(str: String): String {
    val list = str.split(" ").toMutableList()

    var i = 0
    while (i < list.size) {
        if (list[i] == "*" || list[i] == "/") {
            val a = list[i - 1].toDouble()
            val b = list[i + 1].toDouble()
            if (list[i] == "/" && b == 0.0) return "Ошибка"
            val res = if (list[i] == "*") a * b else a / b
            list[i - 1] = res.toString()
            list.removeAt(i)
            list.removeAt(i)
        } else {
            i++
        }
    }

    var res = list[0].toDouble()
    i = 1
    while (i < list.size) {
        val sign = list[i]
        val b = list[i + 1].toDouble()
        if (sign == "+") {
            res += b
        } else {
            res -= b
        }
        i += 2
    }

    return res.toString()
}