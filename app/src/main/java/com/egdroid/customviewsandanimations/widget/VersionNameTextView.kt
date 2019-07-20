package com.egdroid.customviewsandanimations.widget

import android.content.Context
import android.content.pm.PackageManager
import android.util.AttributeSet
import android.widget.TextView
import com.egdroid.customviewsandanimations.R

/**
 * Created at Tito on 2019-07-20
 *
 * VersionNameTextView is a class that extends [TextView] and makes some logic to
 * get the version name of the application.
 */

class VersionNameTextView : TextView {

    constructor(context: Context) : super(context) {
        setVersion()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setVersion()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setVersion()
    }

    private fun setVersion() {
        text = try {
            val packageInfo = context.packageManager.getPackageInfo(
                context.packageName, 0
            )
            context.resources.getString(
                R.string.version_name,
                packageInfo.versionName
            )
        } catch (e: PackageManager.NameNotFoundException) {
            context.resources.getString(
                R.string.error_version_name
            )
        }
    }

}