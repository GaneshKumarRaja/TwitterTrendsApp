package com.ganesh.twitterapp



import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule

import org.junit.runner.RunWith

import androidx.test.rule.ActivityTestRule



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(
    AndroidJUnit4::class
)
@LargeTest
class ExampleInstrumentedTest {


    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)











}
