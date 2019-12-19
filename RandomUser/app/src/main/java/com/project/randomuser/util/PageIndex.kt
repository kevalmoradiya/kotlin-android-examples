package com.project.randomuser.util

class PageIndex(){
    var index:Int


    init {
        index=1
    }

    fun increment():Int{
         index+=1
        return index
    }
    fun decrement():Int{
        if(index>1)
        {index-=1}
        return index

    }


}