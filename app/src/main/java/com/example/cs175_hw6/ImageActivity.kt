package com.example.cs175_hw6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import java.util.ArrayList
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class ImageActivity : AppCompatActivity() {
    private var pages: MutableList<View> = ArrayList()
    private var viewPager:ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        addImage("https://t7.baidu.com/it/u=4162611394,4275913936&fm=193&f=GIF")
        addImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fqqpublic.qpic.cn%2Fqq_public%2F0%2F0-2829561120-62CE803B4C8EE967747CCC07B5B32789%2F0%3Ffmt%3Djpg%26size%3D69%26h%3D900%26w%3D900%26ppv%3D1.jpg&refer=http%3A%2F%2Fqqpublic.qpic.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641261863&t=4ad2d3c7ba78582790f5bb64ad4dc8e0",)
        addImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2Fv2-888d9e205998576d3974985cf5bfe940_b.gif&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641262849&t=afe7df2b2828672f513ccaa06cc5c346")
        val viewAdapter = ViewAdapter()
        viewAdapter.setDatas(pages)
        viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager!!.adapter = viewAdapter
    }

    private fun addImage(imageURL: String) {
        val imageHolder: ImageView = layoutInflater.inflate(R.layout.activity_image_item, null).findViewById<ImageView>(R.id.image_holder)
        Glide.with(this)
            .load(imageURL)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .error(R.drawable.error)
            //.override(100, 100)
            .into(imageHolder)

        pages.add(imageHolder)
    }
}