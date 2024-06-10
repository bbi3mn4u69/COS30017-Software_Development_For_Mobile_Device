package au.edu.swin.sdmd.e2_unitlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: UnitListAdapter
    // change the size of your list here
    private val mWordList: MutableList<MyUnit> = MutableList( 1000 ) {
        MyUnit("COS10${it.toString().padStart(3,'0')}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
        mAdapter = UnitListAdapter(mWordList)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            addItems()
        }

    }

    private fun addItems() {
        val wordListSize: Int = mWordList.size
        mWordList.add(wordListSize, MyUnit("+ COS10${wordListSize.toString().padStart(3,'0')}"))
        mRecyclerView.adapter?.notifyItemInserted(wordListSize)
        mRecyclerView.smoothScrollToPosition(wordListSize)
    }
}