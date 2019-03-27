package haiphat.com.bds.nghetuvan.utils.helper

import android.view.View
import android.view.ViewGroup
import java.util.ArrayList

/**
 * Created by DEV-01 on 12/27/2017.
 */
class TagViewHelpler {
    fun <Type> appendValue(tagId: Int, view: View, value: Type) {
        val obj = view.getTag(tagId)
        if (obj != null && obj is ArrayList<*>) {
            (obj as ArrayList<Type>).add(value)
        } else {
            val typeList = ArrayList<Type>()
            typeList.add(value)
            view.setTag(tagId, typeList)
        }
    }

    fun getViewsByTag(root: ViewGroup, tagId: Int): List<View> {
        val views = ArrayList<View>()
        val childCount = root.childCount
        for (i in 0 until childCount) {
            val child = root.getChildAt(i)
            if (child is ViewGroup) {
                views.addAll(getViewsByTag(child, tagId))
            }
            addViewWhenContainsTag(tagId, views, child)
        }
        return views
    }

    fun filterViewWithTag(tagId: Int, view: View): List<View> {
        val viewsWithTags = ArrayList<View>()
        addViewWhenContainsTag(tagId, viewsWithTags, view)
        return viewsWithTags
    }

    fun <ViewType : View> filterViewsWithTag(tagId: Int, views: List<ViewType>): List<View> {
        val viewsWithTags = ArrayList<View>()
        for (view in views) {
            addViewWhenContainsTag(tagId, viewsWithTags, view)
        }
        return viewsWithTags
    }

    private fun addViewWhenContainsTag(tagId: Int, views: MutableList<View>, view: View) {
        val tagValue = view.getTag(tagId)
        if (tagValue != null) {
            views.add(view)
        }
    }
}