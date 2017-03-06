# What is 21.Days?
21.Days is a at its core a habit-building application. 21.Days uses behavioral analytics to intelligently deliver prompts to users at exactly the right time to ensure that they follow through on behaviors and solidify them until they become real habits.

# Features
## Easy sign in
21.Days is dedicated to making your habit-building process fun and simple. That's why we don't need you to create another account just for us. 
- Sign in with your Google or Facebook account
- Use 21.Days withouth creating an account

## Create habits
21.Days uses behavioral analytics to intelligently construct a mechanism that prompts you at the exact right time to ensure your habit-building success.
- Behavior-based reward system to reinforce your commitment
- Track how long you've been completing your habits with our streaks system

## Make yourself accountable
21.Days doesn't just let you check your habits off. You have to show 21.Days that you've done what you needed.
- Check in requirements:
    - Set a location-based geofence so 21.Days reminds you until you're where you need to be
    - Take a photo with your tasks to give yourself a visual history
- Intelligent notifications will use machine learning adapt to your behavior and fire at the most optimal times so you can reinforce your habits

## Google Fit integration
Have a fitness habit you want to form? 21.Days makes it easy to see your exercise activities and stats through Google Fit integration.

## View your stats
We believe in letting you see what you've accomplished. See how you're doing and get motivated!
- See aggregate analytics of all of your habits, current and completed
- See seprate data for each individual habit

# Our team?
- Nan Hu
- Afnan Enayet
- Steven Jiang
- Chenghui Li

# Our original pitch
Some individuals who want to delve into the history of this venture have requested that we share a little of our archives. Our original pitch can be found at this link:
[Pitch slides](https://docs.google.com/presentation/d/1LqYHAFYNit3Wd_bE9uATRXGYYwExH9Xo0qOO0rtN3oY/edit#slide=id.p)

# MVC Diagram
Below we present our MVC diagram, outlining the behind-the-scenes view for 21.Days.
![alt text](https://raw.githubusercontent.com/afnanenayet/21.Days/master/docs/images/MVC.jpg?token=ARpKavc22tgoQx1jVjW6MulirliHG6tSks5YvZgIwA%3D%3D)

# Threaded Design Diagram
Below we present our Threaded Design diagram, outlining the threads involved for 21.Days.
![alt text](https://raw.githubusercontent.com/afnanenayet/21.Days/master/docs/images/Threaded%20Design%20Diagram.jpg?token=ARpKatSv5jIV2uj72_uUYUCM0YRruv9tks5Yxi5awA%3D%3D)


# Work Distribution

|               | Complete      | Incomplete  |
| ------------- |:-------------:| :------:|
| Nan and Leo   | Analytics, Google Sign In, Habit Details, Database, Location Services    | Notification Service  |
| Afnan and Steven | Main Activity, Habits List Fragment, Settings Fragment, Facebook Sign In   |   Google Fit Integration, Machine Learning |

# Build status
[![Build Status](https://travis-ci.com/afnanenayet/21.Days.svg?token=QtxzrX3Qc2BDQfwx8D1K&branch=master)](https://travis-ci.com/afnanenayet/21.Days)

Continuous integration support is provided by Travis CI
