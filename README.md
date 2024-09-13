# EmoPal

An Android Application to help children with ASD to develop their Social-Emotional skills through Serious games.

## Table of Contents

- [About the Project](#about-the-project)
- [Technologies Used](#technologies-used)
- [Product Specifications](#product-specifications)
- [Features](#features)
  - [Diary](#diary)
  - [Game](#match-the-emotion-game)
- [Final Notes](#final-notes)

## About the Project

EmoPal is an Android Application, developed in Kotlin and Jetpack Compose, to help children with Autism to develop their Social-Emotional skills.
The application is designed with two key funcions, a Diary and a game consisting in three levels, each focussed on one aspect of learning emotions and social skills.

![MainPage](https://github.com/user-attachments/assets/cd9eb3ae-f233-4ac3-a62e-a1e03134b35d)


## Technologies Used

List the technologies/frameworks/languages used in the project.

- Kotlin
- Jetpack Compose
- RoomDB
- Google Cloud Vision API
- Dagger Hilt
- Gradle
- CameraX
- Nested NavGraphs
- Convolutional Neural Network
- TensorFlowLite
- GoogleColab

## Product Specifications
![image](https://github.com/user-attachments/assets/8be310e9-e00a-4187-9060-427ecdfe3537)


## Features

Highlights of the main features of the application.

### Diary
![Diary](https://github.com/user-attachments/assets/7c933520-42aa-4f51-afed-e43f441e638c)

- Take pictures of current emotion
- Automatic categorisation of the expressed emotion
- History display of the pictures taken and categorised
- Delete previously saved pictures through pop up menu

### Match the emotion Game
![happy2](https://github.com/user-attachments/assets/7e193463-43e5-4959-a821-e1e6aba534b1)

- Set of pictures categorised in Happiness, Anger, Sadness and Surprise
- Three levels of increasing difficulty
- Automatic recognition of the emotion expressed
- Comparison between picture shown and picture taken

## Final Notes
This project served as a working prototype and more development is needed.
A custom model has been developed to allow the application to run offline and without connecting to the Google Cloud Vision API.<br />
The model is ~70% accurate, but further tests need to be carried out. <br />
The produced model is a TensorFlowLite model trained with Keras using Convolutional Neural Network (CNN).<br />
The dataset used is [FER-2013](https://www.kaggle.com/datasets/msambare/fer2013).

![TrainingModelGraph](https://github.com/user-attachments/assets/d20bd214-5e5b-4a45-9650-1d3bf2963068)

