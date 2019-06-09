package io.inscrib.inscribio

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.afterTextChange(block: (editable: Editable) -> Unit) {
    return this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable) {
            block(p0)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            return
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            return
        }

    })
}
