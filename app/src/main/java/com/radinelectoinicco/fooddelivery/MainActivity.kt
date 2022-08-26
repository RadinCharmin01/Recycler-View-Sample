package com.radinelectoinicco.fooddelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.radinelectoinicco.fooddelivery.databinding.ActivityMainBinding
import com.radinelectoinicco.fooddelivery.databinding.DialogAddNewItemBinding
import com.radinelectoinicco.fooddelivery.databinding.DialogRemoveItemBinding
import com.radinelectoinicco.fooddelivery.databinding.DialogUpdateItemBinding

class MainActivity : AppCompatActivity(), FoodAdapter.DataEvents {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: FoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val foodList = arrayListOf(
            Food(
                "Hamburger",
                "15",
                "3",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                20,
                4.5f
            ),
            Food(
                "Grilled fish",
                "20",
                "2.1",
                "Tehran, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                10,
                4f
            ),
            Food(
                "Lasania",
                "40",
                "1.4",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                30,
                2f
            ),
            Food(
                "pizza",
                "10",
                "2.5",
                "Zahedan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                80,
                1.5f
            ),
            Food(
                "Sushi",
                "20",
                "3.2",
                "Mashhad, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                200,
                3f
            ),
            Food(
                "Roasted Fish",
                "40",
                "3.7",
                "Jolfa, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                50,
                3.5f
            ),
            Food(
                "Fried chicken",
                "70",
                "3.5",
                "NewYork, USA",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                70,
                2.5f
            ),
            Food(
                "Vegetable salad",
                "12",
                "3.6",
                "Berlin, Germany",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                40,
                4.5f
            ),
            Food(
                "Grilled chicken",
                "10",
                "3.7",
                "Beijing, China",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                15,
                5f
            ),
            Food(
                "Baryooni",
                "16",
                "10",
                "Ilam, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                28,
                4.5f
            ),
            Food(
                "Ghorme Sabzi",
                "11.5",
                "7.5",
                "Karaj, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                27,
                5f
            ),
            Food(
                "Rice",
                "12.5",
                "2.4",
                "Shiraz, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                35,
                2.5f
            ),
        )
        myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food>, this)
        binding.recyclerViewFood.adapter = myAdapter
        binding.recyclerViewFood.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val dialogView = DialogAddNewItemBinding.inflate(layoutInflater)
            dialog.setView(dialogView.root)
            dialog.setCancelable(true)
            dialog.show()

            dialogView.dialogAddNewBtnAdd.setOnClickListener {
                if (dialogView.dialogTxtFoodCity.length() > 0 && dialogView.dialogTxtFoodDistance.length() > 0 && dialogView.dialogTxtFoodName.length() > 0 && dialogView.dialogTxtFoodPrice.length() > 0) {
                    val txtFoodCity = dialogView.dialogTxtFoodCity.text.toString()
                    val txtFoodDistance = dialogView.dialogTxtFoodDistance.text.toString()
                    val txtFoodName = dialogView.dialogTxtFoodName.text.toString()
                    val txtFoodPrice = dialogView.dialogTxtFoodPrice.text.toString()

                    val numOfRating: Int = (1..150).random()
                    val urlPic: Int = (0 until 12).random()
                    val rating: Float = (0..5).random().toFloat()

                    val newFood = Food(
                        txtFoodName,
                        txtFoodPrice,
                        txtFoodDistance,
                        txtFoodCity,
                        foodList[urlPic].urlImage,
                        numOfRating,
                        rating
                    )
                    myAdapter.addFood(newFood)
                    dialog.dismiss()
                    binding.recyclerViewFood.smoothScrollToPosition(0)
                } else {
                    Toast.makeText(this, "please fill blanks", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.srchFood.addTextChangedListener { editText->
            if(editText!!.isNotEmpty()){
                val cloneList = foodList.clone() as ArrayList<Food>
                val foodFilter = cloneList.filter { foodItem->
                    foodItem.txtSubject.contains(editText)
                }

                myAdapter.setData(ArrayList(foodFilter))

            }
            else{
                //do nothing
                myAdapter.setData(foodList)
            }
        }


    }
    override fun onClick(food: Food, pos: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val dialogView = DialogUpdateItemBinding.inflate(layoutInflater)
        dialog.setView(dialogView.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogView.dialogTxtFoodName.setText(food.txtSubject)
        dialogView.dialogTxtFoodCity.setText(food.txtCity)
        dialogView.dialogTxtFoodDistance.setText(food.txtDistance)
        dialogView.dialogTxtFoodPrice.setText(food.txtPrice)
        dialogView.dialogUpdateBtndone.setOnClickListener {
            if (dialogView.dialogTxtFoodCity.length() > 0 && dialogView.dialogTxtFoodDistance.length() > 0 && dialogView.dialogTxtFoodName.length() > 0 && dialogView.dialogTxtFoodPrice.length() > 0) {

                val txtFoodCity = dialogView.dialogTxtFoodCity.text.toString()
                val txtFoodDistance = dialogView.dialogTxtFoodDistance.text.toString()
                val txtFoodName = dialogView.dialogTxtFoodName.text.toString()
                val txtFoodPrice = dialogView.dialogTxtFoodPrice.text.toString()

                val newFood = Food(
                    txtFoodName,
                    txtFoodPrice,
                    txtFoodDistance,
                    txtFoodCity,
                    food.urlImage,
                    food.numOfRating,
                    food.rating
                )

                myAdapter.updateFood(newFood, pos)

                dialog.dismiss()

                binding.recyclerViewFood.smoothScrollToPosition(pos)

            }
        }
        dialogView.dialogUpdateBtncancel.setOnClickListener {
            dialog.dismiss()
        }

    }

    override fun onLongClick(food: Food, pos: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val dialogView = DialogRemoveItemBinding.inflate(layoutInflater)
        dialog.setView(dialogView.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogView.dialogRemoveBtnSure.setOnClickListener {
            myAdapter.removeFood(food, pos)
            dialog.dismiss()
            binding.recyclerViewFood.smoothScrollToPosition(0)
        }
        dialogView.dialogRemoveBtnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}


