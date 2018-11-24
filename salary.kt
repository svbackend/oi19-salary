import java.io.File

fun main(args: Array<String>) {

    File("in").listFiles().forEach {
        val startTime = System.currentTimeMillis()

        val inputFilename = it.toString()
        println("---")
        val inputFileData = File(inputFilename).useLines { it.toList() }

        val employeesCount: Int = inputFileData[0].toInt()
        var managers: IntArray = IntArray(employeesCount)
        var salaries: IntArray = IntArray(employeesCount)

        for (i in 0 until employeesCount) {
            val rowData = inputFileData[i+1].split(" ")
            managers[i] = rowData[0].toInt()
            salaries[i] = rowData[1].toInt()

            if (managers[i] === i) salaries[i] = employeesCount
            if (salaries[i] > 0) managers[i] = employeesCount + 1
        }

        println("Managers:")
        println(managers.toString())

        println("Salaries:")
        println(salaries.toString())

        val endTime = System.currentTimeMillis()
        val outputFileData = File(inputFilename.replace("in", "out")).useLines { it.toList() }
        //val outputSteps = outputFileData[0].toLong()

    }
}