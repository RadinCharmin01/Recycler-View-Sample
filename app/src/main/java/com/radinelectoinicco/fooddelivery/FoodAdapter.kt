package com.radinelectoinicco.fooddelivery

import android.content.Context
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.shape.RoundedCornerTreatment
import com.radinelectoinicco.fooddelivery.databinding.FoodItemBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class FoodAdapter(private val data: ArrayList<Food> , private val dataEvents: DataEvents) : RecyclerView.Adapter<FoodAdapter.foodViewHolder>() {

    inner class foodViewHolder(val binding: FoodItemBinding) : RecyclerView.ViewHolder(binding.root)
    {

        fun bindData(position: Int){
            binding.itemTxtSubject.text = data[position].txtSubject
            binding.itemTxtCity.text = data[position].txtCity
            binding.itemTxtPrice.text = "$" + data[position].txtPrice + " vip"
            binding.itemTxtDistance.text = data[position].txtDistance + " miles from you"
            binding.itemRatingMain.rating = data[position].rating
            binding.itemTxtRating.text = "(" + data[position].numOfRating.toString() + " Ratings)"

            Glide.with(binding.root).load(data[position].urlImage)
                .transform(RoundedCornersTransformation(16,4))
                .into(binding.itemImgMain)

            itemView.setOnClickListener{
                dataEvents.onClick(data[adapterPosition] , adapterPosition)
            }


            itemView.setOnLongClickListener {
                dataEvents.onLongClick(data[adapterPosition] , adapterPosition)

                true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): foodViewHolder {
        val binding = FoodItemBinding.inflate( LayoutInflater.from(parent.context) , parent , false )
        return foodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: foodViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addFood(newFood: Food){
        //add food to list:
        data.add(0, newFood)
        notifyItemInserted(0)
    }


    fun removeFood(oldFood: Food , pos:Int) {
        //remove from list :
        data.remove(oldFood)
        notifyItemRemoved(pos)
    }

    fun updateFood(newFood:Food , pos: Int){
        //add data
        data[pos] = newFood
        notifyItemChanged(pos)
    }
    fun setData(newData:ArrayList<Food>) {
        //searchFood
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    interface DataEvents
    {
        fun onClick(food: Food , pos: Int)
        fun onLongClick(food: Food , pos: Int)
    }

}