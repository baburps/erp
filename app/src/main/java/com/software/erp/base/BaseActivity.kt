package com.software.erp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.software.erp.common.utils.LoadingDialogFragment

abstract class BaseActivity : AppCompatActivity(), ToolbarDelegate {

    private var dialogFragment: DialogFragment? = null

    abstract fun layoutId(): Int

    abstract fun onSetUp()

    abstract fun toolBar(): Toolbar?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        setSupportActionBar(toolBar())

        toolBar()?.let {
            it.setNavigationOnClickListener {
                onBackPressed()
            }
            /* toolBar()?.navigationIcon =
                 ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)*/
        }
        onSetUp()
    }

    override fun setTitle(title: String?) {
        title?.let {
            toolBar()?.let {
                it.title = title
            }
        }
    }

    internal fun showLoading() {
        dialogFragment = LoadingDialogFragment()
        dialogFragment?.show(supportFragmentManager, "DialogFragment")
    }

    internal fun hideLoading() {
        dialogFragment?.dismiss()
    }

}