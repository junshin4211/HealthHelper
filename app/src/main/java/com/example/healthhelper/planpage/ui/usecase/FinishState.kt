package com.example.healthhelper.planpage.ui.usecase

enum class FinishState(val state: Int) {
    OnGoing(0),
    Finished(1),
    FinishedWithGoal(2),
    Delete(3);

    companion object {
        fun fromInt(value: Int) = entries.find { it.state == value }
    }
}