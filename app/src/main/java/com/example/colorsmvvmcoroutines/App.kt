package com.example.colorsmvvmcoroutines

import android.app.Application
import com.example.colorsmvvmcoroutines.model.colors.InMemoryColorsRepository
import com.example.foundation.BaseApplication
import com.example.foundation.model.tasks.ThreadUtils
import com.example.foundation.model.tasks.dispatchers.MainThreadDispatcher
import com.example.foundation.model.tasks.factories.ExecutorServiceTasksFactory
import com.example.foundation.model.tasks.factories.HandlerThreadTasksFactory
import java.util.concurrent.Executors

/**
 * Here we store instances of model layer classes.
 */
class App : Application(), BaseApplication {

    private val singleThreadExecutorTasksFactory =
        ExecutorServiceTasksFactory(Executors.newSingleThreadExecutor())
    private val cachedThreadPoolExecutorTasksFactory =
        ExecutorServiceTasksFactory(Executors.newCachedThreadPool())
    private val handlerThreadTasksFactory = HandlerThreadTasksFactory()

    private val threadUtils = ThreadUtils.Default()
    private val dispatcher = MainThreadDispatcher()

    /**
     * Place your repositories here, now we have only 1 repository
     */

    override val singletonScopeDependencies: List<Any> = listOf(
        cachedThreadPoolExecutorTasksFactory, dispatcher,
        InMemoryColorsRepository(handlerThreadTasksFactory, threadUtils)
    )

}