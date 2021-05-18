package com.example.myapplication

class Datasource {
    fun loadAffirmation(): List<Store>{
        return listOf<Store>(
            Store(R.string.item1,R.drawable.image1),
            Store(R.string.item2,R.drawable.image2),
            Store(R.string.item3,R.drawable.image3),
            Store(R.string.item4,R.drawable.image4),
        )
    }
}