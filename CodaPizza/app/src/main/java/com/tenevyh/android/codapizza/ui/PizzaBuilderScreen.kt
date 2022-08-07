package com.tenevyh.android.codapizza.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tenevyh.android.codapizza.R
import com.tenevyh.android.codapizza.model.Pizza
import com.tenevyh.android.codapizza.model.Topping
import com.tenevyh.android.codapizza.model.ToppingPlacement
import java.text.NumberFormat

@Preview
@Composable
fun PizzaBuilderScreen(modifier: Modifier = Modifier){
    var pizza by rememberSaveable(Pizza()) {
        mutableStateOf(Pizza())
    }
    Column(modifier = modifier) {
        ToppingsList(
            pizza = pizza,
            onEditPizza = {pizza = it},
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true))
        OrderButton(pizza = pizza,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp))
    }
}

@Composable
private fun ToppingsList(pizza: Pizza, onEditPizza: (Pizza) -> Unit,
                         modifier: Modifier = Modifier){
    var toppingBeingAdded by rememberSaveable { mutableStateOf<Topping?>(null)
    }

    toppingBeingAdded?.let { topping ->
        ToppingPlacementDialog(
            topping = topping,
            onDismissRequest = {
                toppingBeingAdded = null
            }
        )
    }
    LazyColumn(modifier = modifier){
        items(Topping.values()) { topping ->
            ToppingCell(topping = topping,
                placement = pizza.toppings[topping],
                onClickTopping = {
                    toppingBeingAdded = topping
                }
            )
        }
    }
}

@Composable
private fun OrderButton(
    pizza: Pizza,
    modifier: Modifier = Modifier){
    Button(modifier = modifier,
        onClick = { /*TODO*/ }) {
        val currencyFormatter = remember{NumberFormat.getCurrencyInstance()}
        val price = currencyFormatter.format(pizza.price)
        Text(
            text = stringResource(R.string.place_order_button, price)
                .toUpperCase(Locale.current)
        )
    }
}
