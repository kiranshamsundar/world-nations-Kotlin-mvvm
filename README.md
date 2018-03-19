# world-nations-Kotlin-mvvm
A Simple Android application to understand basics of MVVM with Kotlin

Project has following package strutures :

main:Has Home View & its ViewModel.
di: Has Module & Componenet classes for Dependency Injection
database: For storing data using shared prefrences.
extension: Has useful extension functions for  classes SharedPrefrences,FragmenManager & many.
model: To hold model classes
rest:service :To hold rest Interface to get county list
rest:repo : Has repository classes for fetching list of countries from network or memory
util:Has classes such as binding Adapters used to bind views to respective ViewModels & connection observers.
nations:Has Fragment as View and its respective ViewModel class,reccyleadapter and listitem holder class.


