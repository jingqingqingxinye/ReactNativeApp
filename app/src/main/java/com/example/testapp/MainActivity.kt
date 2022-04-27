package com.example.testapp


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.model.ModuleItem
import com.example.testapp.utils.ZipUtils
import com.google.gson.Gson
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import okhttp3.*
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var adapter: ListModuleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        val textView = findViewById<TextView>(R.id.load_data_from_server)
        textView.setOnClickListener {
            featchData()
        }
        initView()
    }

    /**
     * 初始化布局视图，默认数据为空
     */
    fun initView() {
        recyclerView = findViewById(R.id.list)
        recyclerView?.setLayoutManager(LinearLayoutManager(this))
        adapter = ListModuleAdapter(this, ArrayList())
        recyclerView?.setAdapter(adapter)
        adapter!!.setOnItemClickListener { bundle: ModuleItem.Bundle ->
            // 检查是否下载过，如果已经下载过则直接打开，暂不考虑各种版本问题
            val f = this@MainActivity.filesDir
                .absolutePath + "/" + bundle.name + "/" + bundle.name + ".bundle"
            val file = File(f)
            if (file.exists()) {
                goToRNActivity(bundle.name)
            } else {
                download(bundle.name)
            }
        }
    }

    /**
     * 跳转到RN的展示页面
     * @param bundleName
     */
    fun goToRNActivity(bundleName: String) {
        val starter = Intent(this@MainActivity, RNDynamicActivity::class.java)
        RNDynamicActivity.bundleName = bundleName
        this@MainActivity.startActivity(starter)
    }


    /**
     * 调用服务获取数据
     */
    fun featchData() {
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder().url(API.MODULES).method("GET", null).build()
        val call: Call = okHttpClient.newCall(request)
        call.enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                System.out.println("数据获取失败");
                System.out.println(e);
            }

            override fun onResponse(call: Call, response: Response) {

                if (!response.isSuccessful) {
                    Log.e("jingqingqing", "error")
                }

                val responseHeaders = response.headers
                for (i in 0 until responseHeaders.size) {
                    Log.e("jingqingqing -header", responseHeaders.name(i) + ": " + responseHeaders.value(i))
                }


                val data = response.body?.string() ?: ""
                if (data.isNotEmpty()) {
                    val moduleItem = Gson().fromJson(data, ModuleItem::class.java)
                    runOnUiThread { //刷新列表
                        adapter!!.clearModules()
                        adapter!!.addModules(moduleItem.data)
                    }
                }

            }

        })
    }

    /**
     * 下载对应的bundle
     *
     * @param bundleName
     */
    private fun download(bundleName: String) {
        println(API.DOWNLOAD.toString() + bundleName)
        FileDownloader.setup(this)
        FileDownloader.getImpl().create(API.DOWNLOAD.toString() + bundleName)
            .setPath(this.filesDir.absolutePath, true)
            .setListener(object : FileDownloadListener() {
                override fun started(task: BaseDownloadTask) {
                    super.started(task)
                }

                override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                override fun completed(task: BaseDownloadTask) {
                    try {
                        //下载之后解压，然后打开
                        ZipUtils.unzip(
                            this@MainActivity.filesDir.absolutePath + "/" + bundleName + ".zip",
                            this@MainActivity.filesDir.absolutePath
                        )
                        goToRNActivity(bundleName)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                override fun error(task: BaseDownloadTask, e: Throwable) {}
                override fun warn(task: BaseDownloadTask) {}
            }).start()
    }

}