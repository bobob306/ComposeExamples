package com.benshapiro.composeexamples.UnusedCode

//
//private var _firstName = handle.getStateFlow(FIRST_NAME, InputWrapper(boxName = "First name"))
//val firstName get() = _firstName
//private var _lastName = handle.getStateFlow(LAST_NAME, InputWrapper(boxName = "Last name"))
//val lastName get() = _lastName
//private var _age = handle.getStateFlow(AGE, InputWrapper(boxName = "Age"))
//val age get() = _age
//
//fun onFirstNameEntered(input: String) {
//    val errorId = InputValidator.getFirstNameErrorIdOrNull(input)
//    handle[FIRST_NAME] = firstName.value.copy(value = input, errorId = errorId)
//}
//
//fun onFirstNameCleared(clear: String){
//    handle[FIRST_NAME] = firstName.value.copy(value = clear)
//}
//
//fun onLastNameEntered(input: String) {
//    val errorId = InputValidator.getLastNameErrorIdOrNull(input)
//    handle[LAST_NAME] = lastName.value.copy(value = input, errorId = errorId)
//}
//
//fun onLastNameCleared(clear: String){
//    handle[LAST_NAME] = lastName.value.copy(value = clear)
//}
//
//fun onAgeEntered(input: String) {
//    val errorId = InputValidator.getAgeErrorIdOrNull(input)
//    handle[AGE] = age.value.copy(value = input, errorId = errorId)
//}
//
//fun onAgeCleared(clear: String){
//    handle[AGE] = age.value.copy(value = clear)
//}
//// recomposes composable listening for updates
//// triggers the valid flow and uses latest info

//val firstNameState: EditableUserInputState = EditableUserInputState(
//    boxName = "First name",
//    hint = "Enter first name",
//    initialText = "Enter first name",
//    false,
//    ""
//)
//val lastNameState: EditableUserInputState = EditableUserInputState(
//    boxName = "Last name",
//    hint = "Enter last name",
//    initialText = "Enter last name",
//    false,
//    ""
//)
//
//val ageState: EditableUserInputState = EditableUserInputState(
//    boxName = "Age",
//    hint = "Enter age",
//    initialText = "Enter age",
//    false,
//    ""
//)

//fun saveAction(context: Context) {
//    ageState.isSavePressed = true
//    lastNameState.isSavePressed = true
//    firstNameState.isSavePressed = true
//    if (validateTextField(context)) {
//        createPerson()
//    } else {
//        Toast.makeText(context, "Ensure fields are all entered", Toast.LENGTH_LONG).show()
//    }
//    validateNumberField(
//        /*TODO(find my validation rules from prices)*/
//    )
//}

//    private fun validateTextField(context: Context): Boolean {
//        return if (
//            firstNameState.isHint || lastNameState.isHint || ageState.isHint ||
//            firstNameState.text.isBlank() || lastNameState.text.isBlank() ||
//            ageState.text.isBlank() || !ageState.text.isDigitsOnly()
//        ) {
//            Toast.makeText(context, "Ensure fields are all entered", Toast.LENGTH_LONG).show()
//            false
//        } else {
//            Toast.makeText(context, "Person created", Toast.LENGTH_LONG).show()
//            Log.d("Text fields", "valid")
//            true
//        }
//
//    }

//                Log.d(
//                    "First name vals",
//                    "${firstName.value} " + "${firstName.value.value.isNotEmpty()} " + "${firstName.value.errorId}"
//                )
//                Log.d(
//                    "Last name vals",
//                    "${lastName.value} " + "${lastName.value.value.isNotEmpty()} " + "${lastName.value.errorId}"
//                )
//                Log.d(
//                    "Age vals",
//                    "${age.value} " + "${age.value.value.isNotEmpty()} " + " ${age.value.errorId}"
//                )
//                Log.d("Are inputs valid?", areInputsValidSimple.toString())
//                Log.d("Input valid complex?", areInputsValid.value.toString())
//                clearInputs()
//                Log.d(
//                    "First name vals",
//                    "${firstName.value} " + "${firstName.value.value.isNotEmpty()} " + "${firstName.value.errorId}"
//                )
//                Log.d(
//                    "Last name vals",
//                    "${lastName.value} " + "${lastName.value.value.isNotEmpty()} " + "${lastName.value.errorId}"
//                )
//                Log.d(
//                    "Age vals",
//                    "${age.value} " + "${age.value.value.isNotEmpty()} " + " ${age.value.errorId}"
//                )
//                Log.d("Are inputs valid?", areInputsValidSimple.toString())
//                Log.d("Input valid complex?", areInputsValid.value.toString())