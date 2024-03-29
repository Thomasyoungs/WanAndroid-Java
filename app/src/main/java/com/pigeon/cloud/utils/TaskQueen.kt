package com.pigeon.cloud.utils

/**
 * @author yangzhikuan
 * @date 2020/2/15
 * 
 */
class TaskQueen {
    private val tasks: MutableList<Task> = mutableListOf()

    fun append(task: Task) {
        if (tasks.isEmpty()) {
            tasks.add(task)
            run(task)
        } else {
            var index = tasks.size
            for (i in tasks.size - 1 downTo 0) {
                val t = tasks[i]
                if (task.level <= t.level) {
                    break
                }
                index = i
            }
            tasks.add(index, task)
        }
    }

    private fun next() {
        if (tasks.isEmpty()) return
        run(tasks[0])
    }

    private fun run(task: Task) {
        task.onFinished = {
            tasks.remove(task)
            next()
        }
        task.run()
    }

    abstract class Task(
            val level: Int = 0
    ) {
        internal var onFinished: (() -> Unit)? = null

        abstract fun run()

        fun complete() {
            onFinished?.invoke()
        }
    }
}