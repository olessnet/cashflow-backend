package app.theblu.cashflow.cs.batteries.common


object ListUtil {
    fun <T> splitIntoBatches(list: MutableList<T>, size: Int): List<List<T>> {
        val tempList = mutableListOf<List<T>>()
        var tempSubList = mutableListOf<T>()
        var counter = size;
        for (item in list) {
            tempSubList.add(item)
            counter--;
            if (counter == 0) {
                tempList.add(tempSubList)
                tempSubList = mutableListOf<T>()
                counter = size
            }
        }

        if (counter != 0 && tempSubList.isNotEmpty()) {
            tempList.add(tempSubList)
        }

        return tempList
    }
}