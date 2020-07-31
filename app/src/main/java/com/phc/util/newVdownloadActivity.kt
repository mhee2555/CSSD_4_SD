package com.phc.cssd.util

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.downloadapk.util.checkSelfPermissionCompat
import com.downloadapk.util.requestPermissionsCompat
import com.downloadapk.util.shouldShowRequestPermissionRationaleCompat
import com.downloadapk.util.showSnackbar
import com.phc.cssd.Login
import com.phc.cssd.MainActivity
import com.phc.cssd.R
import com.phc.cssd.url.getUrl
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import javax.security.auth.callback.Callback

class newVdownloadActivity : AppCompatActivity() {

    val client = OkHttpClient()
    var apkUrl=""
    companion object {
        const val PERMISSION_REQUEST_STORAGE = 0
    }

    lateinit var downloadController: DownloadController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        run(getUrl.getURL()+"APK_Version/get_version.php")
//        if(getUrl.Server!=1){
//            apkUrl = "http://poseintelligence.dyndns.biz:8181/chevron/PoseintChevron.apk"
//
//            run("http://poseintelligence.dyndns.biz:8181/chevron/get_version.php")
//        }else{
//            apkUrl = "http://poseintelligence.dyndns.biz:8181/chevron/PoseintChevrontest.apk"
//            run("http://poseintelligence.dyndns.biz:8181/chevron/get_versiontest.php")
//        }
    }

    override fun onRequestPermissionsResult(
                requestCode: Int,
                permissions: Array<out String>,
                grantResults: IntArray
        ) {
            if (requestCode == PERMISSION_REQUEST_STORAGE) {
                // Request for camera permission.
                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // start downloading
                    downloadController.enqueueDownload()
                } else {
                    // Permission request was denied.
                    mainLayout.showSnackbar(R.string.storage_permission_denied, Snackbar.LENGTH_SHORT)
                }
            }
        }


        private fun checkStoragePermission() {
            downloadController = DownloadController(this, apkUrl)
            // Check if the storage permission has been granted
            if (checkSelfPermissionCompat(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED
            ) {
                // start downloading
                downloadController.enqueueDownload()
            } else {
                // Permission is missing and must be requested.
                requestStoragePermission()
            }
        }

        private fun requestStoragePermission() {

            if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                mainLayout.showSnackbar(
                        R.string.storage_access_required,
                        Snackbar.LENGTH_INDEFINITE, R.string.ok
                ) {
                    requestPermissionsCompat(
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            PERMISSION_REQUEST_STORAGE
                    )
                }

            } else {
                requestPermissionsCompat(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSION_REQUEST_STORAGE
                )
            }
        }

    private fun gotoInitAc() {
        val intent = Intent(this@newVdownloadActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    fun run(url: String) {
        Log.d("ttestoy", url)
        val request = Request.Builder()
                .url(url)
                .build()

        client.newCall(request).enqueue(object : Callback, okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                var str_response = response.body()!!.string()
                //creating json object
                val json_contact: JSONObject = JSONObject(str_response)
                //creating json array
                var jsonarray_info: JSONArray = json_contact.getJSONArray("result")

                var json_objectdetail:JSONObject=jsonarray_info.getJSONObject(0)

                var app_version=json_objectdetail.getInt("app_version")
                var app_version_x=json_objectdetail.getInt("app_version_x")
                var version_info=json_objectdetail.getString("version_info")
                apkUrl=json_objectdetail.getString("app_url")
                Log.d("ttestoy", "app_version = "+app_version)
                runOnUiThread {
                    if(app_version<=getUrl.appversion){
                        gotoInitAc()
                    }else{
                        val builder = AlertDialog.Builder(this@newVdownloadActivity)
                        with(builder) {
                            setTitle("New Update Version "+app_version_x+"."+app_version)
                            setMessage("Update information"+version_info+"\nDonwload and Install app")
                            setPositiveButton("UPDATE"){dialog, which ->checkStoragePermission()}
                            setNegativeButton("SKIP" ){dialog, which ->gotoInitAc()}
                            setIcon(resources.getDrawable(R.drawable.pose_favicon_5x, theme))
                        }
                        val alertDialog = builder.create()
                        alertDialog.show()
                    }
                }
            }

        })
    }


}
