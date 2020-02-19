//package deviceTestSeed;
//
//import com.qmetry.qaf.automation.step.CommonStep;
//import com.qmetry.qaf.automation.step.QAFTestStepProvider;
//import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
//import com.quantum.utils.DeviceUtils;
//import com.quantum.utils.UETCommonUtils;
//import com.quantum.utils.UETExceptions;
//import cucumber.api.java.en.When;
//
//import static com.qmetry.qaf.automation.step.CommonStep.sendKeys;
//import static com.quantum.steps.ATThotSpotCustomSteps.thisItemPresent;
//import static com.quantum.steps.PerfectoApplicationSteps.waitFor;
//import static com.quantum.utils.DeviceUtils.*;
//import static com.quantum.utils.DeviceUtils.pressKey;
//
//
//@QAFTestStepProvider
//public class MobileSecurityCustomSteps extends UETCommon {
//
//    // this Cucumber linked method "I manually enter correct CTN into MobileSecurity" is for Mobile Security version 3.3
//    // leave here until confirmed it's not used in other test suites, this is different flow for Mobile Security version 5.0
//    @When("^I manually enter correct CTN into MobileSecurity$")
//    public void enterCorrectCTNtoMobileSecurity() throws InterruptedException, UETExceptions.mfrNotDefinedException, UETExceptions.modelNotDefinedException {
//        DeviceUtils.switchToContext("NATIVE_APP");
//        deleteAllTextMessages();
//        cleanLaunchAppByID("com.att.mobilesecurity");
//        new QAFExtendedWebElement("MobileSecurity.Login.Next.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.Login.VerifyManuallyPhoneNumber.btn").click();
//
//        String QEdevicePhoneNumber = UETCommonUtils.getDeviceProperty("phoneNumber");
//        sendKeys(QEdevicePhoneNumber, "MobileSecurity.VerifyYourNumber.EnterPhoneNumber.field");
//        sendKeys(" ", "MobileSecurity.VerifyYourNumber.EnterPhoneNumber.field");
//        new QAFExtendedWebElement("MobileSecurity.Login.Next.btn").click();
//
//        getPIN("74611888", 6);
//        DeviceUtils.startApp("com.att.mobilesecurity", "identifier");
//        setMessagesPinCode("MobileSecurity.VerifyYourNumber.EnterPhoneNumber.field");
//        Thread.sleep(2000L);
//        new QAFExtendedWebElement("MobileSecurity.Login.Next.btn").click();
//        Thread.sleep(10000L);
//    }
//
//    /* from this point downward methods are for Mobile Security 5.0+ only found one method created for earlier version
//     * but I didn't want to comment it out yet.  Probably should deprecate "I manually enter correct CTN into MobileSecurity"
//     * The Use Case flow of the new application is dramatically different for registration.
//     */
//
//    @When("^User disables draw over other apps$")
//    public static void disableDrawOverOtherApps(){
//        DeviceUtils.startApp("com.android.settings","identifier");
//        new QAFExtendedWebElement("xpath=//*[@text=\"Search settings\"]").click();
//        CommonStep.sendKeys("draw over other apps","xpath=//*[@text=\"Search settings\"]");
//        DeviceUtils.pressKey("KEYBOARD_GO");
//    }
//
//
//    @When("^I register through phone number recognition$")
//    public static void registerMobileSecurityAuto() throws UETExceptions.mfrNotDefinedException {
//        System.out.println("test step - I register through phone number recognition");
//        new QAFExtendedWebElement("MobileSecurity.register.mobileSecurityTitle.msg").isPresent();
//        new QAFExtendedWebElement("MobileSecurity.register.next.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.verifyYourNumber.verifyYourNumberTitle.msg").isPresent();
//        new QAFExtendedWebElement("MobileSecurity.verifyYourNumber.continue.btn").click();
//        if (thisItemPresent("MobileSecurity.allowToMakePhoneCalls.allowToMakePhoneCallsTitle.msg", 9)) {
//            new QAFExtendedWebElement("MobileSecurity.allowToMakePhoneCalls.allow.btn").click();
//        }
//        if (thisItemPresent("MobileSecurity.cannotFindPhoneNumber.letsDoManual.msg", 9)) {
//            // phone can't read CTN from the SIM, manual path required
//            new QAFExtendedWebElement("MobileSecurity.cannotFindPhoneNumber.OK.btn").click();
//            new QAFExtendedWebElement("MobileSecurity.verifyNumberManual.sendPinToVerifyNumber.msg").isPresent();
//            new QAFExtendedWebElement("MobileSecurity.verifyNumberManual.enterPhoneNumber.fld").click();
//            new QAFExtendedWebElement("MobileSecurity.verifyNumberManual.enterPhoneNumber.fld").sendKeys(UETCommonUtils.getDeviceProperty("phoneNumber"));
//            DeviceUtils.pressKey("KEYBOARD_DONE");
//            // starting copy from Call Protect get pin
//            waitFor(12);
//            System.out.println("jefK log -- waiting until popup clears then checking for Verify Pin title");
//            // begin Do-While(notGoodPinCode) loop
//            String callProtectPhoneNumber = UETCommonUtils.getDeviceProperty("phoneNumber");
//            int tooManyPopups = 0;
//            boolean notGoodPinCode = true;
//            tooManyPopups = 0;
//            do {
//                new QAFExtendedWebElement("MobileSecurity.verifyNumberManual.enterPIN.msg").isDisplayed();
//                // open messages app to get pin code and enter it into this field
//                System.out.println("jefK log - waiting then opening messages app for pin code");
//                /* can't call getPinCode within the QAF element because have to come back to Call Protect
//                 * and this emphasizes the rule of scope for application test code, although I could put the
//                 * string for call protect name as an input to getPin method, interesting, for now use local
//                 * variable
//                 */
//                // go get the pin code from text message, consider closing message app to return focus?
//                String callProtectPinCode = CallProtectCustomSteps.getPinCodeFromMessageApp(6);
//                // bring Call Protect to front by launching
//                DeviceUtils.startApp("com.att.mobilesecurity", "identifier");
//                DeviceUtils.switchToContext("NATIVE_APP");
//                // this will put in BAD pin code
////            callProtectPinCode = "999999";
//                System.out.println("jefK log -- enter this pin code " + callProtectPinCode);
//                new QAFExtendedWebElement("MobileSecurity.verifyNumberManual.enterPIN.fld").sendKeys(callProtectPinCode);
//                // now that pin code is entered, click on verify
//                new QAFExtendedWebElement("MobileSecurity.verifyNumberManual.verify.btn").click();
//                // look for invalid pin code error/retry button, if found click and repeat, else exit loop
//                if (thisItemPresent("MobileSecurity.verifyNumberManual.invalidPin.msg", 33)) {
//                    // creating a checkpoint, I don't want to stop the test, or do I?
//                    // for now let's stop the test, like the User gives up, maybe tGuard can find it then
//
//                    System.out.println("jefK log -- pin code invalid, throwing AssertionError");
//                /*
//                 * commenting this out, it is not working the way I expect, diving through methods, it looks like it will wait until it is not present, but if it
//                 * stays present, it does not throw the AssertionError, could ask about this later, or look through documentation, but just going to throw an
//                 * AssertionError directly from if statement
////                new QAFExtendedWebElement("CallProtect.invalidPin.btn").assertNotPresent("PIN code error on CTN <"+callProtectPhoneNumber+"> and PIN code <"+ callProtectPinCode+">");
//                 *
//                 */
//
//                    System.out.println("jefK log -- PIN code error on CTN <" + callProtectPhoneNumber + "> and PIN code <" + callProtectPinCode + ">");
//                    // OK I'm going with AssertionError because of the message, but the there is no failed step, it does fail the test
//                    // using isDisplayed for something I know isn't displayed works also and fails the step, and shows the info luckily
////                new QAFExtendedWebElement("CallProtect.agreeT&C.msg").isDisplayed();
//                    throw new AssertionError("PIN code error on CTN <" + callProtectPhoneNumber + "> and PIN code <" + callProtectPinCode + ">");
//                    // the above flow is temporary, but once long term decision made can clean this up
//                    // taking the below out temporarily, while throwing AssertionError
////                System.out.println("jefK log -- Pin Code was invalid, delete all text messages, then try again");
////                deleteAllTextMessages();
////                System.out.println("jefK log -- all text messages should be gone");
////                // come back to Call protect - invalid PIN popup gone, but can request new pin, at PIN entry screen
////                closeLaunchAppByID("com.att.callprotect");
////                new QAFExtendedWebElement("CallProtect.verifyPin.newPin.btn").click();
////                tooManyPopups++;
//                    // this is end of temporary removal
//
//                } else {
//                    System.out.println("jefK log -- bad pin error wasn't found: Continuing.");
//                    notGoodPinCode = false;
//                }
//            } while (notGoodPinCode && tooManyPopups <= 3);
//
//            //ending copy from Call Protect get pin
//
//        }
//        // now ready to enroll, should end up here anyway if manual message doesn't show up, correct?
////        waitFor(33);
//        new QAFExtendedWebElement("MobileSecurity.youreEnrollingIn.youreEnrollingInTitle.msg").isPresent();
//        new QAFExtendedWebElement("MobileSecurity.youreEnrollingIn.next.btn").click();
////        waitFor(33);
//        new QAFExtendedWebElement("MobileSecurity.termsAndConditions.termsAndConditionsTitle.msg").isPresent();
//        new QAFExtendedWebElement("MobileSecurity.termsAndConditions.iAgree.btn").click();
////        waitFor(33);
//        new QAFExtendedWebElement("MobileSecurity.appAllowFileAccess.forBestExperienceTitle.msg").isPresent();
//        new QAFExtendedWebElement("MobileSecurity.appAllowFileAccess.continue.btn").click();
////        if (ATThotSpotCustomSteps.thisItemPresent("MobileSecurity.appAllowFileAccess.forBestExperienceTitle.msg",9)){
////            new QAFExtendedWebElement("MobileSecurity.appAllowFileAccess.continue.btn").click();
////        }
//        if (thisItemPresent("MobileSecurity.androidAllowFileAccess.allowAccessPhotosMediaFilesTitle.msg", 9)) {
//            new QAFExtendedWebElement("MobileSecurity.androidAllowFileAccess.allow.btn").click();
//        }
//        new QAFExtendedWebElement("MobileSecurity.newFeatures.unlockWithPlusInfo.msg").isPresent();
//        new QAFExtendedWebElement("MobileSecurity.newFeatures.skip.btn").click();
//    }
//
//    @When("^I see the main page Dashboard$")
//    public static void mustSeeDashboardMobileSecurity() {
//        System.out.println("test step - I see the main page Dashboard");
//        new QAFExtendedWebElement("MobileSecurity.homePage.deviceSecurityTile").assertPresent();
//    }
//
//    @When("^I register by manual phone number entry$")
//    public static void registerMobileSecurityManual() {
//        System.out.println("test step - I register by manual phone number entry");
//        // this method needs getPinCode, etc. leave for later, get the other test cases set behind the auto registration
////        new QAFExtendedWebElement("MobileSecurity.verifyYourNumber.verifyYourNumberTitle.msg").isPresent();
////        new QAFExtendedWebElement("MobileSecurity.verifyYourNumber.verifyManually.btn").click();
//    }
//
//    @When("^I register by incorrect phone number$")
//    public static void registerMobileSecurityManualWrong() {
//        System.out.println("test step - I register by incorrect phone number");
//    }
//
//    @When("^I see the bad number error$")
//    public static void mustSeeBadNumberError() {
//        System.out.println("test step - I see the bad number error");
//    }
//
//    @When("^I navigate to the Account page$")
//    public static void navigateToAccountInformationMbileSecurity() {
//        System.out.println("test step - I navigate to the Account page");
//        // maybe change preceeding close and launch to be Given I'm on home screen?  then the java includes the close and open?
//        new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.menuPage.account.btn").click();
//    }
//
//    @When("^I see account email address \"([^\"]*)\" or add it$")
//    public static void seeAccountEmailAddress(String emailAddress) {
//        System.out.println("test step - I see my account email address or add it");
//        new QAFExtendedWebElement("MobileSecurity.account.accountInformation.msg").isPresent();
//        // comment out the use of thisItemPresent and put in the isPresent
////        if (thisItemPresent("MobileSecurity.account.addEmail.btn",9)){
//        if (new QAFExtendedWebElement("MobileSecurity.account.addEmail.btn").isPresent()) {
//            // no email address, starting from scratch
//            new QAFExtendedWebElement("MobileSecurity.account.addEmail.btn").click();
//            new QAFExtendedWebElement("MobileSecurity.editEmail.editEmailTitle.msg").assertPresent();
//            new QAFExtendedWebElement("MobileSecurity.editEmail.emailAddress.fld").click();
//            sendKeys(emailAddress, "MobileSecurity.editEmail.emailAddress.fld");
//            new QAFExtendedWebElement("MobileSecurity.editEmail.saveEmail.btn").click();
//            new QAFExtendedWebElement("MobileSecurity.account.accountInformation.msg").assertPresent();
//            new QAFExtendedWebElement("xpath=//*[@text=\"" + emailAddress + "\"]").assertPresent();
//        } else {
//            // check the email address that is there, if different, edit email address
//            // changing this from method thisItemPresent to isPresent
////            if (!thisItemPresent("xpath=//*[@text=\""+emailAddress+"\"]",9)){
//            if (!new QAFExtendedWebElement("xpath=//*[@text=\"" + emailAddress + "\"]").isPresent()) {
//                new QAFExtendedWebElement("MobileSecurity.account.editEmail.btn").click();
//                new QAFExtendedWebElement("MobileSecurity.editEmail.editEmailTitle.msg").assertPresent();
//                new QAFExtendedWebElement("MobileSecurity.editEmail.emailAddress.fld").click();
//                new QAFExtendedWebElement("MobileSecurity.editEmail.emailAddress.fld").clear();
//                sendKeys(emailAddress, "MobileSecurity.editEmail.emailAddress.fld");
//                new QAFExtendedWebElement("MobileSecurity.editEmail.saveEmail.btn").click();
//                new QAFExtendedWebElement("MobileSecurity.account.accountInformation.msg").assertPresent();
//                new QAFExtendedWebElement("xpath=//*[@text=\"" + emailAddress + "\"]").assertPresent();
//            }
//        }
//    }
//
//    @When("^I run a Device Security Scan$")
//    public static void runSecurityScan() {
//        System.out.println("test step - I run a Device Security Scan");
//        new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.menuPage.deviceSecurity.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.deviceSecurity.deviceSecurityTitle.msg").assertPresent();
//        new QAFExtendedWebElement("MobileSecurity.deviceSecurity.appsTab.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.deviceSecurityApps.scanNow.btn").click();
//    }
//
//    @When("^I see the completed report$")
//    public static void seeCompletedReport() {
//        System.out.println("test step - I see the completed report");
//        // assertVisualText has a 60 second wait, but this test needed more
//        // NOTE - assertVisualText is an option, but assertPresent is an object with locator, that's the preferred
//        waitFor(55);
////        assertVisualText("Updated less than a minute ago");
//        new QAFExtendedWebElement("MobileSecurity.deviceSecurityApps.scanStatus.msg").assertPresent();
//    }
//
//    @When("^I add a second email to monitor$")
//    public static void addEmailToMonitor() {
//        System.out.println("test step - I add a second email to monitor");
//    }
//
//    @When("^I must see both email addresses listed$")
//    public static void seeBothEmailAddresses() {
//        System.out.println("test step - I must see both email addresses listed");
//    }
//
//    @When("^Home screen shows screen lock is disabled$")
//    public static void isScreenLockDisabled() {
//        // initial conditions are; on the home screen, and unlock Plus is button is displayed
//        System.out.println("test step - Home screen shows screen lock is disabled");
//        // two things, this button is the same even when not homePage, so change naming, and even from home there is a path to home
//        closeLaunchAppByID("com.att.mobilesecurity");
//        //make sure home page displayed
//        new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.menuPage.home.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.homePage.unlockPlus.btn").assertPresent();
///* commenting out this section, it was early testing on isPresent, now know there is no assertion error with isPresent
// * use assertPresent to ensure an assertion error
//        if (new QAFExtendedWebElement("MobileSecurity.homePage.screenLockDisabled.btn").isPresent()){
//            System.out.println("jefK log -- found screen lock is disabled");
//        }else {
//            System.out.println("jefK log -- did not find screen lock disabled");
//        }
// */
//        new QAFExtendedWebElement("MobileSecurity.homePage.screenLockDisabled.btn").assertPresent();
//        // some logic to turn off screen lock is the above status is false?
//        // automation panda says each scenario is single case, so no "or"
//        // this does mean sequencing could make initial conditions fail
//    }
//
//    @When("^I click on more info and change to Pin Code 1447$")
//    public static void screenLockMoreInfo() {
//        // testing the more info path from Home Screen, other paths possible
//        System.out.println("test step - I click on more info and change to Pin Code 1447");
//        new QAFExtendedWebElement("MobileSecurity.homePage.moreInfo.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.threatDetails.threatDetailsTitle.msg").assertPresent();
//        new QAFExtendedWebElement("MobileSecurity.threatDetails.goToSettings.btn").click();
//        /* commenting out the odd section that is LG or OS 10
//        new QAFExtendedWebElement("Settings.screenUnlock.screenUnlockTitle.msg").assertPresent();
//         *
//         */
//        new QAFExtendedWebElement("Settings.screenLockType.screenLockType.msg").assertPresent();
//        new QAFExtendedWebElement("Settings.screenLockType.PIN.btn").click();
//        new QAFExtendedWebElement("Settings.setPin.pinEntryInstructions1.msg").assertPresent();
//        new QAFExtendedWebElement("Settings.setPin.pinEntry.fld").click();
//        sendKeys("1447", "Settings.setPin.pinEntry.fld");
//        new QAFExtendedWebElement("Settings.setPin.continueOrOK.btn").click();
//        new QAFExtendedWebElement("Settings.setPin.pinEntryInstructions2.msg").assertPresent();
//        new QAFExtendedWebElement("Settings.setPin.pinEntry.fld").click();
//        sendKeys("1447", "Settings.setPin.pinEntry.fld");
//        new QAFExtendedWebElement("Settings.setPin.continueOrOK.btn").click();
//        new QAFExtendedWebElement("Settings.notifications.done.btn").click();
//    }
//
//    /* commenting out this block as the initial code before refactoring
//    @When("^Home screen shows screen lock is OK$")
//    public static void screenLockStatusOK() {
//        System.out.println("test step - Home screen shows screen lock is OK");
//        new QAFExtendedWebElement("MobileSecurity.deviceSecuritySystem.screenLockEnabled.msg").assertPresent();
//        new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.menuPage.home.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.homePage.deviceSecurityIsOK.btn").assertPresent();
//    }
//    */
//
//    @When("^Display shows Screen Lock is enabled$")
//    public static void screenLockEnabledMobileSecurity() {
//        System.out.println("test step - Home screen shows screen lock is OK");
//        new QAFExtendedWebElement("MobileSecurity.deviceSecuritySystem.screenLockEnabled.msg").assertPresent();
////        new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
////        new QAFExtendedWebElement("MobileSecurity.menuPage.home.btn").click();
////        new QAFExtendedWebElement("MobileSecurity.homePage.deviceSecurityIsOK.btn").assertPresent();
//    }
//
//    @When("^Home screen shows screen lock is OK$")
//    public static void screenLockStatusOK() {
//        System.out.println("test step - Home screen shows screen lock is OK");
////        new QAFExtendedWebElement("MobileSecurity.deviceSecuritySystem.screenLockEnabled.msg").assertPresent();
//        new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.menuPage.home.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.homePage.deviceSecurityIsOK.btn").assertPresent();
//    }
//
//    @When("^User changes screen lock to swipe$")
//    public static void changeScreenLockToSwipe() {
//        System.out.println("test step - User changes screen lock to swipe");
//        closeLaunchAppByID("com.android.settings");
//        new QAFExtendedWebElement("Settings.home.homeTitle.msg").assertPresent();
//        new QAFExtendedWebElement("Settings.home.search.btn").click();
//        new QAFExtendedWebElement("Settings.searchSettings.keywordEntry.fld").click();
//        new QAFExtendedWebElement("Settings.searchSettings.keywordEntry.fld").clear();
//        new QAFExtendedWebElement("Settings.searchSettings.keywordEntry.fld").sendKeys("lock screen");
////            sendKeys("lock screen","Settings.searchSettings.keywordEntry.fld");
//        pressKey("KEYBOARD_GO");
//        new QAFExtendedWebElement("Settings.searchSettingsResult.lockScreen.btn").click();
//        new QAFExtendedWebElement("Settings.lockScreen.lockScreenTitle.msg").assertPresent();
//        new QAFExtendedWebElement("Settings.lockScreen.screenLockType.btn").click();
//        new QAFExtendedWebElement("Settings.confirmPin.confirmPin.msg").assertPresent();
//        new QAFExtendedWebElement("Settings.confirmPin.pinEntry.fld").click();
//        new QAFExtendedWebElement("Settings.confirmPin.pinEntry.fld").clear();
//        new QAFExtendedWebElement("Settings.confirmPin.pinEntry.fld").sendKeys("1447");
//        new QAFExtendedWebElement("Settings.confirmPin.next.btn").click();
//        new QAFExtendedWebElement("Settings.screenLockType.screenLockType.msg").assertPresent();
//        new QAFExtendedWebElement("Settings.screenLockType.swipe.btn").click();
//    }
//
//    // Yaron
//
//    /*
//
//     1. Is present returns a boolean, which we can evaluate IF we want to, but does NOT fail the test and it continues.
//             boolean result =     new QAFExtendedWebElement("MobileSecurity.deviceSecuritySystem.screenLockEnabled").isPresent();
//              if(!result) {
//        System.out.println("element is not present");
//        // take some action
//    }
//
//     2.    assertPresent fails the test if the element is not found.
//     3. Click commands by default fail the test if the element is not there. This can be overriden by the user as in the snippet below
//
//        try {
//            new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
//        } catch (Exception e) {
//            e.printStackTrace();
//            //take action OR ignore
//        }
//        // End Yaron
//
//
//        */
//    // put in code to turn off screen lock as it likely blocks testing
//
//
//    @When("^I navigate to menu and select and open Call Protect$")
//    public static void navigateToCallProtect() {
//        System.out.println("test step - I navigate to menu and select and open Call Protect");
//        new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.menuPage.callProtect.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.aboutCallProtect.aboutCallProtectTitle.msg").assertPresent();
//        new QAFExtendedWebElement("MobileSecurity.aboutCallProtect.openCallProtect.btn").click();
//    }
//
//    @When("^Call Protect is launched or can be installed$")
//    public static void launchOrInstallCallProtectFromMobileSecurity() {
//        System.out.println("test step - Call Protect is launched or can be installed");
//        new QAFExtendedWebElement("CallProtect.enablePage.marketingTitle.msg").assertPresent();
//    }
//
//    @When("^navigate to Device Security and start application scan$")
//    public static void startDeviceSecurityAppScan() {
//        System.out.println("test step -  navigate to Device Security and start application scan");
//        // link to the other test case that installs malware
//    }
//
//    @When("^must see all apps safe$")
//    public static void mustSeeAllAppsSafe() {
//        System.out.println("test step - must see all apps safe");
//    }
//
//    @When("^I am a basic subscriber$")
//    public static void verifyBasicSubscriberMobileSecurityPlus() {
//        // flow through the home screen plus button, see info and subscribe
//        // should only see the button if Basic subscriber
//        System.out.println("test step - I am a basic subscriber");
//        closeLaunchAppByID("com.att.mobilesecurity");
//        new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.menuPage.home.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.homePage.unlockPlus.btn").assertPresent();
//    }
//
//    @When("^User is on Home Screen$")
//    public static void verifyHomeScreenMobileSecurity() {
//        // flow through the home screen plus button, see info and subscribe
//        // should only see the button if Basic subscriber
//        System.out.println("test step - User is on Home Screen");
//        closeLaunchAppByID("com.att.mobilesecurity");
//        new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.menuPage.home.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.homePage.homeTitle.msg").assertPresent();
//    }
//
//    //    @When("^I subscribe to Plus and follow tutorial$")
//    // good example of too many behaviors in a scenario breaking this into two behaviors but will leave in the scenario
//    @When("^I subscribe to Plus$")
//    public static void followPlusTutorialMobileSecurity() {
//        // this likely is button click step, and tutorial clicks
//        // good example of compound
//        System.out.println("test step - I subscribe to Plus and follow tutorial");
//        new QAFExtendedWebElement("MobileSecurity.homePage.unlockPlus.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.comparePlans.comparePlansTitle.msg").assertPresent();
//        new QAFExtendedWebElement("MobileSecurity.comparePlans.changeService.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.serviceSignup.userIDTitle.msg").assertPresent();
//        // clear and fill field even though it might be filled in from MyATT testing
//        new QAFExtendedWebElement("MobileSecurity.serviceSignup.userIDInput.fld").click();
//        new QAFExtendedWebElement("MobileSecurity.serviceSignup.userIDInput.fld").clear();
//        sendKeys("uet.automation.3607125452@gmail.com", "MobileSecurity.serviceSignup.userIDInput.fld");
//        new QAFExtendedWebElement("MobileSecurity.serviceSignup.passwordInput.fld").click();
//        new QAFExtendedWebElement("MobileSecurity.serviceSignup.passwordInput.fld").clear();
//        sendKeys("TESTING-2019", "MobileSecurity.serviceSignup.passwordInput.fld");
//        new QAFExtendedWebElement("MobileSecurity.serviceSignup.continue.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.youreEnrollingInPlus.includesPlusFeatures.msg").assertPresent();
//        new QAFExtendedWebElement("MobileSecurity.youreEnrollingInPlus.iAgree.btn").click();
//    }
//
//    @When("^User is offered the Setup Tutorial$")
//    public static void setupTutorialOffered() {
//        System.out.println("test step - the Setup Tutorial will be offered");
//        new QAFExtendedWebElement("MobileSecurity.plusFeaturesUnlocked.plusFeaturesUnlockedTitle.msg").assertPresent();
//        new QAFExtendedWebElement("MobileSecurity.plusFeaturesUnlocked.notNow.btn").click();
//        // note skipping starting the tutorial and following the "not now" path so this can be a test step for adding plus without setup tutorial, that's another step,
//        // other option is to have a point where it's the branch point before the two choices, might change back
//    }
//
//    @When("^User follows the Setup Tutorial$")
//    public static void follwSetupTutorial() {
//        System.out.println("test step - following Setup Tutorial");
//    }
//
//    @When("^User changes service to Basic$")
//    public static void changeServiceToBasicMobileSecurity() {
//        System.out.println("test step - User changes service to Basic");
//        new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.menuPage.account.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.account.accountInformation.msg").assertPresent("User on Account Page");
//        // this next step is unneeded as that is always present on account page in Basic sub state
//        new QAFExtendedWebElement("MobileSecurity.account.pricePlus.msg").assertPresent();
//        // this change service button is not present in Basic sub state
//        new QAFExtendedWebElement("MobileSecurity.account.changeService.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.changeService.changeService.msg").assertPresent();
//        new QAFExtendedWebElement("MobileSecurity.changeService.freeBasic.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.changeService.next.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.lockPlusFeatures.lockPlusFeatures.msg").assertPresent();
//        new QAFExtendedWebElement("MobileSecurity.lockPlusFeatures.lock.btn").click();
//        PerfectoApplicationSteps.waitFor(33);
//        new QAFExtendedWebElement("MobileSecurity.backToBasics.backToBasics.msg").assertPresent();
//        new QAFExtendedWebElement("MobileSecurity.backToBasics.ok.btn").click();
//    }
//
//    @When("^User removes service$")
//    // this flow is presented when user is a Plus Subscriber, but not if user is a Basic Subscriber
//    // I think I can put logic in to remove service in either case
//    public static void removeServiceMobileSecurity() {
//        System.out.println("test step - User removes service");
//        new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.menuPage.account.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.account.accountInformation.msg").assertPresent("User on Account Page");
//        if (new QAFExtendedWebElement("MobileSecurity.account.removeService.btn").isPresent()){
//            new QAFExtendedWebElement("MobileSecurity.account.removeService.btn").click();
//            new QAFExtendedWebElement("MobileSecurity.removeBothPopup.removeBothTitle.msg").assertPresent("remove both popup is displayed");
//            new QAFExtendedWebElement("MobileSecurity.removeBothPopup.remove.btn").click();
//        }else {
//            new QAFExtendedWebElement("MobileSecurity.account.changeService.btn").click();
//            new QAFExtendedWebElement("MobileSecurity.changeService.changeService.msg").assertPresent();
//            new QAFExtendedWebElement("MobileSecurity.changeService.selectRemoveService.btn").click();
//            new QAFExtendedWebElement("MobileSecurity.changeService.next.btn").click();
//        }
//        // below is the confirm in popup I think? commenting out, as these steps are in the confirmation-verification method
////        new QAFExtendedWebElement("MobileSecurity.changeService.removeServiceConfirm.msg").assertPresent("close application popup is displayed");
////        new QAFExtendedWebElement("MobileSecurity.changeService.removeServiceConfirm.btn").click();
//    }
//
//    @When("^User sees Service Removed Confirmation$")
//    public static void serviceRemovedConfirmationMobileSeurity(){
//        System.out.println("test step - User sees Service Removed Confirmation");
//        PerfectoApplicationSteps.waitFor(22);
//        new QAFExtendedWebElement("MobileSecurity.changeService.removeServiceConfirm.msg").assertPresent();
//        new QAFExtendedWebElement("MobileSecurity.changeService.removeServiceCloseApp.btn").click();
//    }
//
//    @When("^the VPN and other services will be activated$")
//    public static void plusServicesActivatedMobileSecurity() {
//        // one of the services hit a snag, make a list of what should happen, then
//        // steps to go to each item from menu list maybe?
//        System.out.println("the VPN and other services will be activated");
//    }
//
//    @When("^I am a Plus Subscriber$")
//    public static void verifyPlusSubscriberMobileSecurity() {
//        // upper right Plus is NOT there
//        System.out.println("test step - I am a Plus Subscriber");
//        closeLaunchAppByID("com.att.mobilesecurity");
//        new QAFExtendedWebElement("MobileSecurity.homePage.menu.btn").click();
//        new QAFExtendedWebElement("MobileSecurity.menuPage.home.btn").click();
//        if (new QAFExtendedWebElement("MobileSecurity.homePage.unlockPlus.btn").isPresent()) {
//            throw new AssertionError("User is NOT a Plus subscriber - unlock-Plus button found");
//        }
////        new QAFExtendedWebElement("MobileSecurity.homePage.unlockPlus.btn").assertPresent();
//    }
//
//    @When("^I swipe Notifications open$")
//    public static void swipeNotificationsOpenMobileSecurity() {
//        // potential for borad step
//        System.out.println("test step - I swipe Notifications open");
//    }
//
//    @When("^I will see Mobile Security VPN status$")
//    public static void verifyVPNStatusMobileSecurity() {
//        System.out.println("test step - I will see Mobile Security VPN status");
//    }
//
//    @When("^I enable VPN 4 times$")
//    public static void enableVPNMobileSecurity() {
//        // maybe have an enable method and then loop it, pass in the times to loop?
//        System.out.println("test step - I enable VPN 4 times");
//    }
//
//    @When("^Rate the App is displayed$")
//    public static void verifyRateTheAppDisplayedMobileSecurity() {
//        System.out.println("test step - Rate the App is displayed");
//    }
//
//    @When("^I connect to secure Wifi$")
//    public static void connectToSecureWifiMobileSecurity() {
//        // if done from settings, has global use
//        System.out.println("test step - I connect to secure Wifi");
//    }
//
//    @When("^Status says Wifi looks safe$")
//    public static void verifyWifiStatusSafeMobileSecurity() {
//        System.out.println("test step - Status says Wifi looks safe");
//    }
//
//    @When("^Theft Alert is enabled$")
//    public static void enableCheckTheftAlertMobileSecurity() {
//        //general method design of needing a state, not verifying one
//        System.out.println("test step - Theft Alert is enabled");
//    }
//
//    @When("^wrong pin code used to unlock screen$")
//    public static void attemptToUnlockScreenMobileSecurity() {
//        // could have input of pin code to be good or bad, keep attempting until a message?
//        System.out.println("test step - wrong pin code used to unlock screen");
//    }
//
//    @When("^a theft alert is emailed$")
//    public static void verifyTheftAlertEmailedMobileSecurity() {
//        System.out.println("test step - a theft alert is emailed");
//    }
//
//    @When("^I connect to unsecure Wifi$")
//    public static void connectToUnsecureWifiMobileSecurity() {
//        System.out.println("test step - I connect to unsecure Wifi");
//    }
//
//    @When("^VPN will be activated$")
//    public static void verifyVPNActivatedMobileSecurity() {
//        // VPN is automatic when changing to open Wifi, even on user choice through settings
//        // looked like the VPN needed to scan the wifi for a while, now working faster
//        System.out.println("test step - VPN will be activated");
//    }
//
//    @When("^Device connected to RAN only$")
//    public static void connectedToRANOnlyMobileSecurity() {
//        // global appeal
//        System.out.println("test step - Device connected to RAN only");
//    }
//
//    @When("^I browse to malicious URL$")
//    public static void browseToMaliciousURLMobileSecurity() {
//        System.out.println("test step - I browse to malicious URL");
//    }
//
//    @When("^verify URL was detected as malicious$")
//    public static void verifyMaliciousURLDetectedMobileSecurity() {
//        System.out.println("test step - verify URL was detected as malicious");
//    }
//
//    @When("^I install a malware application$")
//    public static void installMalwareMobileSecurity() {
//        System.out.println("test step - I install a malware application");
//    }
//
//    @When("^the malware application is detected$")
//    public static void verifyMalwareIsDetectedMobileSecurity() {
//        System.out.println("test step - the malware application is detected");
//    }
//
////    @When()
//
//
//}
//
