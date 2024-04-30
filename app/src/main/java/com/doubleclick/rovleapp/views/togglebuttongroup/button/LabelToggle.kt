package com.doubleclick.restaurant.views.togglebuttongroup.button

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

/**
 * Created By Eslam Ghazy on 11/20/2022
 */
class LabelToggle @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    MarkerButton(context!!, attrs), ToggleButton {
    private val mAnimationDuration = DEFAULT_ANIMATION_DURATION.toLong()
    private var mCheckAnimation: Animation? = null
    private var mUncheckAnimation: Animation? = null
    private var mTextColorAnimator: ValueAnimator? = null
    override fun setMarkerColor(markerColor: Int) {
        super.setMarkerColor(markerColor)
        initBackground()
    }

    override fun setTextColor(color: Int) {
        super.setTextColor(color)
        initAnimation()
    }

    override fun setTextColor(colors: ColorStateList?) {
        super.setTextColor(colors)
        initAnimation()
    }

    private fun init() {
        initBackground()
        initText()
        initAnimation()
    }

    private fun initBackground() {
        val checked = GradientDrawable()
        checked.setColor(mMarkerColor)
        checked.cornerRadius = dpToPx(25f)
        checked.setStroke(1, mMarkerColor)
        mIvBg.setImageDrawable(checked)
        val unchecked = GradientDrawable()
        unchecked.setColor(ContextCompat.getColor(context, android.R.color.transparent))
        unchecked.cornerRadius = dpToPx(25f)
        unchecked.setStroke(dpToPx(1f).toInt(), mMarkerColor)
        mTvText.background = unchecked
    }

    private fun initText() {
        val padding = dpToPx(16f).toInt()
        mTvText.setPadding(padding, 0, padding, 0)
    }

    private fun initAnimation() {
        val defaultTextColor: Int = getDefaultTextColor()
        val checkedTextColor: Int = getCheckedTextColor()
        Log.v(
            LOG_TAG,
            "initAnimation(): defaultTextColor = $defaultTextColor, checkedTextColor = $checkedTextColor"
        )
        mTextColorAnimator = ValueAnimator.ofObject(
            ArgbEvaluator(), defaultTextColor, checkedTextColor
        )
        mTextColorAnimator!!.duration = mAnimationDuration
        mTextColorAnimator!!.addUpdateListener { valueAnimator ->
            mTvText.setTextColor(
                valueAnimator.animatedValue as Int
            )
        }
        mCheckAnimation = AlphaAnimation(0f, 1f)
        mCheckAnimation!!.duration = mAnimationDuration
        mCheckAnimation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                mTvText.setTextColor(checkedTextColor)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        mUncheckAnimation = AlphaAnimation(1f, 0f)
        mUncheckAnimation!!.duration = mAnimationDuration
        mUncheckAnimation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                mIvBg.visibility = INVISIBLE
                mTvText.setTextColor(defaultTextColor)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        if (checked) {
            mIvBg.visibility = VISIBLE
            mIvBg.startAnimation(mCheckAnimation)
            mTextColorAnimator!!.start()
        } else {
            mIvBg.visibility = VISIBLE
            mIvBg.startAnimation(mUncheckAnimation)
            mTextColorAnimator!!.reverse()
        }
    }

    companion object {
        private val LOG_TAG = LabelToggle::class.java.simpleName
        private const val DEFAULT_ANIMATION_DURATION = 150
    }

    init {
        init()
    }
}
