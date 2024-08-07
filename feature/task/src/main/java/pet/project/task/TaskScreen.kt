package pet.project.task

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import pet.project.domain.TodoItem
import pet.project.theme.AppTheme
import pet.project.theme.CustomTheme
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.UUID

/* part 2 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    tsState: TaskScreenUiState,
    addItem: (item: TodoItem) -> Unit,
    updateItem: (String, TodoItem) -> Unit,
    resetRemovedStatus: (Boolean) -> Unit,
    removeTodoItem: (String) -> Unit,
    moveBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mContext = LocalContext.current

    val item = tsState.currentItem
    val removed = tsState.removed

    var mDate by rememberSaveable { mutableStateOf("") }

    var menuExpanded by rememberSaveable { mutableStateOf(false) }
    var switchState: Boolean by rememberSaveable { mutableStateOf(false) }

    var inputText by rememberSaveable(item?.taskText) { mutableStateOf(item?.taskText ?: "") }
    var importanceStatus by rememberSaveable(item?.importance?.importanceString) {
        mutableIntStateOf(
            item?.importance?.importanceString ?: R.string.Default
        )
    }

    val mCalendar = Calendar.getInstance()

    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        /* context = */
        mContext,
        /* themeResId = */
        if (CustomTheme.colors.isLight) {
            R.style.DatePickerLightTheme
        } else {
            R.style.DatePickerDarkTheme
        },
        /* listener = */
        { _: DatePicker, year: Int, month: Int, day: Int ->
            mDate = "$year-${if (month <= 8) 0 else ""}" +
                    "${month + 1}-${if (day <= 9) 0 else ""}$day"
        },
        /* year = */
        mYear,
        /* monthOfYear = */
        mMonth,
        /* dayOfMonth = */
        mDay
    )

    Column(modifier = modifier) {
        TaskTitle(
            onClose = {
                moveBack()
            },
            onAccept = {
                moveBack()
                val deadline = if (mDate != "") {
                    LocalDate.parse(mDate)
                } else if (switchState) {
                    null
                } else if (item?.deadline != null) {
                    item.deadline
                } else {
                    null
                }
                val importance = when (importanceStatus) {
                    R.string.Default -> pet.project.domain.TaskImportance.DEFAULT
                    R.string.Low -> pet.project.domain.TaskImportance.LOW
                    else -> pet.project.domain.TaskImportance.HIGH
                }
                if (item == null) {
                    addItem(
                        TodoItem(
                            id = UUID.randomUUID().toString(),
                            taskText = inputText,
                            importance = importance,
                            deadline = deadline
                        )
                    )
                } else {
                    updateItem(
                        item.id,
                        item.copy(
                            taskText = inputText,
                            importance = importance,
                            deadline = deadline
                        )
                    )
                }
            },
            inputText = inputText
        )
        Spacer(Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .verticalScroll(
                    rememberScrollState()
                )
                .weight(1f, fill = false)
        ) {
            TaskTextField(
                inputText = inputText,
                onTextChange = {
                    inputText = it
                }
            )
            TaskImportance(
                menuExpanded = menuExpanded,
                importanceStatus = importanceStatus,
                changeExpandedStatus = {
                    menuExpanded = !menuExpanded
                },
                onChoose = {
                    importanceStatus = it
                    menuExpanded = !menuExpanded
                }
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = CustomTheme.colors.supportSeparator,
                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp)
            )
            TaskDeadline(
                item = item,
                mDate = mDate,
                switchState = switchState,
                deadline = item?.deadline,
                onSwitchChecked = {
                    switchState = !switchState
                    if (switchState) {
                        mDatePickerDialog.show()
                    } else {
                        mDate = ""
                    }
                }
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = CustomTheme.colors.supportSeparator,
                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp)
            )
            TaskDeletion(
                item = item,
                onDelete = {
                    resetRemovedStatus(true)
                    removeTodoItem(item!!.id)
                    moveBack()
                },
                removed = removed
            )
            Spacer(Modifier.height(16.dp))
        }
        AnimatedVisibility(visible = menuExpanded) {
            ModalBottomSheet(
                onDismissRequest = { menuExpanded = false },
                containerColor = CustomTheme.colors.backSecondary
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            importanceStatus = R.string.Default
                            menuExpanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.Default),
                            textAlign = TextAlign.Center
                        )
                    }
                    TextButton(
                        onClick = {
                            importanceStatus = R.string.Low
                            menuExpanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.Low))
                    }
                    TextButton(
                        onClick = {
                            importanceStatus = R.string.High
                            menuExpanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.High),
                            color = CustomTheme.colors.red
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskTitle(
    onClose: () -> Unit,
    onAccept: () -> Unit,
    inputText: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        IconButton(
            onClick = onClose,
            modifier = Modifier.padding(top = 16.dp, start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Закрыть экран",
                tint = CustomTheme.colors.labelPrimary,
                modifier = Modifier
            )
        }
        TextButton(
            enabled = inputText.isNotEmpty(),
            onClick = onAccept,
            modifier = Modifier.padding(end = 4.dp, top = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.save),
                style = CustomTheme.typography.button,
                color = if (inputText != "") {
                    CustomTheme.colors.yellow
                } else {
                    CustomTheme.colors.gray
                },
                modifier = Modifier.testTag("save_task")
            )
        }
    }
}

@Composable
private fun TaskTextField(
    inputText: String,
    onTextChange: (String) -> Unit
) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = CustomTheme.colors.backSecondary,
            contentColor = CustomTheme.colors.labelPrimary
        ),
        elevation = CardDefaults.elevatedCardElevation(
            2.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        TextField(
            value = inputText,
            onValueChange = { onTextChange(it) },
            minLines = 3,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = CustomTheme.colors.backSecondary,
                focusedContainerColor = CustomTheme.colors.backSecondary,
                focusedTextColor = CustomTheme.colors.labelPrimary,
                unfocusedTextColor = CustomTheme.colors.labelPrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    stringResource(R.string.task_placeholder),
                    color = CustomTheme.colors.gray,
                    style = CustomTheme.typography.body
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("text_field")
        )
    }
}

@Composable
private fun TaskImportance(
    menuExpanded: Boolean,
    changeExpandedStatus: () -> Unit,
    onChoose: (Int) -> Unit,
    importanceStatus: Int
) {
    Box {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 12.dp)
                .clickable {
                    changeExpandedStatus()
                }
        ) {
            Text(
                text = stringResource(R.string.Importance),
                style = CustomTheme.typography.body,
                color = CustomTheme.colors.labelPrimary
            )
            Text(
                text = stringResource(importanceStatus),
                style = CustomTheme.typography.subhead,
                color = CustomTheme.colors.gray
            )
        }
//        TaskDropDownMenu(
//            menuExpanded = menuExpanded,
//            onDismiss = changeExpandedStatus,
//            onClick = { onChoose(it) }
//        )
    }
}

@Composable
private fun TaskDropDownMenu(
    menuExpanded: Boolean,
    onDismiss: () -> Unit,
    onClick: (importanceString: Int) -> Unit
) {
    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = onDismiss,
        offset = DpOffset(44.dp, (-16).dp),
        modifier = Modifier
            .background(
                CustomTheme.colors.backElevated
            )
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.Default),
                    color = CustomTheme.colors.labelPrimary
                )
            },
            onClick = { onClick(R.string.Default) }
        )
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.Low),
                    color = CustomTheme.colors.labelPrimary
                )
            },
            onClick = { onClick(R.string.Low) }
        )
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.High),
                    color = CustomTheme.colors.red
                )
            },
            onClick = { onClick(R.string.High) },
            leadingIcon = {
                Icon(
                    painter = painterResource(
                        id = R.drawable.baseline_priority_high_24
                    ),
                    contentDescription = null,
                    tint = CustomTheme.colors.red
                )
            }
        )
    }
}

@Composable
private fun TaskDeadline(
    item: TodoItem?,
    mDate: String,
    switchState: Boolean,
    deadline: LocalDate?,
    onSwitchChecked: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(start = 16.dp).semantics(mergeDescendants = true) {  }) {
            Text(
                text = stringResource(R.string.Deadline_title),
                style = CustomTheme.typography.body,
                color = CustomTheme.colors.labelPrimary,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = item?.deadline?.toString() ?: mDate,
                textDecoration = if (switchState && item?.deadline != null &&
                    item.deadline.toString() != mDate
                ) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                },
                style = CustomTheme.typography.subhead,
                color = if (switchState && item?.deadline != null &&
                    item.deadline.toString() != mDate
                ) {
                    CustomTheme.colors.gray
                } else {
                    CustomTheme.colors.yellow
                },
                modifier = Modifier.semantics {
                    stateDescription = if ((item?.deadline?.toString() ?: mDate) == "")
                        "Дедлайн отсутствует" else ""
                }
            )
            Text(
                text = if (item?.deadline != null && mDate != "" &&
                    mDate != deadline.toString()
                ) {
                    mDate
                } else {
                    ""
                },
                style = CustomTheme.typography.subhead,
                color = CustomTheme.colors.yellow
            )
        }
        Switch(
            checked = switchState,
            onCheckedChange = onSwitchChecked,
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = CustomTheme.colors.white,
                uncheckedBorderColor = Color.Transparent,
                uncheckedTrackColor = CustomTheme.colors.grayLight,
                checkedBorderColor = Color.Transparent,
                checkedThumbColor = CustomTheme.colors.yellow,
                checkedTrackColor = CustomTheme.colors.grayLight
            ),
            modifier = Modifier
                .scale(0.75f)
                .padding(end = 12.dp, top = 8.dp)
                .semantics {
                    contentDescription = "Изменить дедлайн"
                    stateDescription = if (switchState) "Дедлайн установлен" else "Дедлайн не установлен"
                }
        )
    }
}

@Composable
private fun TaskDeletion(
    item: TodoItem?,
    onDelete: () -> Unit,
    removed: Boolean
) {
    val red = CustomTheme.colors.red
    val gray = CustomTheme.colors.gray
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(
                enabled = item != null,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    color = CustomTheme.colors.supportSeparator
                ),
                onClick = onDelete
            )
            .testTag("delete_task")
    ) {
        Icon(
            Icons.Filled.Delete,
            contentDescription = "Кнопка",
            tint = if (removed) {
                gray
            } else {
                red
            },
            modifier = Modifier.semantics {
                stateDescription = if (removed) "Не активна" else "Активна"
            }
        )
        Text(
            text = stringResource(R.string.Delete),
            color = if (removed) {
                gray
            } else {
                red
            },
            style = CustomTheme.typography.body,
            modifier = Modifier.padding(start = 4.dp, top = 3.dp).semantics {
                stateDescription = if (!removed) "Нажать дважды чтобы удалить" else ""
            }
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun TaskScreenPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun TaskScreenDarkPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
        }
    }
}
