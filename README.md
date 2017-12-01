# Sugarbaby
HCI Assignment 2017/18

# About
An app to help diabetics manage their condition in a centralized way.   
_As this is a **prototype** only meant to demonstrate key functions, changes made are not persistent after leaving the active page._

# Functions
## Goals
This startpage shows a list of goals to be achieved, e.g. keeping blood glucose level below Xmg/dL for a week.   
The page also features a monster that gets weaker as more goals are cleared. **Enhancement**: Users could collect different monsters as a record of their progress.

## Medication
This function provides on option menu where the user can add their regular medicine, and select what times they would like to receive notification reminders for that medicine. **Enhancement**: This data should be stored in a personal database.   

This page has 2 lists. The first is a list of the next 3 medicines/shots the user should be taking, calculated from the user-added list of medication. Reminders are also sent to the user through push notifications. **Enhancement**: The user can then use the notification or open the app to mark this medicine as _"taken"_.   
The second list is a record of what medicines the user has taken. Missed doses are clearly marked in a different color.   

On the second page is a record of what medicine the user is taking. There is also a bar graph depicting how many times the user has missed each medication. **Enhancement**: The user should be able to edit the medicine details in case the recommended dosage increases or decreases.

## Measurements
Blood Glucose Levels, Carbohydrate Intake, Calorie Intake, A1C Levels and Weight are all represented here as graphs over time.   
Blood Glucose, Carbohydrates, and Calories are represented in the same graph so that the user can see for themselves how their food intake is affecting their blood sugar level. This graph currently only shows data over the course of a day.
**Enhancement**: Optimally, the user should be able to select a time period, or choose between Daily/Weekly/Monthly/Yearly views.   

For each of the 3 graphs (Blood Glucose, A1C and Weight), the user can tap on the headings with the _"+"_ sign to add a new measurement, which then updates the respective graph dynamically.   

Additionally, the page provides an option menu function to export the data as an image to the device. **Enhancement**: The user could open the image directly after saving by tapping on a notification.

## Food
This page shows a record of all meals recorded by the user, with information like when the food was eaten, and total carbohydrates and calories absorbed in this meal. **Enhancement**: The user should be able to click on each meal to see exactly what they ate.   

To add a new meal, the user has to tap on the large _"Record a meal"_ button at the top of the page. This opens up a new page where the user can change the time this meal was eaten (**set to current time by default**) and add the food items that made up the meal.   
At the 2nd line of the page, the current total count of carbohydrates and calories in this meal is calculated so that the user can judge whether they are eating too much. When a new food item is added, these totals are updated. Typing in the input box brings up a list of food items already in the crowd-sourced database. When a food item from the list is selected, or if a new item not found in the database is added and the _"Edit"_ button beside the input field is clicked, a dialog appears to confirm the carbohydrates and calories for this food item. The type of food is also set from this dialog from a drop-down list with a choice of Fish, Fruit, Meat, Snacks, etc.. Once _"Confirm"_ is selected, the food item is added into the list for this meal.   
When _"Create Meal"_ at the bottom of the screen is tapped, the meal data is added into the user's record. **Enhancement**: This action will also update the first graph in the **Measurements** page.

## Journal
This is a page for users to keep track of medical appointments, and their own general wellbeing. The page displays the details of the next doctor's appointment for the user, with date, time, location, and the doctor's name. Using this data, the app can also send notification reminders to the user before the appointment.   

There is also a Rating feature that allows users to rate how good they feel for the day. The app will send a push notification once a day asking the user to rate their health. This data is recorded and presented graphically on the same page. **Enhancement**: The user could add more details like _"feeling giddy all day"_ or _"having a fever"_, which could be shown when a datapoint in the graph is tapped.
