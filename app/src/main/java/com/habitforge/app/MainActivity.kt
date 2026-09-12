package com.habitforge.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Habit(
    val name: String,
    val icon: String,
    var completed: Boolean = false
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HabitForgeApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitForgeApp() {

    var newHabit by remember { mutableStateOf("") }

    val habits = remember {
        mutableStateListOf(
            Habit("Drink Water", "💧"),
            Habit("Workout", "🏋️"),
            Habit("Study", "📚")
        )
    }

    val completedCount = habits.count { it.completed }

    MaterialTheme {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "🔥 HabitForge",
                            fontSize = 22.sp
                        )
                    }
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {

                Text(
                    text = "Build better habits, one day at a time.",
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = newHabit,
                    onValueChange = { newHabit = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("New habit")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if (newHabit.isNotBlank()) {
                            habits.add(
                                Habit(
                                    name = newHabit.trim(),
                                    icon = "🔥"
                                )
                            )
                            newHabit = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Habit")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Today's Progress",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = {
                        if (habits.isEmpty()) {
                            0f
                        } else {
                            completedCount.toFloat() / habits.size
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$completedCount / ${habits.size} habits completed"
                )

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {

                    items(habits) { habit ->

                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = habit.icon,
                                    fontSize = 26.sp
                                )

                                Spacer(
                                    modifier = Modifier.width(12.dp)
                                )

                                Text(
                                    text = habit.name,
                                    modifier = Modifier.weight(1f),
                                    fontSize = 17.sp
                                )

                                Checkbox(
                                    checked = habit.completed,
                                    onCheckedChange = {
                                        habit.completed = it
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
