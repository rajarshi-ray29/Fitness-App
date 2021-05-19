package com.example.myapplication

class Datasource {
    fun loaddata(): List<Store>{
        return listOf<Store>(
            Store(R.string.item1,R.drawable.image1,20.00F),
            Store(R.string.item2,R.drawable.image2,40.00F),
            Store(R.string.item3,R.drawable.image3, 35.0F),
            Store(R.string.item4,R.drawable.image4, 45.00F),
        )
    }
}