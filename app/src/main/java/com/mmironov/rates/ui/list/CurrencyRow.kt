package com.mmironov.rates.ui.list

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class CurrencyRow(
    val icon: Drawable,
    val code: String,
    val description: String,
    var index: Int) : BaseObservable() {

    @get:Bindable
    var amount: String = "1.0"
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.amount)
            }
        }

    fun copy(
        icon: Drawable = this.icon,
        code: String = this.code,
        description: String = this.description,
        index: Int = this.index
    ): CurrencyRow {
        val copied = CurrencyRow(icon, code, description, index)
        copied.amount = this.amount
        return copied
    }
}