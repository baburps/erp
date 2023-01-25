package com.software.erp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.software.erp.common.utils.LoadingDialogFragment

abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    internal var binding: DB? = null
    private var dialogFragment: DialogFragment? = null

    internal var TAG: String? = null

    abstract fun onSetUp()

    abstract fun layoutId(): Int

    abstract fun getViewTag(): String

//    abstract fun setOptionMenu(): Boolean

    //Initialize Loader
    private val loadingDialogFragment by lazy { LoadingDialogFragment() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val view = inflater.inflate(layoutId(), container, false)
        /*  //To update toolbar menu from fragment
          setHasOptionsMenu(setOptionMenu())*/
        TAG = getViewTag()

        binding = DataBindingUtil.inflate(
            inflater, layoutId(), container, false
        )
        binding?.lifecycleOwner = this

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.lifecycleOwner = this
        onSetUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    internal fun setTitle(title: String?) {
        title?.let {
            (activity as ToolbarDelegate).setTitle(title)
        }
    }

    internal fun showLoading() {
        dialogFragment = LoadingDialogFragment()
        dialogFragment?.show(activity?.supportFragmentManager!!, "DialogFragment")
    }

    internal fun hideLoading() {
        dialogFragment?.dismiss()
    }

    internal fun showToast(message: String?) {
        message?.let {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }
}


