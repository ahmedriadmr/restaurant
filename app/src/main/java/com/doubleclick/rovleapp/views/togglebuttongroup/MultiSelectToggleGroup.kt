package com.doubleclick.rovleapp.views.togglebuttongroup

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import com.doubleclick.rovleapp.R

/**
 * Created By Eslam Ghazy on 11/20/2022
 */
class MultiSelectToggleGroup : ToggleButtonGroup {
    private var mOnCheckedStateChangeListener: OnCheckedStateChangeListener? = null
    private var mMaxSelectCount = -1

    constructor(context: Context?) : super(context!!)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MultiSelectToggleGroup, 0, 0
        )
        mMaxSelectCount = try {
            a.getInt(R.styleable.MultiSelectToggleGroup_tbgMaxSelect, -1)
        } finally {
            a.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val initialCheckedId: Int =
            if (mInitialCheckedId !== View.NO_ID) mInitialCheckedId else mSilentInitialCheckedId
        if (initialCheckedId != View.NO_ID) {
            setCheckedStateForView(initialCheckedId, true)
        }
    }

    override fun <T> onChildCheckedChange(
        child: T,
        isChecked: Boolean
    ) where T : View?, T : Checkable? {
        checkSelectCount()
        if (mSilentInitialCheckedId == child!!.id) {
            mSilentInitialCheckedId = View.NO_ID
        } else {
            notifyCheckedStateChange(child.id, isChecked)
        }
    }

    fun check(id: Int) {
        setCheckedStateForView(id, true)
    }

    fun check(id: Int, checked: Boolean) {
        setCheckedStateForView(id, checked)
    }

    fun setOnCheckedChangeListener(listener: OnCheckedStateChangeListener?) {
        mOnCheckedStateChangeListener = listener
    }

    private fun checkSelectCount() {
        if (mMaxSelectCount < 0) {
            return
        }
        val uncheckedViews: MutableList<View> = ArrayList()
        var checkedCount = 0
        for (i in 0 until childCount) {
            val view: View = getChildAt(i)
            if (view is Checkable) {
                if ((view as Checkable).isChecked) {
                    checkedCount++
                } else {
                    uncheckedViews.add(view)
                }
            }
        }
        if (checkedCount >= mMaxSelectCount) {
            for (view in uncheckedViews) {
                view.isEnabled = false
            }
        } else {
            for (view in uncheckedViews) {
                view.isEnabled = true
            }
        }
    }

    private fun notifyCheckedStateChange(id: Int, isChecked: Boolean) {
        if (mOnCheckedStateChangeListener != null) {
            mOnCheckedStateChangeListener!!.onCheckedStateChanged(this, id, isChecked)
        }
    }

    interface OnCheckedStateChangeListener {
        fun onCheckedStateChanged(
            group: MultiSelectToggleGroup?,
            checkedId: Int,
            isChecked: Boolean
        )
    }

}
