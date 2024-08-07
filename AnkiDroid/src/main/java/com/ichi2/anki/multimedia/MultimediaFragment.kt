/*
 * Copyright (c) 2024 Ashish Yadav <mailtoashish693@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ichi2.anki.multimedia

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.ichi2.anki.AnkiActivity
import com.ichi2.anki.R
import com.ichi2.anki.multimediacard.IMultimediaEditableNote
import com.ichi2.anki.multimediacard.fields.IField
import com.ichi2.anki.snackbar.showSnackbar
import com.ichi2.compat.CompatHelper.Companion.getSerializableCompat
import com.ichi2.utils.show
import timber.log.Timber

/**
 * Abstract base class for fragments that handle multimedia operations.
 *
 * This class provides a common framework for fragments that need to handle multimedia operations,
 * including caching directories, managing fields and notes, and setting toolbar titles.
 *
 * @param layout The layout resource ID to be inflated by this fragment.
 */
abstract class MultimediaFragment(@LayoutRes layout: Int) : Fragment(layout) {

    abstract val title: String

    protected var ankiCacheDirectory: String? = null

    protected var indexValue: Int = 0
    protected lateinit var field: IField
    protected lateinit var note: IMultimediaEditableNote

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AnkiActivity).setToolbarTitle(title)

        if (arguments != null) {
            Timber.d("Getting MultimediaActivityExtra values from arguments")
            val multimediaActivityExtra = arguments?.getSerializableCompat(MultimediaActivity.MULTIMEDIA_ARGS_EXTRA) as? MultimediaActivityExtra

            if (multimediaActivityExtra != null) {
                indexValue = multimediaActivityExtra.index
                field = multimediaActivityExtra.field
                note = multimediaActivityExtra.note
            }
        }
    }

    /**
     * Shows a Snackbar at the bottom of the screen with a predefined
     * "Something went wrong" message from the application's resources.
     */
    fun showSomethingWentWrong() {
        showSnackbar(resources.getString(R.string.multimedia_editor_something_wrong))
    }

    /**
     * Creates and shows an AlertDialog with a predefined error message
     * from the application's resources. The dialog includes an "OK" button that,
     * when clicked, finishes the current activity.
     */
    fun showErrorDialog() {
        AlertDialog.Builder(requireContext()).show {
            setMessage(getString(R.string.something_wrong))
            setPositiveButton(getString(R.string.dialog_ok)) { _, _ ->
                requireActivity().finish()
            }
        }
    }
}
