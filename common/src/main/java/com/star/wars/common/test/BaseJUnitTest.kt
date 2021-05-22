package com.star.wars.common.test

import com.star.wars.common.test.MonkInstantTaskExecutorRule
import com.star.wars.common.test.TrampolineSchedulerRule
import org.junit.After
import org.junit.Before

abstract class BaseJUnitTest {
    private val trampoline = TrampolineSchedulerRule()

    abstract fun start()
    abstract fun stop()

    @Before
    fun setup() {
        trampoline.start()
        MonkInstantTaskExecutorRule.start()
        start()
    }

    @After
    fun tearDown() {
        stop()
        MonkInstantTaskExecutorRule.tearDown()
        trampoline.tearDown()
    }
}
