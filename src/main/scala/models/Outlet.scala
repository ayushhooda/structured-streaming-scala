package models

/**
  * Structure of the Outlet
  * @param id - outlet id
  * @param name - outlet name
  * @param place - outlet location
  * @param menu - list of items it contains
  */
case class Outlet(id: String, name: String, place: String, menu: List[Product])
