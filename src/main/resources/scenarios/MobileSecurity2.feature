@MobileSecurity_Version_5.0
Feature: Mobile Security in Device Testing

  @initialConditionsMobileSecurity
  Scenario: initial conditions Mobile Security
    Given I clear device of all text messages
    And I disable WiFi on the device
    And I install an app "AT&T Mobile Security" from Playstore

  @490426
    # when the phone cannot read the SIM for phone number, this test case will procede on "manual" phone number entry
  Scenario: 01. Verify registration by automatic phone number recognition
    # starting as basic user and data cleared = clean and launch
    Given I clean and launch app by ID "com.att.mobilesecurity"
    When I register through phone number recognition
    # select continue register phone number
    # agree to terms and conditions
    # skip unlocking Plus, stay basic user
    Then I see the main page Dashboard

  @490427
    # so now this one just copies 490426 but selecting manual instead of auto
  Scenario: 02. Verify registration by manual phone number entry correct
    # starting as basic user and data cleared = clean and launch
    Given I clean and launch app by ID "com.att.mobilesecurity"
    When I register by manual phone number entry
    # select verify manually
    # type a correct phone number
    # get Pin and enter it
    # I think next step is T&C accept, but getting bad pin code error, maybe do manual first
    # probably skip unlocking plus
    # *** can probably reuse flow of accept T&C and skip Plus as a module
    # just do the steps, then look for repeats, doing the steps is quick
    Then I see the main page Dashboard

  @490427-2
    # run this call flow before 490427 above, this is a separate behavior, but the test suite needs a registered user
  Scenario: Verify registration by manual phone number entry incorrect
    Given I clean and launch app by ID "com.att.mobilesecurity"
    When I register by incorrect phone number
    Then I see the bad number error
    # the above can be run first before correct number entry
    # consider have keyword statements without java, wonder what happens?

  @490428
  Scenario: 03. Verify an email address can be added to account
    # cannot clean app as it will require registration again
    # maybe change this language to Given I'm on Home Page?  and can add a verification into this?
    Given I close and launch app by ID "com.att.mobilesecurity"
    When I navigate to the Account page
    Then I see account email address "uet.automation.4255168453@gmail.com" or add it
    # account settings is where you can change to plus or remove service

  @490432
  Scenario: 04. Verify Basic user can add an email and save
    # put this directly after the account setting email, it is redundant for v5.x
    # can add second email address if Plus user for Theft Alerts
    Given I close and launch app by ID "com.att.mobilesecurity"
    When I add a second email to monitor
    Then I must see both email addresses listed
    # should other things be added to monitor? how to verify it's monitored? this might be for flow first time use?
    # difficult to catch the one time popup, so hopefully the accounts-add email path will prevent the popup, and focus on adding a service as "next step"

  @490429
  Scenario: 05. Verify the Device Security scan completes successfully
    # cannot clean app as it will require registration again
    Given I close and launch app by ID "com.att.mobilesecurity"
    When I run a Device Security Scan
    Then I see the completed report
    # specific phrase when report is done, run when running

  @490431
  Scenario: 07. Verify the SYS passcode OK screen for a user with a passcode
    # typical device setup is without screen lock, not sure what happens if it is setup, but can turn off from Android settings
    # test cases for Basic User could verify home screen with "unlock Plus" button shown
    Given Home screen shows screen lock is disabled
    When I click on more info and change to Pin Code 1447
    Then Display shows Screen Lock is enabled
    And Home screen shows screen lock is OK
    # turn screen lock off

  @40431-2
    Scenario: 07-2 verify application detects when screen lock is set to swipe only
    Given Home screen shows screen lock is OK
    When User changes screen lock to swipe
    Then Home screen shows screen lock is disabled

  @490433
  Scenario: 08. Verify link to Call protect will open Call Protect
    Given I close and launch app by ID "com.att.mobilesecurity"
    When I navigate to menu and select and open Call Protect
    Then Call Protect is launched or can be installed
    # put in logic to look for install first, or title of opened app

  @490436
  Scenario: 11. Verify successful scan upon install for a user with no malicious apps installed
    # how to verify no malicious apps installed? maybe scan verify all apps safe, then install bad app?
      this is redundant with scenario 05
    Given I close and launch app by ID "com.att.mobilesecurity"
    When navigate to Device Security and start application scan
    Then must see all apps safe
    # yes the given will be a scan that is safe, then install malware, then scan again 490441

  @490434
  Scenario: 09. Verify the Explore button on the New Features Plus Explore screen
    # goal of this test case is to verify the tutorial flow once subscribing to plus from upper right corner button
    Given I am a basic subscriber
    When I subscribe to Plus
    Then User is offered the Setup Tutorial

  @490434-2
  Scenario: 09-2 verify the feature tutorial after unlocking Plus
  # hold off on this as an extra item
    Given User is offered the Setup Tutorial
    When User follows the Setup Tutorial
    Then the VPN and other services will be activated
    # this begins the Plus User series of test cases

  @490434-3
  Scenario: 09-3 remove Plus service or "lock Plus"
    # goal of this test case is to verify the tutorial flow once subscribing to plus from upper right corner button
    Given I am a Plus Subscriber
    When User changes service to Basic
    Then I am a basic subscriber

  @490437
  Scenario: 12. Verify the New Verbiage and Logo on the VPN Sticky Notification.
    # ok, for each test case after registration I'm either Basic or Plus and can verify upper right button on home
    Given I am a Plus Subscriber
    When I swipe Notifications open
    Then I will see Mobile Security VPN status
    # simple ones

  @490435
  Scenario: 10. Verify the Rate the App Dialog is displayed when user enables VPN 4 times
    Given I am a Plus Subscriber
    When I enable VPN 4 times
    Then Rate the App is displayed
    # need to figure out what enable VPN means

  @490439
  Scenario: 14. Verify the Dash_Advanced screen displays wifi xxx looks safe state when user is connected to secure wifi
    Given I am a Plus Subscriber
    # need to work on how to enable VPN, but it does it once, looks like once the setting is checked user does nothing
    # but UI status is very confusing
    And I enable VPN 4 times
    When I connect to secure Wifi
    Then Status says Wifi looks safe

  @490430
  Scenario: 06. Verify Theft Alert email is sent when the wrong PIN is entered multiple times
    Given I am a Plus Subscriber
    And Theft Alert is enabled
    When wrong pin code used to unlock screen
    Then a theft alert is emailed
    # this can be created with the user check link

  @490438
  Scenario: 13. Enable on Secure transfer to RAN then to Known Unsecure Wi-Fi Network
    Given I am a Plus Subscriber
    And I enable VPN 4 times
    When I connect to unsecure Wifi
    Then VPN will be activated
    # can create this without moving device

  @490440
  Scenario: 15 Safe Browsing_Safe Browsing on Android browser  should warn of potentially malicious URL over cellular network
    Given I am a Plus Subscriber
    And Device connected to RAN only
    When I browse to malicious URL
    Then verify URL was detected as malicious
    #lots of places to check

  @490440-2
  Scenario: Safe Browsing_Safe Browsing on Android browser  should warn of potentially malicious URL over Wi Fi
    Given I am a Plus Subscriber
    And I connect to secure Wifi
    When I browse to malicious URL
    Then verify URL was detected as malicious
    #says do the same on wifi as RAN

  @490441
  Scenario: Security Advisor_Malware resident prior to Lookout activation should be detected after registration completes
    Given I am a basic subscriber
    And I install a malware application
    When I register through phone number recognition
    And I subscribe to Plus
    Then the malware application is detected
    # I think this test case can be run at anytime, but most efficient to load malware to start?

#  @490999
#  Scenario: remove Mobile Security Service
##    Given User disables draw over other apps
#    Given User is on Home Screen
#    When User removes service
#    Then User sees Service Removed Confirmation


#  @new steps
#    Scenario: the I must see location and I must see text
#      When I must view object ".*"
#      When I must view text ".*"







