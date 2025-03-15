package com.akmanaev.filmstrip.expandablerecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akmanaev.filmstrip.R
import com.akmanaev.filmstrip.dto.FilmDetails
import com.bumptech.glide.Glide

interface OnItemClickListener {
    fun onItemClicked(parentData: ParentData)
}

class RecycleAdapter(
    private val list: MutableList<ParentData>,
    private val itemActionListener: ItemActionListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == Constants.PARENT) {
            val rowView: View =
                LayoutInflater.from(parent.context).inflate(R.layout.parent_row, parent, false)
            GroupViewHolder(rowView)
        } else {
            val rowView: View =
                LayoutInflater.from(parent.context).inflate(R.layout.child_row, parent, false)
            ChildViewHolder(rowView)
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataList = list[position]
        if (dataList.type == Constants.PARENT) {
            holder as GroupViewHolder
            holder.apply {
                parentTV?.text = dataList.parentTitle
                downIV?.setOnClickListener {
                    expandOrCollapseParentItem(dataList, position)
                }
            }
        } else {
            holder as ChildViewHolder
            holder.apply {
                val singleFilm = dataList.subList.first()
                title?.text = singleFilm.title
                Glide.with(holder.itemView.context)
                    .load(singleFilm.imageUrl)
                    .fitCenter()
                    .into(image!!)
                itemView.setOnClickListener {
                    itemActionListener.onItemClicked(singleFilm.id)
                }
                itemView.setOnLongClickListener {
                    itemActionListener.onItemLongClicked(singleFilm.id)
                    return@setOnLongClickListener true
                }
            }
        }
    }

    private fun expandOrCollapseParentItem(singleBoarding: ParentData, position: Int) {

        if (singleBoarding.isExpanded) {
            collapseParentRow(position)
        } else {
            expandParentRow(position)
        }
    }

    private fun expandParentRow(position: Int) {
        val currentBoardingRow = list[position]
        val services = currentBoardingRow.subList
        currentBoardingRow.isExpanded = true
        var nextPosition = position
        if (currentBoardingRow.type == Constants.PARENT) {
            services.forEach { service ->
                val parentModel = ParentData()
                parentModel.type = Constants.CHILD
                val subList: ArrayList<FilmDetails> = ArrayList()
                subList.add(service)
                parentModel.subList = subList
                list.add(++nextPosition, parentModel)
            }
            notifyDataSetChanged()
        }
    }

    private fun collapseParentRow(position: Int) {
        val currentBoardingRow = list[position]
        val services = currentBoardingRow.subList
        list[position].isExpanded = false
        if (list[position].type == Constants.PARENT) {
            services.forEach { _ ->
                list.removeAt(position + 1)
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int = list[position].type

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class GroupViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val parentTV: TextView? = row.findViewById(R.id.parent_Title)
        val downIV: ImageView? = row.findViewById(R.id.down_iv)
    }

    class ChildViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val title: TextView? = row.findViewById(R.id.textTitle)
        val image: ImageView? = row.findViewById(R.id.imageTitle)
    }
}