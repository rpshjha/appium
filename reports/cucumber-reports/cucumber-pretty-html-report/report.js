$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/profile.feature");
formatter.feature({
  "name": "Profile",
  "description": "",
  "keyword": "Feature",
  "tags": [
    {
      "name": "@profile"
    }
  ]
});
formatter.scenario({
  "name": "open profile enlarge every menu point and verify the header",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@profile"
    }
  ]
});
formatter.step({
  "name": "I am on home page",
  "keyword": "Given "
});
formatter.match({
  "location": "steps.ProfileSteps.i_am_on_home_page()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I click on profile tab",
  "keyword": "When "
});
formatter.match({
  "location": "steps.ProfileSteps.i_click_on_profile_tab()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "profile page should get open",
  "keyword": "Then "
});
formatter.match({
  "location": "steps.ProfileSteps.profile_page_should_get_open()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I should see different menus available on profile page",
  "keyword": "And "
});
formatter.match({
  "location": "steps.ProfileSteps.i_should_see_different_menus_available_on_profile_page()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I click on each menu and verify content",
  "keyword": "Then "
});
formatter.match({
  "location": "steps.ProfileSteps.i_click_on_each_menu()"
});
formatter.result({
  "status": "passed"
});
});