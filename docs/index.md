# What is 21.Days?
21.Days is, at its core, a habit-building application. 21.Days encourages user accountability and responsibility through its notification and location-checking system to ensure that users follow through with their habits. Building a habit is just that simple!

# Features
## Easy sign in
21.Days is dedicated to making your habit-building process fun and simple. That's why you don't need to create another account just for us. 
- Sign in with Google or Facebook
- Use 21.Days without creating an account

## Create habits
Setting up a new habit in 21.Days is easy! With our clutter-free interface, beginning a new habit is just a few taps and clicks away.
- Choose from all-day habits or specify a time you wish to be notified
- Integrate with Google Fit to create step count and distance based habits
- Track how well you've been completing your habits with our streaks system

## Make yourself accountable
21.Days doesn't just let you check your habits off. You have to prove to 21.Days and, more importantly, yourself that you've completed your habit.
- Check in requirements:
    - Set a location-based geofence so 21.Days knows whether you're at the right place to complete your habit
- Be notified to complete your habits at the time you have specified

## Google Fit integration
Have a fitness habit you want to form? 21.Days makes it easy to see your exercise activities and stats through Google Fit. Just make sure you're signed in to your Google account.

## View your stats
We believe in letting you see what you've accomplished. See how you're doing and get motivated!
- See aggregate analytics of all of your habits, current and completed
- See seprate data for each individual habit

# Our team?
- Nan Hu
- Afnan Enayet
- Steven Jiang
- Chenghui Li

# Our demo slides
Some individuals who want to delve into the history of this venture have requested that we share a little of our archives. Our demo slides, including our original pitch, can be found here:
[Pitch slides](https://docs.google.com/presentation/d/1jZVyAWzXpNxvboM9mWs4kSQBQadF9-X9Kso-EU4HQVo/edit?usp=sharing)

# MVC Diagram
Below we present our MVC diagram, outlining the behind-the-scenes view for 21.Days.
![alt text](https://github.com/afnanenayet/21.Days/blob/master/docs/images/MVC.jpg?raw=true)

# Threaded Design Diagram
Below we present our Threaded Design diagram, outlining the threads involved for 21.Days.
![alt text](https://github.com/afnanenayet/21.Days/blob/master/docs/images/Threaded%20Design%20Diagram.jpg?raw=true)


# Work Distribution

|               | Complete      | Incomplete  |
| ------------- |:-------------:| :------:|
| Nan and Leo   | Analytics Fragment, Individual Analytics, Google Sign In, Habit Details, Database, Location Services |   |
| Afnan and Steven | Main Activity, Habits List Fragment, Settings Fragment, Facebook Sign In, Notification Service, Google Fit Integration   |    |

# Build status
[![Build Status](https://travis-ci.com/afnanenayet/21.Days.svg?token=QtxzrX3Qc2BDQfwx8D1K&branch=master)](https://travis-ci.com/afnanenayet/21.Days)

Continuous integration support is provided by Travis CI
