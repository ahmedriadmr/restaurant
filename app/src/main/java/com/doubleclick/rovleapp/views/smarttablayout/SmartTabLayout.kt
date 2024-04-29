package com.doubleclick.rovleapp.views.smarttablayout

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.doubleclick.rovleapp.R
import kotlin.math.roundToInt

/**
 * Created By Eslam Ghazy on 11/20/2022
 */
class SmartTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    HorizontalScrollView(context, attrs, defStyle) {
    val tabStrip: SmartTabStrip
    private val titleOffset: Int
    private val tabViewBackgroundResId: Int
    private val tabViewTextAllCaps: Boolean
    private var tabViewTextColors: ColorStateList
    private val tabViewTextSize: Float
    private val tabViewTextHorizontalPadding: Int
    private val tabViewTextMinWidth: Int
    private var viewPager: ViewPager? = null
    private var onScrollChangeListener: OnScrollChangeListener? = null
    private var tabProvider: TabProvider? = null
    private val internalTabClickListener: InternalTabClickListener?
    private var onTabClickListener: OnTabClickListener? = null
    private var distributeEvenly: Boolean
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (onScrollChangeListener != null) {
            onScrollChangeListener!!.onScrollChanged(l, oldl)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (tabStrip.isIndicatorAlwaysInCenter() && tabStrip.childCount > 0) {
            val firstTab: View = tabStrip.getChildAt(0)
            val lastTab: View = tabStrip.getChildAt(tabStrip.childCount - 1)
            val start: Int =
                (w - Utils.getMeasuredWidth(firstTab)) / 2 - Utils.getMarginStart(firstTab)
            val end: Int = (w - Utils.getMeasuredWidth(lastTab)) / 2 - Utils.getMarginEnd(lastTab)
            tabStrip.minimumWidth = tabStrip.measuredWidth
            ViewCompat.setPaddingRelative(this, start, paddingTop, end, paddingBottom)
            clipToPadding = false
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        // Ensure first scroll
        if (changed && viewPager != null) {
            scrollToTab(viewPager!!.currentItem, 0f)
        }
    }


    /**
     * Set the custom layout to be inflated for the tab views.
     *
     * @param layoutResId Layout id to be inflated
     * @param textViewId id of the [TextView] in the inflated view
     */
    private fun setCustomTabView(layoutResId: Int, textViewId: Int) {
        tabProvider = SimpleTabProvider(context, layoutResId, textViewId)
    }

    private fun scrollToTab(tabIndex: Int, positionOffset: Float) {
        val tabStripChildCount: Int = tabStrip.childCount
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return
        }
        val isLayoutRtl: Boolean = Utils.isLayoutRtl(this)
        val selectedTab: View = tabStrip.getChildAt(tabIndex)
        val widthPlusMargin: Int =
            Utils.getWidth(selectedTab) + Utils.getMarginHorizontally(selectedTab)
        var extraOffset = (positionOffset * widthPlusMargin).toInt()
        if (tabStrip.isIndicatorAlwaysInCenter()) {
            if (0f < positionOffset && positionOffset < 1f) {
                val nextTab: View = tabStrip.getChildAt(tabIndex + 1)
                val selectHalfWidth: Int =
                    Utils.getWidth(selectedTab) / 2 + Utils.getMarginEnd(selectedTab)
                val nextHalfWidth: Int = Utils.getWidth(nextTab) / 2 + Utils.getMarginStart(nextTab)
                extraOffset = (positionOffset * (selectHalfWidth + nextHalfWidth)).roundToInt()
            }
            val firstTab: View = tabStrip.getChildAt(0)
            var x: Int
            if (isLayoutRtl) {
                val first: Int = Utils.getWidth(firstTab) + Utils.getMarginEnd(firstTab)
                val selected: Int = Utils.getWidth(selectedTab) + Utils.getMarginEnd(selectedTab)
                x = Utils.getEnd(selectedTab) - Utils.getMarginEnd(selectedTab) - extraOffset
                x -= (first - selected) / 2
            } else {
                val first: Int = Utils.getWidth(firstTab) + Utils.getMarginStart(firstTab)
                val selected: Int = Utils.getWidth(selectedTab) + Utils.getMarginStart(selectedTab)
                x = Utils.getStart(selectedTab) - Utils.getMarginStart(selectedTab) + extraOffset
                x -= (first - selected) / 2
            }
            scrollTo(x, 0)
            return
        }
        var x: Int
        if (titleOffset == TITLE_OFFSET_AUTO_CENTER) {
            if (0f < positionOffset && positionOffset < 1f) {
                val nextTab: View = tabStrip.getChildAt(tabIndex + 1)
                val selectHalfWidth: Int =
                    Utils.getWidth(selectedTab) / 2 + Utils.getMarginEnd(selectedTab)
                val nextHalfWidth: Int = Utils.getWidth(nextTab) / 2 + Utils.getMarginStart(nextTab)
                extraOffset = (positionOffset * (selectHalfWidth + nextHalfWidth)).roundToInt()
            }
            if (isLayoutRtl) {
                x = -Utils.getWidthWithMargin(selectedTab) / 2 + width / 2
                x -= Utils.getPaddingStart(this)
            } else {
                x = Utils.getWidthWithMargin(selectedTab) / 2 - width / 2
                x += Utils.getPaddingStart(this)
            }
        } else {
            x = if (isLayoutRtl) {
                if (tabIndex > 0 || positionOffset > 0) titleOffset else 0
            } else {
                if (tabIndex > 0 || positionOffset > 0) -titleOffset else 0
            }
        }
        val start: Int = Utils.getStart(selectedTab)
        val startMargin: Int = Utils.getMarginStart(selectedTab)
        x += if (isLayoutRtl) {
            start + startMargin - extraOffset - width + Utils.getPaddingHorizontally(this)
        } else {
            start - startMargin + extraOffset
        }
        scrollTo(x, 0)
    }

    /**
     * Allows complete control over the colors drawn in the tab layout. Set with
     * [.setCustomTabColorizer].
     */
    interface TabColorizer {
        /**
         * @return return the color of the indicator used when `position` is selected.
         */
        fun getIndicatorColor(position: Int): Int

        /**
         * @return return the color of the divider drawn to the right of `position`.
         */
        fun getDividerColor(position: Int): Int
    }

    /**
     * Interface definition for a callback to be invoked when the scroll position of a view changes.
     */
    interface OnScrollChangeListener {
        /**
         * Called when the scroll position of a view changes.
         *
         * @param scrollX Current horizontal scroll origin.
         * @param oldScrollX Previous horizontal scroll origin.
         */
        fun onScrollChanged(scrollX: Int, oldScrollX: Int)
    }

    /**
     * Interface definition for a callback to be invoked when a tab is clicked.
     */
    interface OnTabClickListener {
        /**
         * Called when a tab is clicked.
         *
         * @param position tab's position
         */
        fun onTabClicked(position: Int)
    }

    /**
     * Create the custom tabs in the tab layout. Set with
     * [.setCustomTabView]
     */
    interface TabProvider {
        /**
         * @return Return the View of `position` for the Tabs
         */
        fun createTabView(container: ViewGroup?, position: Int, adapter: PagerAdapter?): View?
    }

    class SimpleTabProvider(context: Context, layoutResId: Int, textViewId: Int) : TabProvider {
        private val inflater: LayoutInflater
        private val tabViewLayoutId: Int
        private val tabViewTextViewId: Int

        override fun createTabView(
            container: ViewGroup?,
            position: Int,
            adapter: PagerAdapter?
        ): View? {
            var tabView: View? = null
            var tabTitleView: TextView? = null
            if (tabViewLayoutId != NO_ID) {
                tabView = inflater.inflate(tabViewLayoutId, container, false)
            }
            if (tabViewTextViewId != NO_ID && tabView != null) {
                tabTitleView = tabView.findViewById<View>(tabViewTextViewId) as TextView
            }
            if (tabTitleView == null && TextView::class.java.isInstance(tabView)) {
                tabTitleView = tabView as TextView?
            }
            if (tabTitleView != null) {
                tabTitleView.text = adapter!!.getPageTitle(position)
            }
            return tabView
        }

        init {
            inflater = LayoutInflater.from(context)
            tabViewLayoutId = layoutResId
            tabViewTextViewId = textViewId
        }
    }

    private inner class InternalTabClickListener : OnClickListener {
        override fun onClick(v: View) {
            for (i in 0 until tabStrip.childCount) {
                if (v === tabStrip.getChildAt(i)) {
                    if (onTabClickListener != null) {
                        onTabClickListener!!.onTabClicked(i)
                    }
                    viewPager!!.currentItem = i
                    return
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_DISTRIBUTE_EVENLY = false
        private const val TITLE_OFFSET_DIPS = 24
        private const val TITLE_OFFSET_AUTO_CENTER = -1
        private const val TAB_VIEW_PADDING_DIPS = 16
        private const val TAB_VIEW_TEXT_ALL_CAPS = true
        private const val TAB_VIEW_TEXT_SIZE_SP = 12
        private const val TAB_VIEW_TEXT_MIN_WIDTH = 0
        private const val TAB_CLICKABLE = true
    }

    init {

        // Disable the Scroll Bar
        isHorizontalScrollBarEnabled = false
        val dm = resources.displayMetrics
        val density = dm.density
        var tabBackgroundResId = NO_ID
        var textAllCaps = TAB_VIEW_TEXT_ALL_CAPS
        val textColors: ColorStateList
        var textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP.toFloat(), dm
        )
        var textHorizontalPadding = (TAB_VIEW_PADDING_DIPS * density).toInt()
        var textMinWidth = (TAB_VIEW_TEXT_MIN_WIDTH * density).toInt()
        var distributeEvenly = DEFAULT_DISTRIBUTE_EVENLY
        var customTabLayoutId = NO_ID
        var customTabTextViewId = NO_ID
        var clickable = TAB_CLICKABLE
        var titleOffset = (TITLE_OFFSET_DIPS * density).toInt()
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.stl_SmartTabLayout, defStyle, 0
        )
        tabBackgroundResId = a.getResourceId(
            R.styleable.stl_SmartTabLayout_stl_defaultTabBackground, tabBackgroundResId
        )
        textAllCaps = a.getBoolean(
            R.styleable.stl_SmartTabLayout_stl_defaultTabTextAllCaps, textAllCaps
        )
        textColors = a.getColorStateList(R.styleable.stl_SmartTabLayout_stl_defaultTabTextColor)!!
        textSize = a.getDimension(
            R.styleable.stl_SmartTabLayout_stl_defaultTabTextSize, textSize
        )
        textHorizontalPadding = a.getDimensionPixelSize(
            R.styleable.stl_SmartTabLayout_stl_defaultTabTextHorizontalPadding,
            textHorizontalPadding
        )
        textMinWidth = a.getDimensionPixelSize(
            R.styleable.stl_SmartTabLayout_stl_defaultTabTextMinWidth, textMinWidth
        )
        customTabLayoutId = a.getResourceId(
            R.styleable.stl_SmartTabLayout_stl_customTabTextLayoutId, customTabLayoutId
        )
        customTabTextViewId = a.getResourceId(
            R.styleable.stl_SmartTabLayout_stl_customTabTextViewId, customTabTextViewId
        )
        distributeEvenly = a.getBoolean(
            R.styleable.stl_SmartTabLayout_stl_distributeEvenly, distributeEvenly
        )
        clickable = a.getBoolean(
            R.styleable.stl_SmartTabLayout_stl_clickable, clickable
        )
        titleOffset = a.getLayoutDimension(
            R.styleable.stl_SmartTabLayout_stl_titleOffset, titleOffset
        )
        a.recycle()
        this.titleOffset = titleOffset
        tabViewBackgroundResId = tabBackgroundResId
        tabViewTextAllCaps = textAllCaps
        tabViewTextColors = textColors
        tabViewTextSize = textSize
        tabViewTextHorizontalPadding = textHorizontalPadding
        tabViewTextMinWidth = textMinWidth
        internalTabClickListener = if (clickable) InternalTabClickListener() else null
        this.distributeEvenly = distributeEvenly
        if (customTabLayoutId != NO_ID) {
            setCustomTabView(customTabLayoutId, customTabTextViewId)
        }
        tabStrip = SmartTabStrip(context, attrs)
        if (distributeEvenly && tabStrip.isIndicatorAlwaysInCenter()) {
            throw UnsupportedOperationException(
                "'distributeEvenly' and 'indicatorAlwaysInCenter' both use does not support"
            )
        }

        // Make sure that the Tab Strips fills this View
        isFillViewport = !tabStrip.isIndicatorAlwaysInCenter()
        addView(tabStrip, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }
}
