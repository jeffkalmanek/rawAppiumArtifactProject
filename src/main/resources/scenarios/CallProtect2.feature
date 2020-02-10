@CallProtect_Version_example

 # This code will run BEFORE ALL OTHER testing is done, as long as this is put into the TestNG file with @Before_Code
 # Please use @Before_Code_Production for Production runs
 # Please use @Before_Code for Development runs

Feature: Call Protect Device Test Run
  # this is the test set for Device Testing of Call Protect

  @initialConditionsCallProtect_Code
  Scenario: Initial Conditions Call Protect
##    Given I restart the device
##    And I wait for "66" seconds
    And I disable WiFi on the device
#    And I clean and launch app by ID "com.drivemode"
#    And I wait "9" to click "DriveMode.AllowPopup.allow.btn" if present
#    And I wait "9" to click "DriveMode.AllowPopup.allow.btn" if present
#    And I wait "9" to click "DriveMode.AllowPopup.allow.btn" if present
#    And I wait "9" to click "DriveMode.AllowPopup.allow.btn" if present
#    And I wait "9" to click "DriveMode.acceptT&C.accept.btn" if present
#    And I wait "9" to click "DriveMode.introAutoMode.next.btn" if present
#    And I wait "9" to click "DriveMode.introAutoMode.next.btn" if present
#    And I wait "9" to click "DriveMode.introAutoMode.next.btn" if present
#    And I wait "9" to click "DriveMode.mainPage.toggleOn.btn" if present
#    And I wait "9" to click "DriveMode.mainPage.settings.btn" if present
#    And I wait "9" to click "DriveMode.settingsPage.autoMode.btn" if present
#    And I wait "9" to click "DriveMode.autoModePage.toggleON.btn" if present
#    # this also does a close and launch of messaging application, need to change it for clean and launch
#    And I clear device of all text messages
##     And I remove all accounts
#   # And I configure Google PlayStore with "uet.automation@gmail.com" email and "Automation2019!" password
#    And I setup PlayStore with "uet.automation@gmail.com" email and "Automation2019!" password
    And I install an app "AT&T Call Protect" from Playstore
#    # thought here is to rate after an install or update, but I have the popup "look" method in the correct spot for Call Protect
#    # which is after the first block attempt - rating does not prevent a popup to rate, unfortunately, does happen at same spot
#    # here are steps that confirm the application is updated.  possible to add this to install method, later
##    And I click on "PlayStore.CallProtect.fromSearchTitle.btn"
#    #  need to put these two statements into the "install an app..." so the verification is in the method
#    Then "PlayStore.CallProtect.fromMainTitle.msg" must exist
#    And "PlayStore.CallProtectDetails.open.btn" must exist

  @CallProtect09.14
  Scenario: New User Enables the AT&T Call Protect service
    Given I clear device of all text messages
    And I clean and launch app by ID "com.att.callprotect"
     When I register phone number in ATTCallProtect
    Then I will see the Call Protect Main Page

  @CallProtect07.02
  Scenario: Navigate from main page to About Call Protect screen
    Given I close and launch app by ID "com.att.callprotect"
    When I click on "CallProtect.mainPage.options.btn"
    And I click on "CallProtect.mainPage.options.settings.btn"
    And I click on "CallProtect.mainPage.options.settings.aboutCallProtect.btn"
    Then "CallProtect.mainPage.options.settings.aboutCallProtect.version.msg" must exist

  @CallProtect07.04
  Scenario: Navigate About AT&T Mobile Security
    Given I close application by id "com.att.mobilesecurity"
    And I close and launch app by ID "com.att.callprotect"
    When I click on "CallProtect.mainPage.options.btn"
    And I click on "CallProtect.mainPage.options.settings.btn"
    And I click on "CallProtect.mainPage.options.settings.aboutMobileSecurity.btn"
    Then "CallProtect.aboutMobileSecurity.description.msg" must exist
    When I click on "CallProtect.aboutMobileSecurity.back.btn"
    And I click on "CallProtect.mainPage.options.settings.aboutMobileSecurity.btn"
    And I click on "CallProtect.aboutMobileSecurity.openApp.btn"
    Then "MobileSecurity.mainPage.msg" must exist

  @CallProtect07.06
  Scenario: Navigate to settings to delete call history
    Given I close and launch app by ID "com.att.callprotect"
    When I click on "CallProtect.mainPage.options.btn"
    And I click on "CallProtect.mainPage.options.settings.btn"
    And I click on "CallProtect.mainPage.options.settings.deleteCall.btn"
    And I click on "CallProtect.mainPage.options.settings.deleteCall.confirmDelete.btn"
    And I click on "CallProtect.mainPage.options.settings.back.btn"
    Then I must see text "No calls in the last 30 days"

  @CallProtect01.99
  Scenario: performance test of call blocking
    Given User clears dialer call log
    And User clears Call Protect call log
    When I receive a call
    And Unwanted Calls are received
    Then check call log

  @CallProtect03.07
  Scenario: Report and Block Incoming Call from Contact
    Given I receive a call
    When I close and launch app by ID "com.att.callprotect"
 #    When I select that call from Call Log - descriptive
    When I click on "CallProtect.mainPage.firstPhoneNum.btn"
    And verify it's a contact or add it as a contact
 #    Then I can block and report that call - descriptive
    And I wait "9" to click "CallProtect.callLogItem.unblock.btn" if present
    Then I click on "CallProtect.callLogItem.block.btn"
    And I rate app if first Block
    Then I click on "CallProtect.callLog.details.report.btn"
    And I click on "CallProtect.callLog.details.reportScamFraud.btn"
    And I click on "CallProtect.callLog.details.reportScamFraud.submit.btn"

  @CallProtect04.01
  Scenario: UnBlock a blocked number
    Given I close and launch app by ID "com.att.callprotect"
    When I click on "CallProtect.mainPage.blockTab.btn"
    And I click on "CallProtect.blockTab.blockList.btn"
    Then I find an item to UnBlock or move on
    And I click on "CallProtect.blockTab.myBlockList.itemName.btn"
    Then I must see text "Manually Blocked"
    When I click on "CallProtect.callLogItem.unblock.btn"

  @CallProtect04.02
  Scenario: select a Contact to Block List
    Given I close and launch app by ID "com.att.callprotect"
    When I click on "CallProtect.mainPage.blockTab.btn"
    And I click on "CallProtect.blockTab.blockList.btn"
    And I click on "CallProtect.myBlockList.add.btn"
    And I click on "CallProtect.myBlockList.add.selectContacts.btn"
    And I click on "CallProtect.myBlockList.selectContacts.selectCallFromPerfecto.btn"
    And I click on "CallProtect.myBlockList.selectContacts.actionBlock.btn"
    Then "CallProtect.myBlockList.blockNumberIsCallFromPerfecto.btn" must exist
      # commenting out next 3 steps because test case sequence is changed
      # change back when the Auto Block issue is resolved
  # added two unblock test cases in the flow, figure out later
#    And I click on "CallProtect.myBlockList.blockNumberIsCallFromPerfecto.btn"
#    Then I must see text "Unblock"
#    And I click on "CallProtect.myBlockList.enterNumber.unblock.btn"

  @CallProtect04.01
  Scenario: UnBlock a blocked number
    Given I close and launch app by ID "com.att.callprotect"
    When I click on "CallProtect.mainPage.blockTab.btn"
    And I click on "CallProtect.blockTab.blockList.btn"
    Then I find an item to UnBlock or move on
    And I click on "CallProtect.blockTab.myBlockList.itemName.btn"
    Then I must see text "Manually Blocked"
    When I click on "CallProtect.callLogItem.unblock.btn"

  @CallProtect04.08
    # NOTE can use same phone number if Unblock is run after each Block test case, refactor in the test run order
  Scenario: select a number from call log to Block List
    Given I close and launch app by ID "com.att.callprotect"
    When I click on "CallProtect.mainPage.blockTab.btn"
    And I click on "CallProtect.blockTab.blockList.btn"
    And I click on "CallProtect.myBlockList.add.btn"
    And I click on "CallProtect.myBlockList.add.selectCallLog.btn"
    And I click on "CallProtect.myBlockList.selectCallLog.selectMostRecent.off.btn"
    And I click on "CallProtect.myBlockList.selectCallLog.actionBlock.btn"
    Then "CallProtect.myBlockList.headerName.msg" must exist

  @CallProtect04.03
  Scenario: Enter a number to Block list
    Given I close and launch app by ID "com.att.callprotect"
    When I click on "CallProtect.mainPage.blockTab.btn"
    And I click on "CallProtect.block.myBlockList.btn"
    Then "CallProtect.myBlockList.msg" must exist
    When I click on "CallProtect.myBlockList.add.btn"
    And I click on "CallProtect.myBlockList.add.enterNumber.btn"
        # note - any 10 digit number can be entered, and given a name, but fake numbers can't call, can't use on other test cases
    And I click on "CallProtect.myBlockList.enterNumber.fld" and enter "2064567890"
    And I click on "CallProtect.myBlockList.enterName.fld" and enter "BlockMe"
    And I click on "CallProtect.myBlockList.enterNumber.block.btn"
    Then I must see text "BlockMe"

  @CallProtect01.14
  Scenario: Registered user is able to remove account
    Given I close and launch app by ID "com.att.callprotect"
    When I remove Call Protect account
#    Then "CallProtect.enablePage.enable.btn" must exist
#    Then I must view object "CallProtect.enablePage.enable.btn"
    Then I must view text "Enable call protection"




#  @Test3.07 - this is how I would design the test case for manual and automated testing, but comment out
#    for now and draft a simple one that repeats the existing steps
#  Scenario: Add to Block List from call log
#    Given I receive a call
#    When I block calls from that contact
#    Then calls are blocked

#  @CallProtect03.07 - this is detailed way to do it, but now 3.07 is created separately - NOTE this is duplicate leave in but commented out
#    Scenario: add to Block List from Call Log
#      Given I receive a call
#        When I close and launch app by ID "com.att.callprotect"
#        And I click on "CallProtect.mainPage.firstPhoneNum.btn"
#        And I click on "CallProtect.callLogItem.block.btn"
#        And I wait "25" to click "CallProtect.rateThisApp.noThanks.btn" if present
#        Then I must see text "Manually Blocked"
#        And I click on "CallProtect.callLogItem.unblock.btn"

#  @Test7.09 Toggle Blocked Call Notifications
#    # !!! not complete, but moving to another test case until I can figure out
#  # scrolling the main notifications window, or image searching the icon !!!
#  Scenario: Navigate to settings to toggle notifications on and off
##   Given I receive a call
#    When I close and launch app by ID "com.att.callprotect"
#    And I click on "CallProtect.mainPage.options.btn"
#    And I click on "CallProtect.mainPage.options.settings.btn"
#    And I wait "15" to click "CallProtect.mainPage.options.settings.blockedCallNotifcations.checkedOn" if present
#    And I receive a call
#    Then I must not see image "top bar icon"
#    When I click on "CallProtect.mainPage.options.btn"
#    And I click on "CallProtect.mainPage.options.settings.btn"
#    And I wait "15" to click "CallProtect.mainPage.options.settings.blockedCallNotifcations.checkedOff" if present
#    And I receive a call
#    Then I must see image "top bar icon"

