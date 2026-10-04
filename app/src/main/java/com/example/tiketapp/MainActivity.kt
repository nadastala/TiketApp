package com.example.tiketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                TicketScreen()
            }
        }
    }
}

/*
 * PARENT
 * Semua state utama dikelola di sini.
 */
@Composable

fun TicketScreen() {

    var hargaTiket by remember {
        mutableStateOf("50000")
    }

    var jumlahTiket by remember {
        mutableStateOf("1")
    }

    var namaPembeli by remember {
        mutableStateOf("")
    }

    var status by remember {
        mutableStateOf("Nama Masih Kosong")
    }

    var orderTrigger by remember {
        mutableStateOf(0)
    }

    /*
     * LaunchedEffect berjalan ketika orderTrigger berubah.
     */
    LaunchedEffect(orderTrigger) {

        if (orderTrigger > 0) {

            status = "Memproses pesanan........."

            delay(5000)

            status = "Tiket telah dipesan"
        }
    }

    TicketPage(
        hargaTiket = hargaTiket,
        jumlahTiket = jumlahTiket,
        namaPembeli = namaPembeli,
        status = status,

        onHargaChange = {
            hargaTiket = it
        },

        onJumlahChange = {
            jumlahTiket = it
        },

        onNamaChange = {
            namaPembeli = it
        },

        onPesanClick = {

            if (namaPembeli.isBlank()) {

                status = "Nama Masih Kosong"

            } else {

                orderTrigger++
            }
        }
    )
}


/*
 * CHILD
 * Tidak menyimpan state harga, jumlah, atau nama.
 * Semua data diberikan oleh Parent melalui parameter.
 */
@Composable
fun TicketPage(
    hargaTiket: String,
    jumlahTiket: String,
    namaPembeli: String,
    status: String,

    onHargaChange: (String) -> Unit,
    onJumlahChange: (String) -> Unit,
    onNamaChange: (String) -> Unit,
    onPesanClick: () -> Unit
) {

    val harga = hargaTiket.toLongOrNull() ?: 0L
    val jumlah = jumlahTiket.toLongOrNull() ?: 0L
    val total = harga * jumlah

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "HALAMAN TIKET",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Pemesanan Tiket",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = hargaTiket,
                    onValueChange = onHargaChange,
                    label = {
                        Text("Harga Tiket")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = jumlahTiket,
                    onValueChange = onJumlahChange,
                    label = {
                        Text("Jumlah Tiket")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = namaPembeli,
                    onValueChange = onNamaChange,
                    label = {
                        Text("Nama Pembeli Tiket")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Total: Rp $total",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onPesanClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("PESAN TIKET")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Status: $status",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}