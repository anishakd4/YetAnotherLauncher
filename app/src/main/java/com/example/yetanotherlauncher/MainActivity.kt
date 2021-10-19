package com.example.yetanotherlauncher

import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yetanotherlauncher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var resolvedApplist: List<ResolveInfo>
    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        resolvedApplist = packageManager.queryIntentActivities(Intent(Intent.ACTION_MAIN,null)
                        .addCategory(Intent.CATEGORY_LAUNCHER),0)

        val appList = ArrayList<AppBlock>()

        for (ri in resolvedApplist) {
            if(ri.activityInfo.packageName!=this.packageName) {
                val app = AppBlock(
                        ri.loadLabel(packageManager).toString(),
                        ri.activityInfo.loadIcon(packageManager),
                        ri.activityInfo.packageName
                )
                appList.add(app)
            }
        }

        mainBinding.appList.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL )

        mainBinding.appList.adapter = Adapter(this).also {
            it.passAppList(appList)
        }
    }
}
