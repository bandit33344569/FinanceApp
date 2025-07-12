package com.abrosimov.transactions.edit_transaction.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abrosimov.utils.dateutils.DateUtils.padTime
import com.abrosimov.utils.dateutils.DateUtils.parseTimeString
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    initialTime: String = "00:00",
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val time = remember { mutableStateOf(parseTimeString(initialTime)) }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val timePickerState = rememberTimePickerState(
                initialHour = time.value.get(Calendar.HOUR_OF_DAY),
                initialMinute = time.value.get(Calendar.MINUTE)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = onDismiss, colors = ButtonDefaults.textButtonColors()) {
                    Text("Отмена", color = MaterialTheme.colorScheme.error)
                }
                TextButton(onClick = {
                    val selectedTime = "${timePickerState.hour}:${timePickerState.minute}"
                    onTimeSelected(selectedTime.padTime())
                    onDismiss()
                }) {
                    Text("OK")
                }
            }
            TimePicker(
                state = timePickerState,
                modifier = Modifier.weight(1f)
            )

        }
    }
}