import java.io.File
import java.util.Arrays

fun main(args: Array<String>) {

    File("in").listFiles().forEach {
        val startTime = System.currentTimeMillis()

        val inputFilename = it.toString()
        val inputFileData = File(inputFilename).useLines { it.toList() }

        val employeesCount: Int = inputFileData[0].toInt()
        val employeesCountWithBoss = employeesCount+1
        var managers: IntArray = IntArray(employeesCountWithBoss+1)
        var employees: IntArray = IntArray(employeesCountWithBoss+1)
        var salaries: IntArray = IntArray(employeesCountWithBoss)
        var levels: IntArray = IntArray(employeesCountWithBoss+1)
        var queue: IntArray = IntArray(employeesCountWithBoss+1)
        var managerEmployeesCount: IntArray = IntArray(employeesCountWithBoss+1)
        var usedSalaries: IntArray = IntArray(employeesCountWithBoss+1)
        var startIndex: Int = 0
        var endIndex: Int = 0

        for (i in 1..employeesCount) {
            val rowData = inputFileData[i].split(" ")
            managers[i] = rowData[0].toInt()
            salaries[i] = rowData[1].toInt()

            if (managers[i] === i) {
                salaries[i] = employeesCount
            }

            if (salaries[i] > 0) {
                managers[i] = employeesCountWithBoss
            }
        }

        for (i in 1 until employeesCountWithBoss) {
            ++levels[managers[i]]
        }

        for (i in 1 until employeesCountWithBoss) {
            if (levels[i] == 0) {
                queue[endIndex++] = i;
            }
        }

        while (startIndex < endIndex) {
            val currentIndex = queue[startIndex++]
            val manager = managers[currentIndex]
            if (salaries[currentIndex] === 0) {
                if (--levels[manager] == 0) {
                    queue[endIndex++] = manager
                }
                managerEmployeesCount[manager] += managerEmployeesCount[currentIndex] + 1
            }
        }

        for (i in 1 until employeesCountWithBoss) {
            if (salaries[i] > 0) {
                usedSalaries[salaries[i]] = i
            } else if (employees[managers[i]] == 0) {
                employees[managers[i]] = i
            } else {
                employees[managers[i]] = -1
            }
        }

        var i = 0
        var freePlacesCount = 0
        var availablePlacesCount = 0
        while (i < employeesCount) {
            while (i < employeesCount && usedSalaries[i + 1] === 0) {
                ++i
                ++freePlacesCount
                ++availablePlacesCount
            }
            while (i < employeesCount && usedSalaries[i + 1] !== 0) {
                ++i
                var akt = usedSalaries[i]
                var l = i
                freePlacesCount -= managerEmployeesCount[akt]
                if (freePlacesCount == 0) {
                    while (availablePlacesCount-- > 0 && employees[akt] > 0) {
                        while (usedSalaries[l] > 0) --l
                        akt = employees[akt]
                        salaries[akt] = l
                        usedSalaries[l] = akt
                    }
                    availablePlacesCount = 0
                }
                if (managerEmployeesCount[akt] !== 0)
                    availablePlacesCount = 0
            }
        }

        val endTime = System.currentTimeMillis()
        val outputFileData = File(inputFilename.replace("in", "out")).useLines { it.toList() }
        var isResultCorrect = true
        for (i in 1 until salaries.size) {
            if (salaries[i] != outputFileData[i-1].toInt()) {
                isResultCorrect = false
            }
        }
        if (isResultCorrect === true && (endTime-startTime)/1000 < 5) {
            println("$inputFilename test passed!")
            if (salaries.size <= 50) {
                println("Result:")
                println(Arrays.toString(salaries))
            }

        } else {
            if ((endTime-startTime)/1000 >= 5) {
                println("$inputFilename 5 seconds time limit exceed!")
            }
            if (isResultCorrect == false) {
                println("$inputFilename result not correct!")
                println("Actual:")
                println(Arrays.toString(salaries))
                println("Expected:")
                println(outputFileData)
            }
            return
        }

    }
}