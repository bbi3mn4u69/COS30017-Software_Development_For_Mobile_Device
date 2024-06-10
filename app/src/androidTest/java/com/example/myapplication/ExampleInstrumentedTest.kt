package com.example.myapplication

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.Swipe
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.core.AllOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.function.IntToDoubleFunction
import java.util.regex.Matcher


@LargeTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

// test the element in UI
//    first activity
    @Test
    fun test_isActivityinview() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.first)).check(matches(isDisplayed()))
    }
    @Test
    fun test_isButtoninview() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.borrowBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.nextBtn)).check(matches(isDisplayed()))
    }
    @Test
    fun test_isTextviewinview() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.itemName)).check(matches(isDisplayed()))
        onView(withId(R.id.itemDes)).check(matches(isDisplayed()))
        onView(withId(R.id.attr1)).check(matches(isDisplayed()))
        onView(withId(R.id.attr2)).check(matches(isDisplayed()))
        onView(withId(R.id.attr3)).check(matches(isDisplayed()))
    }
    @Test
    fun test_isRatingbarinview() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()))
    }
    @Test
    fun test_isImageviewinview() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }
//    second activity
    @Test
    fun test_isSavebuttoninview() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.borrowBtn)).perform(click())
        onView(withId(R.id.saveBtn)).check(matches(isDisplayed()))
    }
    @Test
    fun test_isTextview2inview() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.borrowBtn)).perform(click())
        onView(withId(R.id.days)).check(matches(isDisplayed()))
        onView(withId(R.id.priceview)).check(matches(isDisplayed()))
    }
    @Test
    fun test_isSeekbarinview() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.borrowBtn)).perform(click())
        onView(withId(R.id.seekBar3)).check(matches(isDisplayed()))
    }

//    test the function

//    test when press next button, the information of the game should appear
    @Test
    fun test_nextButton() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)

     val COD = Game("Call of duty", "COD game", 35, false, null, null, "Shooting", "Action", "Attractive", 2.5f)
     val CSGO = Game("Counter-Strike Global-Offensive", "CSGO game", 65, false, null, null, "Shooting", "Survival", "Attractive", 3.5f)
    val PUBG = Game("Player Unknow Battle Ground", "PUBG game", 68, false, null, null, "Shooting", "Survival", "Guns", 5f)
     val RR = Game ("Real Racing", "RR game", 80, false, null, null, "Racing", "Car", "Attractive", 4.5f)
     val Informationlist = listOf(
        COD, CSGO, PUBG, RR
    )
    val numberOfPresses = 3
        for (i in 1..numberOfPresses) {
            // Click the "Next" button
            onView(withId(R.id.nextBtn)).perform(click())
            when (i) {
                i -> {
                    onView(withId(R.id.itemName)).check(matches(withText(Informationlist[i].name)))
                    onView(withId(R.id.itemDes)).check(matches(withText(Informationlist[i].des)))
                    onView(withId(R.id.priceDis)).check(matches(withText("$ " + Informationlist[i].price)))
                    onView(withId(R.id.attr1)).check(matches(withText(Informationlist[i].attr1)))
                    onView(withId(R.id.attr2)).check(matches(withText(Informationlist[i].attr2)))
                    onView(withId(R.id.attr3)).check(matches(withText(Informationlist[i].attr3)))
                }
            }
        }
    }
//    test when press the borrow button, the second activity appear
    @Test
    fun test_borrowButton() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.borrowBtn)).perform(click())
        onView(withId(R.id.second)).check(matches(isDisplayed()))
    }
    @Test
//    test when press save button without set the value for seek bar, the second activity cannot finish
    fun test_saveButton1() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.borrowBtn)).perform(click())
        onView(withId(R.id.saveBtn)).perform(click())
        onView(withId(R.id.second)).check(matches(isDisplayed()))
    }
//    test the borrow button being disable if pressing the save button after setting a value for seek bar
//    expect: disable
    @Test
    fun test_saveButton2() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.borrowBtn)).perform(click())
        onView(withId(R.id.seekBar3)).perform(setSeekBarProgress(3))
        onView(withId(R.id.saveBtn)).perform(click())
        onView(withId(R.id.borrowBtn)).check(matches((isNotEnabled())))
    }
    fun setSeekBarProgress(progress: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Set SeekBar progress to $progress"
            }

            override fun getConstraints(): org.hamcrest.Matcher<View> {
                return AllOf.allOf(
                    ViewMatchers.isAssignableFrom(android.widget.SeekBar::class.java),
                    IsInstanceOf.instanceOf(android.widget.SeekBar::class.java)
                )
            }

            override fun perform(uiController: UiController, view: View) {
                val seekBar = view as android.widget.SeekBar
                seekBar.progress = progress
            }
        }
    }
//    check the next borrow button is disable or not
//    expect: disable
    @Test
    fun test_saveButton3() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.borrowBtn)).perform(click())
        onView(withId(R.id.seekBar3)).perform(setSeekBarProgress(3))
        onView(withId(R.id.saveBtn)).perform(click())
        onView(withId(R.id.borrowBtn)).check(matches((isNotEnabled())))
        onView(withId(R.id.nextBtn)).perform(click())
        onView(withId(R.id.borrowBtn)).check(matches((isEnabled())))
    }
//    check the borrow button is being disabled after press the next button to return to the borrowed item
//    expect: disable
    @Test
    fun test_saveButton4() {
        val activity1 = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.borrowBtn)).perform(click())
        onView(withId(R.id.seekBar3)).perform(setSeekBarProgress(3))
        onView(withId(R.id.saveBtn)).perform(click())
        onView(withId(R.id.borrowBtn)).check(matches((isNotEnabled())))
        for (i in 0..4) {
            onView(withId(R.id.nextBtn)).perform(click())
        }
        onView(withId(R.id.borrowBtn)).check(matches((isEnabled())))
    }


}

