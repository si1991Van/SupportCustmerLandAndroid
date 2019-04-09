package haiphat.com.bds.nghetuvan.view

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import com.soundcloud.android.crop.Crop
import com.support.customer.lands.BaseApplication
import com.support.customer.lands.R
import com.support.customer.lands.databinding.ActivityBaseBinding
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.utills.IntentActionKeys
import com.support.customer.lands.utills.dialog.DialogChangeAvatar
import com.support.customer.lands.utills.extensions.fromUrl
import com.support.customer.lands.viewcontroller.auth.LoginActivity
import com.support.customer.lands.viewcontroller.profile.ProfileActivity
import java.io.File


abstract class BaseActivity : AppCompatActivity() {

    lateinit var baseActivityBinding: ActivityBaseBinding
    var imageUri: Uri? = null

    private val logoutBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (!this@BaseActivity.isFinishing) {
                val pIntent = Intent(this@BaseActivity, LoginActivity::class.java)
                startActivity(pIntent)
                isSelectorProfile.onClick = true
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(logoutBroadcastReceiver, IntentFilter(IntentActionKeys.FORCE_LOGOUT_ACTION))
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(logoutBroadcastReceiver)
        super.onStop()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        baseActivityBinding.clContent.addView(
            getContentView(),
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        baseActivityBinding.clContent.getChildAt(0).bringToFront()
        baseActivityBinding.rippleBack.setOnClickListener { this.onBackPressed() }
        baseActivityBinding.rippleRight.setOnRippleCompleteListener {
            if (isSelectorProfile.onClick) {
                startActivity(Intent(this@BaseActivity, ProfileActivity::class.java))
                isSelectorProfile.onClick = false
            }
        }
        baseActivityBinding.imgRight.fromUrl(url = UserServices?.userInfo?.avatar, placeHolder = R.drawable.ic_defaut_avatar)
    }


    fun setHeaderTitle(title: String?) {
        baseActivityBinding.tvTitle.text = title
    }

    fun setHeaderBackgroundColor(color: Int) {
        baseActivityBinding.clHeader.setBackgroundColor(color)
    }

    fun setHeaderVisibility(visibility: Int) {
        baseActivityBinding.clHeader.visibility = visibility
    }

    fun setrippleRightVisibility(visibility: Int) {
        baseActivityBinding.rippleRight.visibility = visibility
    }

    override fun onBackPressed() {
        this.getCurrentFragment()?.let {
            if (it.onBackPressed()) {
                isSelectorProfile.onClick = true
                return
            }
        }
        isSelectorProfile.onClick = true
        super.onBackPressed()
    }



    protected open fun getCurrentFragment(): BaseFragment? {
        return null
    }

    protected abstract fun getContentView(): View


    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imageUri = BaseApplication.context?.let {
            FileProvider.getUriForFile(
                it, it.packageName,
                File(Environment.getExternalStorageDirectory(), System.currentTimeMillis().toString() + ".jpg")
            )
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, isSelectorProfile.REQUEST_CAMERA)
    }

    fun openGallery() {
        val intent =
            Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, getString(R.string.change_image_select_file)),
            isSelectorProfile.SELECT_FILE
        )
    }


    fun beginCrop(source: Uri?) {
        val destination = Uri.fromFile(
            File(
                Environment.getExternalStorageDirectory(),
                System.currentTimeMillis().toString() + ".jpg"
            )
        )
        Crop.of(source, destination).asSquare().start(this@BaseActivity)
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun showDialogUpdateAvatar(){
        var dialogChangeImage = DialogChangeAvatar(this@BaseActivity, onSelectedCamera = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), IntentActionKeys.REQUEST_CAMERA_PERMISSION)
            }else{
                openCamera()
            }
        }, onSelectedGallery = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), IntentActionKeys.REQUEST_SELECT_FILE_PERMISSION)
            }else{
                openGallery()
            }
        })
        dialogChangeImage.setCanceledOnTouchOutside(false)
        dialogChangeImage.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogChangeImage.show()
    }

}

object isSelectorProfile{
    var onClick = true

    const val REQUEST_CAMERA = 1888
    const val SELECT_FILE = 1889
}